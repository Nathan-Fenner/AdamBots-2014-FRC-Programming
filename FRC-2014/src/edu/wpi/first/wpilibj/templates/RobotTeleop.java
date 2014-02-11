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

	public static void update() {

		if (Gamepad.primary.getX()) {
			// shift
		}
		if (Gamepad.primary.getY()) {
			// shift
		}

		// Begin drive control

		SmartDashboard.putNumber("Cap Mode (0:Limit, 1:Round)", cap_mode);
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
		//SmartDashboard.putNumber("left drive", leftDrive + (r = (r + 0.001) % 1.0) / 800.0);
		r = (r + 0.001) % 1.0;
		// End Drive Control

		//TODO: check if sign is correct
		//RobotPickup.moveGamePiece(Gamepad.primary.getRightY() + Gamepad.secondary.getRightY());
		// both can control it, if needed
		// perhaps come up with a better way


		//TODO: add override functionality to RobotPickup

		if (Gamepad.secondary.getY()) {
			RobotPickup.liftRollerArm();
		} else if (Gamepad.secondary.getX()) {
			RobotPickup.lowerRollerArm();
		} else {
			//RobotPickup.neutralRollerArm();
		}

		//TODO: make robotshoot possible to use


	}
	public static double r = 0.0;
}
