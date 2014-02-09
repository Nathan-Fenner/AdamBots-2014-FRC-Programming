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

		//System.out.println("Cap mode " + cap_mode);
		double forwardRate = Gamepad.primary.getTriggers();


		double turnRate = Gamepad.primary.getLeftX();
		double leftDrive = forwardRate - turnRate;
		double rightDrive = forwardRate + turnRate;
		// it is a problem if leftDrive or rightDrive has a magnitude exceeding 1.
		// two strategies: both will be tested to see how they work
		// ONE: cap them independently

		//RobotActuators.pickupSystemMotor.set(0.3);
		//RobotActuators.pickupRollerArmMotor.set(0.3);
		// TWO: cap magnitude to scale both down together
		if (cap_mode == cap_limit) {
			// cap both left and right drive
			leftDrive = Math.max(-1.0, Math.min(1.0, leftDrive));
			rightDrive = Math.max(-1.0, Math.min(1.0, rightDrive));
		}
		if (cap_mode == cap_round) {
			// find amount required to cap, then reduce by this amount
			double cap_scale = Math.max(1.0, Math.max(Math.abs(leftDrive), Math.abs(rightDrive)));
			leftDrive /= cap_scale;
			rightDrive /= cap_scale;
		}
		RobotDrive.drive(leftDrive, rightDrive);



		SmartDashboard.putNumber("left drive", leftDrive + (r = (r + 0.001) % 1.0) / 800.0);
		RobotDrive.driveSetRaw(leftDrive, rightDrive);

		// End Drive Control

		//TODO: check if sign is correct
		RobotPickup.moveGamePiece(Gamepad.primary.getRightY() + Gamepad.secondary.getRightY());
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
