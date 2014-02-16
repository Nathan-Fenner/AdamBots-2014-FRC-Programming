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
public class StandardOneBallAuton {
    
    //// VARIABLES -------------------------------------------------------------
    public static double speed;
    public static int step;
    public static boolean isAtSpot;
    public static boolean shooterInPosition;
    public static Timer timer;
    public static double startMovingBack;
    public static final double STRAIGHT_DISTANCE = 50; // needs to be found in testing
    public static final double BACKWARDS_DISTANCE = -50; // needs to be found in testing
    
    public static double averageDriveEncoder;
    public static double pickupAngle;
    
    // init
    public static void initialize() {
	timer = new Timer();
	timer.start();
	startMovingBack = 0.0;
	speed = 0.0;
	step = 0;
    }
    
    // constructor
    public StandardOneBallAuton() {
	
    }
    
    // Moves forward while putting the arm down
    public static void stepOne() {
	if (averageDriveEncoder <= STRAIGHT_DISTANCE) {
	    RobotDrive.driveSetRaw(speed * ((averageDriveEncoder + 0.15) / STRAIGHT_DISTANCE),
		    speed * ((averageDriveEncoder + 0.15) / STRAIGHT_DISTANCE));	    
	} else {
	    speed = 0.0;
	}
	
	if (RobotPickup.isPickupInShootPosition()) {
	    RobotPickup.moveToShootPosition();
	}
	
	step = 1;
	if (averageDriveEncoder <= STRAIGHT_DISTANCE && RobotPickup.isPickupInShootPosition()) {
	    stepTwo();
	}
    }
    
    // doesnt do anything because it's covered in a method called from step 1
    public static void stepTwo() {
	step = 2;
	stepThree();
    }
    
    // shoots if the goal is hot or waits if the goal isnt
    public static void stepThree() {
	if (RobotVision.isHot() || timer.get() >= 5) {
	    RobotShoot.shoot();
	    startMovingBack = timer.get() + 0.5;
	}
	if (startMovingBack <= timer.get()) {
	    step = 3;
	    stepFour();
	}
	
    }
    
    // moves back to the white line
    public static void stepFour() {
	if (averageDriveEncoder >= BACKWARDS_DISTANCE) {
	    RobotDrive.driveSetRaw(-speed * ((averageDriveEncoder + 0.15) / BACKWARDS_DISTANCE), -speed * ((averageDriveEncoder + 0.15) / BACKWARDS_DISTANCE));
	}
	step = 4;
    }
    
    // update method
    public static void update() {
	averageDriveEncoder = (RobotDrive.getEncoderLeftTicks() + RobotDrive.getEncoderRightTicks())/2.0;
	pickupAngle = RobotPickup.getArmAngleAboveHorizontal();
    }
}
