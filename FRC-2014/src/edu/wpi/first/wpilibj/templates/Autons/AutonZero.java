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
	public static boolean switch1;
	public static boolean switch2;
	public static boolean switch3;
	public static boolean beenThru;
	
	public static void initialize() {
		timer = new Timer();
		step = 1;
	}
	
	// reset all Encoders
	public void reset() {
		RobotSensors.leftDriveEncoder.reset();
		RobotSensors.rightDriveEncoder.reset();
		RobotSensors.shooterWinchEncoder.reset();
		switch1 = RobotSensors.configSwitchA.getVoltage() >= 2.5;
		switch2 = RobotSensors.configSwitchB.getVoltage() >= 2.5;
		switch3 = RobotSensors.configSwitchC.getVoltage() >= 2.5;
	}
	
	public static void stepOne() {
		timer.start();
		if (timer.get() <= closeTimer) {
			RobotPickup.openRollerArm();
		} else {
			RobotPickup.closeRollerArm();
		}
		if (!beenThru) {
			RobotShoot.unwind();
			beenThru = true;
		}
		if (timer.get() > closeTimer && timer.get() >= fallTimer + closeTimer) {
			step = 2;
		}
	}
}
