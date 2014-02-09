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
    public static final double STRAIGHT_DISTANCE = 50; // needs to be found in testing
    public static final double DISTANCE_TO_TRUSS = 50;
    public static final int SHOOTER_TOLERANCE = 2;
    public static boolean movingBack;
    public static boolean atPosition;
    public static boolean shot;
    public static Timer time;
    
    // initiates everything
    public static void initiate() {
	averageDriveEncoder = 0.0;
	time = null;
    }
    
    // update method
    public static void update() {
	// get values
	averageDriveEncoder = (RobotSensors.rightDriveEncoder.get() + RobotSensors.leftDriveEncoder.get())/2.0;
    }
    
    // moves the pickup mech to the position and returns whether it got there or not
    public static boolean pickupToPosition(double spot, double tolerance) {
	RobotPickup.moveToShoot();
	return (RobotPickup.armEncoder > spot - tolerance && RobotPickup.armEncoder < spot + tolerance);
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
		RobotPickup.liftRollerArm();
	    }
	}
	return false;
    }
}
