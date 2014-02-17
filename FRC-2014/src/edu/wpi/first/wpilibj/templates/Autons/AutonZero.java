/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates.Autons;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.templates.*;

/**
 *
 * @author Tyler
 */
public class AutonZero {
	
	public static Timer timer;
	public static double fallTimer = 0.25;
	public static double closeTimer = 0.5;
	public static int step;
	private static boolean beenThru = false;
	
	public static void initialize() {
		timer = new Timer();
		step = 1;
	}
	
	// reset all Encoders
	public static void reset() {
		RobotSensors.leftDriveEncoder.reset();
		RobotSensors.rightDriveEncoder.reset();
		RobotSensors.shooterWinchEncoder.reset();
	}
	
	public static void stepOne() {
		timer.start();
		if (timer.get() <= closeTimer) {
			RobotPickup.openRollerArm();
		} else {
			RobotPickup.closeRollerArm();
		}
		if (!beenThru && RobotShoot.unwind()) {
			beenThru = true;
		}
		if (timer.get() > closeTimer && timer.get() >= fallTimer + closeTimer) {
			step = 2;
		}
	}
	
	public static void update() {
		StandardOneBallAuton.update();
	}
}
