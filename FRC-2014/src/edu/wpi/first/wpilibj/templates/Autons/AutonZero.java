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
	public static double fallTimer = 2.0;
	public static double closeTime = 2.0;
	public static int step;
	private static boolean beenThru = false;
	private static boolean pickupBeenDown = false;
	
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
		/*if (!RobotPickup.isPickupInPickupPosition() && !pickupBeenDown) {
			RobotPickup.moveToPickupPosition();
		} else {
			pickupBeenDown = true;
		}
		
		if (pickupBeenDown) {
			RobotPickup.moveToShootPosition();
		}
		
		if (timer.get() == 0) {
			timer.start();
		}
		if (!beenThru *//*&& RobotShoot.unwind()*//*) {
			//System.out.println("Unwinding: " + timer.get());
			beenThru = true;
		}*/
		/*if (RobotShoot.unwind() && pickupBeenDown) {
			step = 2;
		}*/
		if (timer.get() == 0) {
			timer.start()ss;
		}
		
		RobotPickup.moveToPickupPosition();
		if (RobotShoot.isReadyToShoot()) {
			RobotShoot.shoot();
			step = 2;
		}
	}
	
	public static void update() {
		StandardOneBallAuton.update();
	}
}
