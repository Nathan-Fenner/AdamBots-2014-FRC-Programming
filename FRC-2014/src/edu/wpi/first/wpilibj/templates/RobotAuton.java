/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Timer;


/**
 *
 * @author Tyler
 */
public class RobotAuton {
    
    //// VARIABLES -------------------------------------------------------------
    public static double averageEncoder;
    public static double distanceMultiplier;
    public static final double STRAIGHT_DISTANCE = 50; // needs to be found in testing
    public static final int SHOOTER_TOLERANCE = 2;
    public static boolean movingBack;
    public static boolean atPosition;
    public static boolean shot;
    public static Timer time;
    
    // initiates everything
    public static void initiate() {
	averageEncoder = 0.0;
	time = new Timer();
	time.start();
    }
    
    // does auton #1
    public static void autonOne() {
	atPosition = (STRAIGHT_DISTANCE < averageEncoder);
	if (!atPosition && !movingBack) {
	    RobotDrive.driveStraight(1.0);
	    RobotPickup.moveToCatchPosition();
	} else if (atPosition && !movingBack) {
	    RobotDrive.stop();
	    shoot();
	} else if (movingBack) {
	    RobotDrive.driveStraight(-1.0);
	}
	
	if (shot && distanceMultiplier * STRAIGHT_DISTANCE < averageEncoder) {
	    movingBack = true;
	}
    }
    
    // update method
    public static void update() {
	averageEncoder = (RobotSensors.leftDriveEncoder.get() + RobotSensors.rightDriveEncoder.get())/2.0;
    }
    
    // shoots
    private static void shoot() {
	if ((Vision.isHot() || time.get() >= 5) && pickupInRange()) {
	    RobotShoot.automatedShoot();
	}
	shot = true;
    }
    
    // tells whether or not the pickup system is in a tollerable range
    private static boolean pickupInRange() {
	return (RobotSensors.pickupSystemEncoder.get() + SHOOTER_TOLERANCE < RobotPickup.TARGET_SHOOT_POSITION && RobotSensors.pickupSystemEncoder.get() - SHOOTER_TOLERANCE > RobotPickup.TARGET_SHOOT_POSITION);
    }
}
