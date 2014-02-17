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
public class StandardTwoBallAuton {
	//// VARIABLES -------------------------------------------------------------
	public static Timer timer;
	public static double averageDriveEncoder;
	public static final double STRAIGHT_DISTANCE = 50;
	public static final double speed = 0.5;
	public static double startMovingBack;
	public static int step;

	// init method
	public static void initialize() {
		step = 1;
		averageDriveEncoder = 0.0;
		timer = new Timer();
		startMovingBack = 0.0;
	}

	// step one
	public static void stepOne() {
		RobotPickup.moveToShootPosition();
		if (timer.get() == 0.0) {
			timer.start();
		}
		if (averageDriveEncoder <= STRAIGHT_DISTANCE) {
			double forward = speed * Math.max(-1, Math.min(1, (STRAIGHT_DISTANCE - averageDriveEncoder) / 1000.0)) + .2;
			RobotDrive.driveSetRaw(forward, forward);
		} else {
			RobotDrive.driveSetRaw(0, 0);
		}

		if (averageDriveEncoder >= STRAIGHT_DISTANCE && RobotPickup.isPickupInShootPosition()) {
			step = 2;
		}
	}

	// step two
	public static void stepTwo() {
		if (RobotVision.isHot() || timer.get() >= 5) {
			RobotShoot.shoot();
			startMovingBack = timer.get() + 0.5;
			step = 3;
		}
	}
	
	// step three
	public static void stepThree() {
		if (startMovingBack <= timer.get()) {
			step = 4;
		}
	}
	
	// step Four
	public static void stepFour() {
		RobotPickup.moveToPickupPosition();
		if (RobotPickup.isPickupInPickupPosition()) {
			step = 5;
		}
	}
	
	// step Five
	public static void stepFive() {
	}
}
