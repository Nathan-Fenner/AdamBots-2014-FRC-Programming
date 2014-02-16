/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Nathan
 */
public class RobotTeleop {

	private static int cap_mode = 0;
	private static final int cap_limit = 0;
	private static final int cap_round = 1;
	static double fine_speed = 0.0;
	private static int pickupPosition = 0;
	private static boolean pickupPositionDebounce = false;
	private static boolean catchClosing = false;
	private static boolean catchClosingDebounce = false;
	private static boolean shootDebounce = false;
	
	public static double DEBUG_OSCILLATE = 0.0;

	public static void update() {

		if (Gamepad.primary.getB()) {
			RobotDrive.shiftHigh();
		} else if (Gamepad.primary.getA()) {
			RobotDrive.shiftLow();
		}

		// Begin drive control

		double forwardRate = Gamepad.primary.getTriggers();
		double turnRate = Gamepad.primary.getLeftX();
		double leftDrive = forwardRate - turnRate;
		double rightDrive = forwardRate + turnRate;

		SmartDashboard.putNumber("Left Drive Sum", leftDrive);
		SmartDashboard.putNumber("Right Drive Sum", rightDrive);

		double leftPWM = RobotDrive.pwmFromTPS(leftDrive * 900);
		double rightPWM = RobotDrive.pwmFromTPS(rightDrive * 900);

		SmartDashboard.putNumber("Left Curve Value", leftPWM);
		SmartDashboard.putNumber("Right Curve Value", rightPWM);

		// it is a problem if leftDrive or rightDrive has a magnitude exceeding 1.
		// two strategies: both will be tested to see how they work
		// ONE: cap them independently
		// TWO: cap magnitude to scale both down together
		if (cap_mode == cap_limit) {
			// cap both left and right drive
			leftPWM = Math.max(-1.0, Math.min(1.0, leftPWM));
			rightPWM = Math.max(-1.0, Math.min(1.0, rightPWM));
		}
		if (cap_mode == cap_round) {
			// find amount required to cap, then reduce by this amount
			double cap_scale = Math.max(1.0, Math.max(Math.abs(leftPWM), Math.abs(rightPWM)));
			leftPWM /= cap_scale;
			rightPWM /= cap_scale;
		}
		RobotDrive.drive(leftPWM, rightPWM);

		DEBUG_OSCILLATE = (DEBUG_OSCILLATE + 0.001) % 1.0; // used for SmartDashboard control

		// End Drive Control

		// Robot Pickup Control:

		// both can control it, potentially fighting with each other
		// care must be taken here
		RobotPickup.setRollerSpeed(Gamepad.primary.getRightY() + Gamepad.secondary.getLeftY());

		//RobotPickup.adjustArmAngle(Gamepad.secondary.getTriggers());

		RobotPickup.overrideEncoder(Gamepad.secondary.getBack());
		RobotPickup.setOverrideSpeed(Gamepad.secondary.getTriggers() / 3.0);


		if (Gamepad.secondary.getY()) {
			RobotPickup.openRollerArm();
		} else if (Gamepad.secondary.getX()) {
			RobotPickup.closeRollerArm();
		} else {
			RobotPickup.neutralRollerArm();
		}

		if (Gamepad.secondary.getLB() || Gamepad.secondary.getRB()) {
			if (!pickupPositionDebounce) {
				if (Gamepad.secondary.getLB()) {
					pickupPosition--;
				}
				if (Gamepad.secondary.getRB()) {
					pickupPosition++;
				}
				pickupPosition = Math.max(0, Math.min(2, pickupPosition));
			}
			pickupPositionDebounce = true;
		} else {
			pickupPositionDebounce = false;
		}

		switch (pickupPosition) {
			case 0:
				RobotPickup.moveToPickupPosition();
				break;
			case 1:
				RobotPickup.moveToShootPosition();
				break;
			case 2:
				RobotPickup.moveToCatchPosition();
				break;
		}

		if (Gamepad.secondary.getA()) {
			if (!catchClosingDebounce) {
				catchClosing = false;
			}
			catchClosingDebounce = true;
			double col = RobotVision.highBlueBall();
			if (col > 5) {
				catchClosing = true;
			}
			if (catchClosing) {
				RobotPickup.closeRollerArm();
			}
		} else {
			catchClosingDebounce = false;
		}

		if (Math.abs(Gamepad.secondary.getTriggers()) > 0.9) {
			if (!shootDebounce) {
				RobotShoot.shoot();
			}
			shootDebounce = true;
		} else {
			shootDebounce = false;
		}

	}
}
