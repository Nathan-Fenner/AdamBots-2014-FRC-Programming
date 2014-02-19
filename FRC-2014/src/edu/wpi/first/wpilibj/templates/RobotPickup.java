package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Nathan
 */
public class RobotPickup {

	static double angle_K_P = -0.1;
	static double angle_K_I = 0.0;
	static double angle_K_D = 0.0;
	static double angle_I = 0.0;
	static double angle_I_DECAY = 0.9;
	private static final double ANGLE_TOLERANCE = 3;
	private static final double PICKUP_POSITION = -10;
	private static final double SHOOT_POSITION = 51.0;
	private static final double CATCH_POSITION = 90;
	private static double armTargetAngle = CATCH_POSITION;
	private static boolean overrideEncoder = false;
	private static double overrideSetValue = 0.0;
	private static boolean ignoreLimitSwitches = false;
	private static boolean armEnabled = true;
	private static Timer timer;
	private static double targetTweak = 0;

	public static void disableArm() {
		armEnabled = false;
	}

	public static void enableArm() {
		armEnabled = true;
	}

	public static void setOverrideSpeed(double speed) {
		overrideSetValue = speed;
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
		if (Math.abs(givenAngle - armTargetAngle) > 1) {
			angle_I = 0;
		}
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
		return Math.abs(getArmAngleAboveHorizontal() - angle) < ANGLE_TOLERANCE;
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
		return RobotSensors.pickupPotentiometer.get() * 73.015 - 39.0664;
		//return RobotSensors.pickupPotentiometer.get() * 74.522 - 258.68; //Practice robot
	}

	public static void initialize() {
		timer = new Timer();
		timer.start();
		SmartDashboard.putNumber("Adjust K", 1.0);
		SmartDashboard.putNumber("Adjust U", 1.0);
	}

	public static void overrideEncoder(boolean doOverride) {
		overrideEncoder = doOverride;
	}

	public static void setIgnoreLimits(boolean ignore) {
		ignoreLimitSwitches = ignore;
	}

	public static void enterOverrideEncoderMode() {
		overrideEncoder(true);
	}

	public static void exitOverrideEncoderMode() {
		overrideEncoder(false);
	}
	private static double lastError = 0.0;
	private static double lastAngle = 0.0;
	private static double lastTime = 0.0;
	private static double adjustRate = 0.0;
	static double ADJUST_K = 1.0;
	static double ADJUST_U = 1.0;

	public static void update() {
		double mechSpeed = 0.0;
		if (armEnabled) {
			if (overrideEncoder) {
				if ((!isUpperLimitReached() || ignoreLimitSwitches) && overrideSetValue > 0) {
					mechSpeed = overrideSetValue;
				}
				if ((!isLowerLimitReached() || ignoreLimitSwitches) && overrideSetValue < 0) {
					mechSpeed = overrideSetValue;
				}
			} else {

				ADJUST_K = 0; // SmartDashboard.getNumber("Adjust K");
				ADJUST_U = 2.5; // SmartDashboard.getNumber("Adjust U");
				double angleDifference = getArmAngleAboveHorizontal() - lastAngle;
				double timeDifference = timer.get() - lastTime;
				double degreesPerSecond = angleDifference / timeDifference;
				double lastTime = timer.get();

				lastAngle = getArmAngleAboveHorizontal();

				targetTweak = 0.5 * (armTargetAngle - getArmAngleAboveHorizontal());

				double targetDistance = armTargetAngle - getArmAngleAboveHorizontal();
				double targetSpeed = -Math.max(-30, Math.min(30, targetDistance * ADJUST_U));
				//negative because down is positive and up is negative



				adjustRate = -(targetSpeed - degreesPerSecond) * ADJUST_K / 1000.0;



				targetTweak = Math.max(-5, Math.min(5, targetTweak));

				SmartDashboard.putNumber("Target Tweak", targetTweak);

				adjustRate = Math.max(-1, Math.min(1, adjustRate));

				double amt = Math.max(-0.3, Math.min(0.3, adjustRate + targetSpeed / 100.0));

				if (amt < 0 && (!isUpperLimitReached() || ignoreLimitSwitches)) {
					mechSpeed = amt;
				}
				if (amt > 0 && (!isLowerLimitReached() || ignoreLimitSwitches)) {
					mechSpeed = amt;
				}

				SmartDashboard.putNumber("Target Speed", targetSpeed);
				SmartDashboard.putNumber("Adjust Rate", adjustRate);



				/*
				 double error = armTargetAngle - getArmAngleAboveHorizontal();

				 double errorDifference = error - lastError;

				 lastError = error;

				 SmartDashboard.putNumber("Angle Error", error);

				 SmartDashboard.putNumber("Error Integral", angle_I);

				 angle_K_P = -0.02;
				 angle_K_I = -0.1;
				 angle_K_D = 0;

				 angle_I_DECAY = SmartDashboard.getNumber("Angle I Decay");

				 double amt = error * angle_K_P + angle_I * angle_K_I / 1000.0 + angle_K_D * errorDifference;

				 angle_I += error;
				 angle_I *= angle_I_DECAY;

				 amt = Math.max(-1, Math.min(1, amt));

				 amt *= 0.5;


				 *
				 * */

				/*
				 double amt = Math.min(1,Math.abs( (getArmAngleAboveHorizontal() - armTargetAngle) / 10.0));
				 amt *= amt;
				 amt *= 0.2;
				 amt += 0.03;
				 if (getArmAngleAboveHorizontal() < armTargetAngle && (!isUpperLimitReached() || ignoreLimitSwitches)) {
				 mechSpeed = -1 * amt;
				 }
				 if (getArmAngleAboveHorizontal() > armTargetAngle && (!isLowerLimitReached() || ignoreLimitSwitches)) {
				 mechSpeed = 1 * amt;
				 }
				 */
			}
		}
		SmartDashboard.putNumber("Arm Target Angle", armTargetAngle);
		SmartDashboard.putNumber("Mech Speed", mechSpeed);
		SmartDashboard.putBoolean("upper limit arm", isUpperLimitReached());
		SmartDashboard.putBoolean("lower limit arm", isLowerLimitReached());
		SmartDashboard.putNumber("Angle", RobotTeleop.DEBUG_OSCILLATE / 800.0 + getArmAngleAboveHorizontal());
		RobotActuators.pickupMechMotor.set(mechSpeed);
	}
}