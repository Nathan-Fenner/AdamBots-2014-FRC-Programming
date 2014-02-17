/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.Autons;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.templates.RobotDrive;
import edu.wpi.first.wpilibj.templates.RobotPickup;
import edu.wpi.first.wpilibj.templates.RobotSensors;
import edu.wpi.first.wpilibj.templates.RobotShoot;
import edu.wpi.first.wpilibj.templates.RobotVision;

/**
 *
 * @author Tyler
 */
public class RobotAuton {

	//// VARIABLES -------------------------------------------------------------
	public static double averageDriveEncoder;
	public static double distanceMultiplier;
	public static final double DRIVE_TOLERANCE = 2;
	public static final double DISTANCE_TO_TRUSS = 50;
	public static final int SHOOTER_TOLERANCE = 2;
	public static boolean movingBack;
	public static boolean atPosition;
	public static boolean shot;
	public static boolean switch1;
	public static boolean switch2;
	public static boolean switch3;
	public static int num;
	public static Timer time;

	// initiates everything
	public static void initiate() {
		averageDriveEncoder = 0.0;
		switch1 = RobotSensors.configSwitchA.getVoltage() >= 2.5;
		switch2 = RobotSensors.configSwitchB.getVoltage() >= 2.5;
		switch3 = RobotSensors.configSwitchC.getVoltage() >= 2.5;
		time = null;
		StandardOneBallAuton.initialize();
	}

	// update method
	public static void update() {
		// get values
		averageDriveEncoder = (RobotSensors.rightDriveEncoder.get() + RobotSensors.leftDriveEncoder.get()) / 2.0;
		StandardOneBallAuton.update();
	}

	// moves the pickup mech to the position and returns whether it got there or not
	/*public static boolean pickupToPosition(double spot, double tolerance) {
		RobotPickup.moveToShootPosition();
		return Math.abs(RobotPickup.getArmAngleAboveHorizontal() - spot) < tolerance;
	}

	// drives to a distance and tells if it got there or not
	public static boolean driveDistance(double spot) {
		if (averageDriveEncoder < spot + DRIVE_TOLERANCE && averageDriveEncoder > spot - DRIVE_TOLERANCE) {
			RobotDrive.driveStraight(0.0);
			return true;
		} else if (averageDriveEncoder < 0.8 * spot) {
			RobotDrive.driveStraight(1.0);
		} else if (averageDriveEncoder < spot) {
			RobotDrive.driveStraight(0.3);
		} else if (averageDriveEncoder > 0.8 * spot) {
			RobotDrive.driveStraight(-1.0);
		} else if (averageDriveEncoder > spot) {
			RobotDrive.driveStraight(-0.3);
		}
		return false;
	}

	public static boolean shoot() {
		if (time == null) {
			time = new Timer();
			time.start();
		} else {
			if (time.get() >= 0.3) {
				RobotShoot.automatedShoot();
				if (time.get() >= 0.5) {
					return true;
				}
			} else {
				RobotPickup.openRollerArm();
			}
		}
		return false;
	}

	public static boolean initialIntake() {
		if (time == null) {
			time = new Timer();
			time.start();
		} else if (time.get() <= 0.4) {
			RobotPickup.openRollerArm();
		} else {
			return true;
		}
		return false;
	}*/
}
