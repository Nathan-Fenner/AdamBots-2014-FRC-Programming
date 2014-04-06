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
	public static final int TENSION_VALUE = 1090; //downloaded to robot for Q26
	public static double openingTime = 0.5;
	public static double currentTime = 0.0;
	public static Timer secondTimer;

	// init
	public static void initialize() {
		AutonZero.initialize();
		AutonZero.reset();
		startMovingBack = 0.0;
		secondTimer = new Timer();
		RobotShoot.setTargetTicks(TENSION_VALUE);	// AUTON TARGET TICKS
	}

	// Moves forward while putting the arm down
	public static void stepTwo() {
		

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
		//// TODO : TAKE THE || true OUT!!!
		if (secondTimer.get() == 0 && RobotVision.isHot() && RobotShoot.isReadyToShoot()) {
			secondTimer.start();
		}
		if ((secondTimer.get() >= 0.5 || timer.get() >= 7.0) && RobotShoot.isReadyToShoot()) {
			secondTimer.stop();
			secondTimer.reset();
			RobotShoot.shoot();
			//FileWrite.writeFile("washot.txt", "\nhot: " + RobotVision.isHot() + "\nTime: " + timer.get());
			startMovingBack = timer.get() + 0.5;
			step = 99;		// IF YOU EVER WANT OT MOVE BACK AFTER SHOOTING IN AUTON, CHANGE TO step = 4;
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
		SmartDashboard.putBoolean("vision HOT GOAL", RobotVision.isHot());
	}
}
