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
public class StandardOneBallAuton extends AutonZero{
	//// VARIABLES -------------------------------------------------------------

	public static final double speed = 0.5;
	public static Timer timer;
	public static double startMovingBack;
	public static final double STRAIGHT_DISTANCE = 50; // needs to be found in testing
	public static final double BACKWARDS_DISTANCE = -50; // needs to be found in testing
	public static double averageDriveEncoder;

	// init
	public static void initialize() {
		startMovingBack = 0.0;
		timer = new Timer();
	}

	// Moves forward while putting the arm down
	public static void stepTwo() {
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
			step = 3;
		}
	}

	// doesnt do anything because it's covered in a method called from step 1
	public static void stepThree() {
		if (RobotVision.isHot() || timer.get() >= 5) {
			RobotShoot.shoot();
			startMovingBack = timer.get() + 0.5;
			step = 4;
		}
	}

	// shoots if the goal is hot or waits if the goal isnt
	public static void stepFour() {
		if (startMovingBack <= timer.get()) {
			step = 5;
		}
	}

	// moves back to the white line
	public static void stepFive() {
		if (averageDriveEncoder >= BACKWARDS_DISTANCE) {
			double forward = speed * Math.max(-1, Math.min(1, (BACKWARDS_DISTANCE - averageDriveEncoder) / 1000.0)) - .2;
			//Forward is negative, so actually, backwards, but counting in the direction of forwards.
			RobotDrive.driveSetRaw(forward, forward);
		} else {
			step = 6;
		}
	}

	// update method
	public static void update() {
		averageDriveEncoder = (RobotDrive.getEncoderLeftTicks() + RobotDrive.getEncoderRightTicks()) / 2.0;
		switch (step) {
			case 1:
				stepOne();
				break;
			case 2:
				stepTwo();
				break;
			case 3:
				stepThree();
				break;
			case 4:
				stepFour();
				break;
			case 5:
				stepFive();
				break;
			default:
				break;
		}
	}
}
