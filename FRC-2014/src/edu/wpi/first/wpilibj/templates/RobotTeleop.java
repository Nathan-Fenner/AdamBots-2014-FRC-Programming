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

	public static void update() {
		if (Gamepad.primary.getA()) {
			cap_mode = cap_limit;
		}
		if (Gamepad.primary.getB()) {
			cap_mode = cap_round;
		}
		System.out.println("Cap mode " + cap_mode);
		double forwardRate = Gamepad.primary.getTriggers();
		double turnRate = Gamepad.primary.getLeftX();
		double leftDrive = forwardRate + turnRate;
		double rightDrive = forwardRate - turnRate;
		// it is a problem if leftDrive or rightDrive has a magnitude exceeding 1.
		// two strategies: both will be tested to see how they work
		// ONE: cap them independently
		// TWO: cap magnitude to scale both down together
		if (cap_mode == cap_limit) {
			// cap both left and right drive
			leftDrive = Math.max(-1, Math.min(1, leftDrive));
			rightDrive = Math.max(-1, Math.min(1, rightDrive));
		}
		if (cap_mode == cap_round) {
			// find amount required to cap, then reduce by this amount
			double cap_scale = Math.max(1.0, Math.max(Math.abs(leftDrive), Math.abs(rightDrive)));
			leftDrive /= cap_scale;
			rightDrive /= cap_scale;
		}

		RobotDrive.drive(leftDrive, rightDrive);

	}
}
