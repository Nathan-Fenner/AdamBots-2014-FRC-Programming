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

	static double fine_speed = 0.0;
	private static int pickupPosition = 1;
	private static boolean pickupPositionDebounce = false;
	private static boolean shootDebounce = false;
	public static double DEBUG_OSCILLATE = 0.0;

	public static double adjustInput(double x) {
		if (x < 0) {
			return -adjustInput(-x);
		}
		double a = 1.02058885;
		double b = 0.0039712;
		return (Math.sqrt(a * x + b) - Math.sqrt(b)) / Math.sqrt(a + b);
	}

	public static void update() {

		if (Gamepad.primary.getB()) {
			RobotDrive.shiftHigh();
		} else if (Gamepad.primary.getA()) {
			RobotDrive.shiftLow();
		}

		// Begin drive control

		double forwardRate = Gamepad.primary.getTriggers();
		double turnRate = Gamepad.primary.getLeftX() * 1;
		double leftDrive = forwardRate - turnRate;
		double rightDrive = forwardRate + turnRate;

		leftDrive = Math.max(-1.0, Math.min(1.0, leftDrive));
		rightDrive = Math.max(-1.0, Math.min(1.0, rightDrive));

		double leftPWM = RobotDrive.pwmFromTPS(leftDrive * 900);
		double rightPWM = RobotDrive.pwmFromTPS(rightDrive * 900);

		leftPWM = Math.max(-1.0, Math.min(1.0, leftPWM));
		rightPWM = Math.max(-1.0, Math.min(1.0, rightPWM));
		RobotDrive.drive(leftPWM, rightPWM);

		DEBUG_OSCILLATE = (DEBUG_OSCILLATE + 0.001) % 1.0; // used for SmartDashboard control

		// End Drive Control

		// Robot Pickup Control:

		// both can control it, potentially fighting with each other
		// care must be taken here
		RobotPickup.setRollerSpeed(Gamepad.primary.getRightY() + Gamepad.secondary.getLeftY());

		//RobotPickup.adjustArmAngle(Gamepad.secondary.getTriggers());


		if (Gamepad.secondary.getY()) {
			RobotPickup.openRollerArm();
		} else if (Gamepad.secondary.getX()) {
			RobotPickup.closeRollerArm();
		} else {
			RobotPickup.neutralRollerArm();
		}

		// added the false &&
		
		if (Math.abs(Gamepad.secondary.getLeftX()) > .1) {
			RobotPickup.manualAdjustment = true;
		}

		if (Gamepad.secondary.getLB() || Gamepad.secondary.getRB()) {
			RobotPickup.manualAdjustment = false;
			if (!pickupPositionDebounce) {
				if (Gamepad.secondary.getLB()) {
					pickupPosition--;
				}
				if (Gamepad.secondary.getRB()) {
					pickupPosition++;
				}
				pickupPosition = Math.max(0, Math.min(3, pickupPosition));
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
				RobotPickup.moveToTrussPosition();
				break;
			case 3:
				RobotPickup.moveToCatchPosition();
				break;
		}

		if (Math.abs(Gamepad.secondary.getTriggers()) > 0.9) {
			if (!shootDebounce) {
				System.out.println("Shoot!!!");
				RobotShoot.shoot();
			}
			shootDebounce = true;
		} else {
			shootDebounce = false;
		}


	}
}
