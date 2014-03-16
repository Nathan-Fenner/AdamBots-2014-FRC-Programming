package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Timer;

/**
 *
 * @author Nathan
 */
public class RobotPickup {

	private static final double ANGLE_TOLERANCE = 4; // much closer than before
	private static final double PICKUP_POSITION = -18;
	private static final double SHOOT_POSITION = 45.0;
	private static final double CATCH_POSITION = 90;
	private static double armTargetAngle = CATCH_POSITION;
	private static double lastPosition = 0.0;
	private static double velocity = 0.0;
	private static Timer timer;
	private static double lastTime = 0;

	public static double getArmTargetAngle() {
		return armTargetAngle;
	}

	public static double getVelocity() {
		return velocity;
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
		movePickupToAngle(PICKUP_POSITION);
	}

	public static void moveToShootPosition() {
		movePickupToAngle(SHOOT_POSITION);
	}

	public static void moveToCatchPosition() {
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

	public static boolean isPickupInCatchPosition() {
		return isPickupInPosition(CATCH_POSITION);
	}

	public static double getArmAngleAboveHorizontal() {
		// apply some function to this to convert to angle
		return RobotSensors.pickupPotentiometer.get() * 73.015 - 179.257; // Competition robot
		// return RobotSensors.pickupPotentiometer.get() * 74.522 - 258.68; //Practice robot
	}

	public static void initialize() {
		timer = new Timer();
		timer.start();
	}
	public static boolean rapidFall = true;

	public static void update() {

		double mechSpeed = 0.0;
		double targetAngleDifference = armTargetAngle - getArmAngleAboveHorizontal();
		double targetSpeed = 0;

		if (targetAngleDifference > 3.5) {
			targetSpeed = 0.8; // almost full throttle, upward
			rapidFall = false; // do not want to oscillate when coming down.
		}

		if (targetAngleDifference < -20) {
			rapidFall = true;
		}

		if (targetAngleDifference < -3.5) {
			// down
			if (targetAngleDifference < -10 && rapidFall) {
				// go fast
				targetSpeed = -0.8;
			} else {
				// go slow
				targetSpeed = -0.15;
			}

		}

		// be very careful: up is negative, so 'mechspeed' is inverted
		if (targetSpeed > 0 && !isUpperLimitReached()) {
			mechSpeed = -targetSpeed;
		}
		if (targetSpeed < 0 && !isLowerLimitReached()) {
			mechSpeed = -targetSpeed;
		}

		RobotActuators.pickupMechMotor.set(mechSpeed);

		// arm velocity calculations

		double now = timer.get();
		if (now - lastTime < 0.2) {
			velocity = 0.25 * velocity + 0.75 * (getArmAngleAboveHorizontal() - lastPosition) / (now - lastTime);
		} else {
			// been more than 0.2 seconds since last time
			// so assumed it's stopped
			velocity = 0;
		}
		lastTime = now;
		lastPosition = getArmAngleAboveHorizontal();


	}
}
