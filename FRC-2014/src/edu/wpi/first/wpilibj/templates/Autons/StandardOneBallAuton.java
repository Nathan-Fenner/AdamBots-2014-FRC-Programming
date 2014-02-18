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
	public static double startMovingBack;
	public static final double STRAIGHT_DISTANCE = 150; // needs to be found in testing
	public static final double BACKWARDS_DISTANCE = -700; // needs to be found in testing
	public static double openingTime = 0.5;
	public static double currentTime = 0.0;
	public static double averageDriveEncoder;

	// init
	public static void initialize() {
		AutonZero.initialize();
		AutonZero.reset();
		startMovingBack = 0.0;
	}

	// Moves forward while putting the arm down
	public static void stepTwo() {
		if (averageDriveEncoder <= STRAIGHT_DISTANCE) {
			//double forward = speed * Math.max(-1, Math.min(1, (STRAIGHT_DISTANCE - averageDriveEncoder) / 1000.0)) + .2;
			double forward = 0.5;
			RobotDrive.drive(-forward, -forward);
			System.out.println("-->stage 2: drive speed = " + forward);
		} else {
			RobotDrive.drive(0, 0);
		}

		if (/*RobotShoot.rewindShooter() && */averageDriveEncoder >= STRAIGHT_DISTANCE && timer.get() >= 5.0 ) {
			step = 3;
		}
	}

	// doesnt do anything because it's covered in a method called from step 1
	public static void stepThree() {
		if (currentTime == 0.0) {
			RobotPickup.openRollerArm();
			currentTime = timer.get();
		}
		System.out.println(currentTime + openingTime - timer.get());
		if ((RobotVision.isHot() || timer.get() >= 5) && currentTime + openingTime - timer.get() >= 0.5) {
			//RobotShoot.shoot();
			System.out.println("Shoot");
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
			RobotDrive.drive(forward, forward);
		} else {
			step = 6;
		}
	}

	// update method
	public static void update() {
		//averageDriveEncoder = (RobotDrive.getEncoderLeftTicks() + RobotDrive.getEncoderRightTicks()) / 2.0;
		averageDriveEncoder = RobotDrive.getEncoderRightTicks();
		switch (step) {
			case 1:
				System.out.println("Stage 1");
				stepOne();
				break;
			case 2:
				System.out.println("Stage 2");
				stepTwo();
				break;
			case 3:
				System.out.println("Stage 3");
				stepThree();
				break;
			case 4:
				System.out.println("Stage 4");
				stepFour();
				break;
			case 5:
				System.out.println("Stage 5");
				stepFive();
				break;
			default:
				break;
		}
	}
}
