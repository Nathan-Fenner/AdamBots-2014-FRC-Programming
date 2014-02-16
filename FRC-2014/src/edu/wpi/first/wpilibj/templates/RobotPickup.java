package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Nathan
 */
public class RobotPickup {

	private static final double ANGLE_TOLERANCE = 2;
	private static final double PICKUP_POSITION = -15;
	private static final double SHOOT_POSITION = 45;
	private static final double CATCH_POSITION = 90;
	private static double armTargetAngle = 0;
	private static boolean overrideEncoder = false;
	private static double overrideSetValue = 0.0;
	private static boolean ignoreLimitSwitches = false;

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
		return RobotSensors.pickupPotentiometer.get() * 74.522 - 258.68;
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
			if (getArmAngleAboveHorizontal() < armTargetAngle && (!isUpperLimitReached() || ignoreLimitSwitches)) {
				mechSpeed = -0.25 * Math.min(1, Math.abs(getArmAngleAboveHorizontal() - armTargetAngle) / 25.0);
			}
			if (getArmAngleAboveHorizontal() > armTargetAngle && (!isLowerLimitReached() || ignoreLimitSwitches)) {
				mechSpeed = 0.2 * Math.min(1, Math.abs(getArmAngleAboveHorizontal() - armTargetAngle) / 25.0);
			}
		}
		SmartDashboard.putNumber("Angle", RobotTeleop.DEBUG_OSCILLATE / 800.0 + getArmAngleAboveHorizontal());
		RobotActuators.pickupMechMotor.set(mechSpeed);
	}
}