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
    
    // init
    public static void initialize() {
	timer = new Timer();
	timer.start();
	startMovingBack = 0.0;
    }
    
    // constructor
    public StandardOneBallAuton() {
	
    }
    
    // Moves forward while putting the arm down
    public static void stepOne() {
	step = 1;
	isAtSpot = RobotAuton.driveDistance(RobotAuton.STRAIGHT_DISTANCE);
	shooterInPosition = RobotAuton.pickupToPosition(RobotPickup.SHOOT_POSITION, RobotPickup.TOLERANCE);
	if (isAtSpot && shooterInPosition) {
	    stepTwo();
	}
    }
    
    // doesnt do anything because it's covered in a method called from step 1
    public static void stepTwo() {
	step = 2;
    }
    
    // shoots if the goal is hot or waits if the goal isnt
    public static void stepThree() {
	step = 3;
	if (RobotVision.isHot() || timer.get() >= 5) {
	    RobotAuton.shoot();
	    startMovingBack = timer.get() + 0.5;
	}
	if (startMovingBack <= timer.get()) {
	    stepFour();
	}
    }
    
    // moves back to the white line
    public static void stepFour() {
	step = 3;
	RobotAuton.driveDistance(-(RobotAuton.STRAIGHT_DISTANCE + RobotAuton.DISTANCE_TO_TRUSS));
    }
}
