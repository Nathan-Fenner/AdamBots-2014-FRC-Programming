package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 * @author Nathan
 */
public class RobotPickup {
        private static PIDController pidController = new PIDController(-200, 0, 0, RobotSensors.pickupPotentiometer, RobotActuators.pickupMechMotor);
	//changing later
        private static final double ANGLE_TOLERANCE = 10; 
        //// TODO: CHANGE BACK TO 3
	private static final double PICKUP_POSITION = -18;
	//private static final double SHOOT_POSITION = 45.0;						// Practice robot
	//private static final double SHOOT_POSITION = 48.0;						// Practice robot // Changed to bring angle up a few degrees.  Actually targeting 45
	//private static final double TRUSS_POSITION = 55.0;
	private static final double SHOOT_POSITION = 43.0;							// competition robot // targeting 5 degrees less than the practice one
	private static final double TRUSS_POSITION = 50.0;							// competition robot // targeting 5 degrees less than the practice one
	private static final double CATCH_POSITION = 90;
	private static double armTargetAngle = CATCH_POSITION;
	private static double lastPosition = 0.0;
	private static double velocity = 0.0;
	private static Timer timer;
	private static double lastTime = 0;

	public static double getArmTargetAngle() {
		return armTargetAngle;
	}

	public static void openRollerArm() {
		if (getArmAngleAboveHorizontal() > 80 || isUpperLimitReached()) {
			return;
		}
		RobotActuators.rollerArmUp.set(false);
		RobotActuators.rollerArmDown.set(true);
	}

	public static void closeRollerArm() {
		RobotActuators.rollerArmUp.set(true);
		RobotActuators.rollerArmDown.set(false);
	}

	public static void neutralRollerArm() {
		RobotActuators.rollerArmUp.set(false);
		RobotActuators.rollerArmDown.set(false);
	}

	public static void setRollerSpeed(double speed) {
		RobotActuators.pickupRollerArmMotor.set(speed);
	}

	public static void adjustArmAngle(double adjustAngle) {
		armTargetAngle += adjustAngle;
	}

	public static boolean isUpperLimitReached() {
		return RobotSensors.pickupSystemUpLim.get();
	}

	public static boolean isLowerLimitReached() {
		return RobotSensors.pickupSystemDownLim.get();
	}

	public static boolean isBallInPickup() {
		return RobotSensors.ballReadyToLiftLim.get();
	}

	public static void movePickupToAngle(double givenAngle) {
           armTargetAngle = givenAngle;
	}

	public static void moveToPickupPosition() {
            pidController.disable();
            movePickupToAngle(PICKUP_POSITION);
	}

	public static void moveToShootPosition() {
            pidController.setSetpoint(SHOOT_POSITION);
            movePickupToAngle(SHOOT_POSITION);
            pidController.enable();
	}

	public static void moveToTrussPosition() {
            pidController.disable();
            movePickupToAngle(TRUSS_POSITION);
	}

	public static void moveToCatchPosition() {
            pidController.disable();
            movePickupToAngle(CATCH_POSITION);
	}

	public static boolean isPickupInPosition() {
		return Math.abs(getArmAngleAboveHorizontal() - armTargetAngle) < ANGLE_TOLERANCE;
	}

	public static boolean isPickupInPosition(double angle) {
		return Math.abs(getArmAngleAboveHorizontal() - angle) < ANGLE_TOLERANCE && armTargetAngle == angle && Math.abs(getVelocity()) < 50;
	}

	public static boolean isPickupInPickupPosition() {
		return isPickupInPosition(PICKUP_POSITION);
	}

	public static boolean isPickupInShootPosition() {
		return isPickupInPosition(SHOOT_POSITION);
	}

	public static boolean isPickupInTrussPosition() {
		return isPickupInPosition(TRUSS_POSITION);
	}

	public static boolean isPickupInCatchPosition() {
		return isPickupInPosition(CATCH_POSITION);
	}

	public static double getArmAngleAboveHorizontal() {
		// apply some function to this to convert to angle
		return RobotSensors.pickupPotentiometer.get() * 73.015 - 179.257;  // Competition robot
		// return RobotSensors.pickupPotentiometer.get() * 74.522 - 258.68; //Practice robot
	}

	public static void initialize() {
		timer = new Timer();
		timer.start();
                pidController.setInputRange(25, 55);
                pidController.setOutputRange(-.35, .35);
                pidController.setPercentTolerance(5);
                pidController.disable();
	}

	public static void update() {
            System.out.println(pidController.isEnable());
            if(!pidController.isEnable()){
		double now = timer.get();
		if (now - lastTime < 0.2) {
			velocity = 0.5 * velocity + 0.5 * (getArmAngleAboveHorizontal() - lastPosition) / (now - lastTime);
		} else {
			// been more than 0.2 seconds since last time
			// so assumed it's stopped
			velocity = 0;
		}
		lastTime = now;
		lastPosition = getArmAngleAboveHorizontal();

		double mechSpeed = 0.0;
		double targetAngleDifference = armTargetAngle - getArmAngleAboveHorizontal();
		double targetSpeed = Math.min(1.0, Math.max(-0.75, targetAngleDifference / 15)) * 0.3;
		// double targetSpeed = Math.min(1.0,Math.max(-0.75,targetAngleDifference / 15)) * 0.3;

		if (Math.abs(targetAngleDifference) < 3.5 + (armTargetAngle < 0 ? 3 : 0)) {
			targetSpeed = 0;
		}

		if (targetAngleDifference > 0) {
			targetSpeed *= 1.5;
		}

		if (lastPosition > 80 && armTargetAngle < 80) {
			targetSpeed *= 1.8;
		}

		if (armTargetAngle > -10 && lastPosition < 0) {
			targetSpeed = 1;
		}

		//double targetSpeed = -Math.max(-30 / 2.5, Math.min(30 / 2.5, armTargetAngle - getArmAngleAboveHorizontal())) * 2.5 / 100.0;
		//negative because down is positive and up is negative

		double amt = -targetSpeed; // since up is negative

		if (amt < 0 && (!isUpperLimitReached())) {
			mechSpeed = amt;
		}
		if (amt > 0 && (!isLowerLimitReached())) {
			mechSpeed = amt;
		}

		RobotActuators.pickupMechMotor.set(mechSpeed);
            }
	}

	public static double getVelocity() {
		return velocity;
	}
}
