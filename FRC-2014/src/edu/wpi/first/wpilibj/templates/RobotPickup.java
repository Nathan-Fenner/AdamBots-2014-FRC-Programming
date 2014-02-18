package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
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
	private static final double ANGLE_TOLERANCE = 2;
	private static final double PICKUP_POSITION = -10;
	private static final double SHOOT_POSITION = 51.0;
	private static final double CATCH_POSITION = 90;
	private static double armTargetAngle = CATCH_POSITION;
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
		return RobotSensors.pickupPotentiometer.get() * 74.522 - 258.68;
	}

	public static void initialize() {
		SmartDashboard.putNumber("Angle K P", -0.01);
		SmartDashboard.putNumber("Angle K I", -0.5);
		SmartDashboard.putNumber("Angle K D", 0.0);
		SmartDashboard.putNumber("Angle I Decay", 0.9);
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
			double error = armTargetAngle - getArmAngleAboveHorizontal();

			double errorDifference = error - lastError;

			lastError = error;

			SmartDashboard.putNumber("Angle Error",error);

			SmartDashboard.putNumber("Error Integral",angle_I);

			angle_K_P = -0.02;
			angle_K_I = -0.1;
			angle_K_D = 0;

			angle_I_DECAY = SmartDashboard.getNumber("Angle I Decay");

			double amt = error * angle_K_P + angle_I * angle_K_I / 1000.0 + angle_K_D * errorDifference;

			angle_I += error;
			angle_I *= angle_I_DECAY;

			amt = Math.max(-1, Math.min(1, amt));

			amt *= 0.5;

			if (amt < 0 && (!isUpperLimitReached() || ignoreLimitSwitches)) {
				mechSpeed = amt;
			}
			if (amt > 0 && (!isLowerLimitReached() || ignoreLimitSwitches)) {
				mechSpeed = amt;
			}

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
		SmartDashboard.putNumber("Arm Target Angle", armTargetAngle);
		SmartDashboard.putNumber("Mech Speed", mechSpeed);
		SmartDashboard.putBoolean("upper limit arm", isUpperLimitReached());
		SmartDashboard.putBoolean("lower limit arm", isLowerLimitReached());
		SmartDashboard.putNumber("Angle", RobotTeleop.DEBUG_OSCILLATE / 800.0 + getArmAngleAboveHorizontal());
		RobotActuators.pickupMechMotor.set(mechSpeed);
	}
}