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
public class StandardZeroBallAuton {
    //// VARIABLES -------------------------------------------------------------
    public static double speed;
    public static int step;
    public static boolean isAtSpot;
    public static boolean shooterInPosition;
    
    public StandardZeroBallAuton() {
	
    }
        
    // Auton step one
    public static void stepOne() {
	step = 1;
	isAtSpot = RobotAuton.driveDistance(RobotAuton.STRAIGHT_DISTANCE);
	shooterInPosition = RobotAuton.pickupToPosition(RobotPickup.SHOOT_POSITION, RobotPickup.TOLERANCE);
	if (isAtSpot && shooterInPosition) {
	    stepTwo();
	}
    }
    
    // auton step two
    public static void stepTwo() {
	step = 2;
	stepThree();
    }
    
    // auton step three
    public static void stepThree() {
	step = 3;
	RobotAuton.driveDistance(-(RobotAuton.STRAIGHT_DISTANCE + RobotAuton.DISTANCE_TO_TRUSS));
    }
    
    // drives to a certain distance and tells if it got there
    
    
    // Moves pickup mech to the shoot position and tells if it got there
    
    
    // update method
    public static void update() {
    }
}
