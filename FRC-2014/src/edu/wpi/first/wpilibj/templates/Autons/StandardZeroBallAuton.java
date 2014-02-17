/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.Autons;

import edu.wpi.first.wpilibj.templates.*;

/**
 *
 * @author Tyler
 */
public class StandardZeroBallAuton extends AutonZero{

	//// VARIABLES -------------------------------------------------------------
	public static final double speed = 0.5;
	public static int step;
	public static double averageDriveEncoder;
	public static final double STRAIGHT_DISTANCE = 50;
	public static final double BACKWARDS_DISTANCE = -50;
	
	public static void intialize() {
		averageDriveEncoder = 0.0;
	}

	// Auton step one
	public static void stepTwo() {
		RobotPickup.moveToPickupPosition();
		if (averageDriveEncoder <= STRAIGHT_DISTANCE) {
			double forward = speed * Math.max(-1, Math.min(1, (STRAIGHT_DISTANCE - averageDriveEncoder) / 1000.0)) + .2;
			RobotDrive.driveSetRaw(forward, forward);
		} else {
			RobotDrive.driveSetRaw(0, 0);
			step = 3;
		}
	}

	// auton step two
	public static void stepThree() {
		step = 4;
	}

	// auton step three
	public static void stepFour() {
		if (averageDriveEncoder >= BACKWARDS_DISTANCE) {
			//Forward is negative, so actually, backwards, but counting in the direction of forwards.
			double forward = speed * Math.max(-1, Math.min(1, (BACKWARDS_DISTANCE - averageDriveEncoder) / 1000.0)) - .2;
			RobotDrive.driveSetRaw(forward, forward);
		} else {
			RobotDrive.driveSetRaw(0, 0);
			step = 5;
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
			default:
				break;
		}
	}
}
