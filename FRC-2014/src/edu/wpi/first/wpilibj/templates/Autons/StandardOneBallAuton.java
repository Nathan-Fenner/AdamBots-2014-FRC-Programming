/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.Autons;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.*;

/**
 *
 * @author Tyler
 */
public class StandardOneBallAuton extends AutonZero {
	//// VARIABLES -------------------------------------------------------------

	public static final double speed = 0.5;
	public static double startMovingBack;
	public static final double STRAIGHT_DISTANCE = 450; // needs to be found in testing
	public static final double BACKWARDS_DISTANCE = 0; // needs to be found in testing
	public static double openingTime = 0.5;
	public static double currentTime = 0.0;
	public static Timer secondTimer;
	public static double averageDriveEncoder;

	// init
	public static void initialize() {
		AutonZero.initialize();
		AutonZero.reset();
		startMovingBack = 0.0;
		secondTimer = new Timer();
		RobotShoot.setTargetTicks(1000);	// AUTON TARGET TICKS
	}

	// Moves forward while putting the arm down
	public static void stepTwo() {
		RobotDrive.disableSmoothing();

		double forward = -1.0;
		if (averageDriveEncoder <= STRAIGHT_DISTANCE) {
			RobotDrive.drive(forward, forward);
		} else {
			RobotDrive.stopDrive();
			step = 3;
		}

		/*if (averageDriveEncoder <= STRAIGHT_DISTANCE) {
		 if (secondTimer.get() == 0) {
		 secondTimer.start();
		 }
		 if (secondTimer.get() <= 0.25) {
		 RobotPickup.setRollerSpeed(1.0);
		 } else {
		 RobotPickup.setRollerSpeed(0.0);
		 }
			
		 System.out.println("-->stage 2: drive speed = " + forward);
		 } else {
		 RobotDrive.drive(0, 0);
		 System.out.println("Setting to 0");

		 //		}*/
		/*if (RobotShoot.rewindShooter() && averageDriveEncoder >= STRAIGHT_DISTANCE && RobotPickup.isPickupInShootPosition()) {
		 step = 3;
		 }*/
	}

	// shoots if the goal is hot or timer says so
	public static void stepThree() {
		RobotPickup.openRollerArm();
		if (secondTimer.get() == 0 && RobotVision.isHot() && RobotShoot.isReadyToShoot()) {
			secondTimer.start();
		}
		if ((secondTimer.get() >= 0.5 || timer.get() >= 5.0) && RobotShoot.isReadyToShoot()) {
			secondTimer.stop();
			secondTimer.reset();
			RobotShoot.shoot();
			//FileWrite.writeFile("washot.txt", "\nhot: " + RobotVision.isHot() + "\nTime: " + timer.get());
			startMovingBack = timer.get() + 0.5;
			step = 4;
		}
	}

	// waits 0.5 seconds
	public static void stepFour() {
		if (startMovingBack <= timer.get()) {
			step = 5;
		}
	}

	// moves back to the white line
	public static void stepFive() {
		if (averageDriveEncoder >= BACKWARDS_DISTANCE) {
			//double forward = speed * Math.max(-1, Math.min(1, (BACKWARDS_DISTANCE - averageDriveEncoder) / 1000.0)) - .2;
			double forward = 1.0;
			RobotDrive.drive(forward, forward);
		} else {
			RobotDrive.stopDrive();
			step = 99;
		}
	}

	// update method
	public static void update() {
		//averageDriveEncoder = (RobotDrive.getEncoderLeftTicks() + RobotDrive.getEncoderRightTicks()) / 2.0;
		averageDriveEncoder = RobotDrive.getEncoderRightTicks();
		SmartDashboard.putBoolean("vision IS HOT", RobotVision.isHot());
		switch (step) {
			case 1:
				//System.out.println("Stage 1");
				stepOne();
				break;
			case 2:
				//System.out.println("Stage 2");
				stepTwo();
				break;
			case 3:
				//System.out.println("Stage 3");
				stepThree();
				break;
			//// CHANGED: COMMENTED OUT THE BELOW
			////		  SHOULD NOT MOVE BACKWARDS NOW
			/*case 4:
				//System.out.println("Stage 4");
				stepFour();
				break;
			case 5:
				//System.out.println("Stage 5");
				stepFive();
				break;*/
			default:
				break;
		}
	}
}
