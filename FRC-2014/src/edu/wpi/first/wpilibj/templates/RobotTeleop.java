/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

/**
 *
 * @author Nathan
 */
public class RobotTeleop {

	private static int cap_mode = 0;
	private static final int cap_limit = 0;
	private static final int cap_round = 1;
	static double fine_speed = 0.0;

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

		double leftPWM = RobotDrive.pwmFromTPS(leftDrive * 900);
		double rightPWM = RobotDrive.pwmFromTPS(rightDrive * 900);
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

		r = (r + 0.001) % 1.0; // used for SmartDashboard control

		// End Drive Control

		// Robot Pickup Control:

		// both can control it, potentially fighting with each other
		// care must be taken here
		//TODO: check if sign is correct
		RobotPickup.setRollerSpeed(Gamepad.primary.getRightY() + Gamepad.secondary.getLeftY());

		RobotPickup.adjustArmAngle(Gamepad.secondary.getTriggers());

		RobotPickup.overrideEncoder(Gamepad.secondary.getBack());
		RobotPickup.setOverrideSpeed(Gamepad.secondary.getTriggers() / 3.0);


		if (Gamepad.secondary.getY()) {
			RobotPickup.openRollerArm();
		} else if (Gamepad.secondary.getX()) {
			RobotPickup.closeRollerArm();
		} else {
			RobotPickup.neutralRollerArm();
		}



		//TODO: make robotshoot possible to use


	}
	public static double r = 0.0;
}
