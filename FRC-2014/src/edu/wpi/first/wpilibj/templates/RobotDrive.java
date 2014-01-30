/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;

/**
 *
 * @author Robin Onsay
 */
public class RobotDrive {
////VARIABLES-------------------------------------------------------------------

	private static Timer time;
////CONSTANTS-------------------------------------------------------------------
	public static final double WHEEL_DIAMETER = 0.5;//Feet
	private static double encoderR;//Single Channel
	private static double encoderL;//Single Channel
	private static final double encoderAvg = (encoderL + encoderR) / 2.0;
	public static final double STOP = 0.0;
////INIT------------------------------------------------------------------------    

	public static void initialize() {
		RobotSensors.RIGHT_DRIVE_ENCODER.start();
		RobotSensors.LEFT_DRIVE_ENCODER.start();
	}
////TESTMETHOD------------------------------------------------------------------

	public static void test() {
		RobotDrive.distanceCorrection(10, 5, (Math.PI * 0.5) / 360);
	}
////METHODS---------------------------------------------------------------------

	public static void update() {
		encoderL = RobotSensors.LEFT_DRIVE_ENCODER.get();
		encoderR = RobotSensors.RIGHT_DRIVE_ENCODER.get();
	}
	/*
	 Corrects distance	
	 targetDist = feet needed to be from Refrence point
	 dist = feet from Refrence point
	 speed = voltage
	 distPerTick = (PI*WHEELDIAMETER)/360
	 */

	public static void distanceCorrection(double targetDist, double dist, double distPerTick) {
		//displacment = initial distance needed to travel to get to target
		double displacement = dist - targetDist;
		//distance in feet from start of encoder;
		double currentDist = encoderAvg * distPerTick;
		//if displacement is +
		double posDiff = displacement - currentDist;
		//if displacement is -
		double negDiff = displacement + currentDist;
		double distToDisplace = (displacement > 0 ? posDiff : negDiff);
		// gets the speed as a percent error. Slows down as it get's closer
		double rawSpeed = distToDisplace / displacement;
		//victor's can't take values less than 0.15
		double realSpeed = (rawSpeed >= 0.15 ? rawSpeed : 0.15);
		//sets the speed until displa
		if(rawSpeed!=0){
			driveStraight(realSpeed >= 0 ? realSpeed : -realSpeed);
		}else{
			robotStop();
		}
	}
	/*
	 sets victors to same value
	 */

	public static void driveStraight(double speed) {
		RobotActuators.RIGHT_DRIVE.set(-speed);
		RobotActuators.LEFT_DRIVE.set(speed);
	}
	/*
	 sets victors to value in argument
	 */

	public static void drive(double rightSpeed, double leftSpeed) {
		RobotActuators.RIGHT_DRIVE.set(-rightSpeed);
		RobotActuators.LEFT_DRIVE.set(leftSpeed);
	}

	//100% 180 right, -100% 180 left
	//percent in decimal
	public static void turn(double speed, double percent) {
		if (percent >= 0) {
			RobotActuators.RIGHT_DRIVE.set(-speed);
			RobotActuators.LEFT_DRIVE.set(speed - percent);
		} else {
			RobotActuators.LEFT_DRIVE.set(speed);
			RobotActuators.RIGHT_DRIVE.set(-speed + percent);
		}
	}
	/*
	 sets victors to zero
	 */

	public static void robotStop() {
		RobotActuators.LEFT_DRIVE.set(STOP);
		RobotActuators.RIGHT_DRIVE.set(STOP);
	}
}
