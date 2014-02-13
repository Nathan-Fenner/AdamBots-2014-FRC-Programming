package edu.wpi.first.wpilibj.templates;

/**
 *
 * @author Nathan
 */

public class RobotPickup {

	private static final double ANGLE_TOLERANCE = 0.02;
	private static final double PICKUP_POSITION = 30;
	private static final double SHOOT_POSITION = 60;
	private static final double CATCH_POSITION = 90;
	private static double armTargetAngle = 0.0;
	private static boolean overrideEncoder = false;
	private static double overrideSetValue = 0.0;
	private static boolean ignoreLimitSwitches = false;

	public static void setOverrideSpeed(double speed) {
		overrideSetValue = speed;
	}

	public static void openRollerArm() {
		RobotActuators.rollerArmUp.set(true);
		RobotActuators.rollerArmDown.set(false);
	}

	public static void closeRollerArm() {
		RobotActuators.rollerArmUp.set(false);
		RobotActuators.rollerArmDown.set(true);
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
		return Math.abs(armEncoderPosition() - armTargetAngle) < ANGLE_TOLERANCE;
	}

	public static double armEncoderPosition() {
		// apply some function to this to convert to angle
		return RobotSensors.pickupPotentiometer.get();
	}

	public static void initialize() {
		// currently, nothing to intialize
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

	public static void update() {
		double mechSpeed = 0.0;
		if (overrideEncoder) {
			if ((!isUpperLimitReached() || ignoreLimitSwitches) && overrideSetValue > 0) {
				mechSpeed = overrideSetValue;
			}
			if ((!isLowerLimitReached() || ignoreLimitSwitches) && overrideSetValue < 0) {
				mechSpeed = overrideSetValue;
			}
		} else {
			if ((!isUpperLimitReached() || ignoreLimitSwitches) && armEncoderPosition() < armTargetAngle - ANGLE_TOLERANCE) {
				mechSpeed = 0.1;
			}
			if ((!isLowerLimitReached() || ignoreLimitSwitches) && armEncoderPosition() > armTargetAngle + ANGLE_TOLERANCE) {
				mechSpeed = -0.1;
			}
		}
		RobotActuators.pickupMechMotor.set(mechSpeed);
	}
}