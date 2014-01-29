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
    public static final double STOP = 0.0;
////INIT------------------------------------------------------------------------    
	public static void initialize() {
        RobotSensors.RIGHT_DRIVE_ENCODER.start();
        RobotSensors.LEFT_DRIVE_ENCODER.start();
    }
////TESTMETHOD------------------------------------------------------------------
	public static void test(){		
		RobotDrive.distanceCorrection(10,5,0.5,(Math.PI * 0.5)/360);
	}
////METHODS---------------------------------------------------------------------
	public static void update(){
		encoderL = RobotSensors.LEFT_DRIVE_ENCODER.get();
		encoderR = RobotSensors.RIGHT_DRIVE_ENCODER.get();
	}
	/*
		targetDist = feet
		dist = feet
		speed = voltage
		distPerTick = (PI*WHEELDIAMETER)/360
	*/
	public static void distanceCorrection(double targetDist, double dist,double speed, double distPerTick){		
		double displacement = dist-targetDist;		
		double encoderValR = encoderL;
		double encoderValL = encoderR;
		double encoderAvg = (encoderValR + encoderValL)/2;
		double distMeters = distPerTick * encoderAvg;
		if(distMeters == (1/10)*displacement)
			speed = (targetDist-distMeters)/targetDist;
		driveStraight(speed);
	}
	public static void driveStraight(double speed){
		RobotActuators.RIGHT_DRIVE.set(-speed);
		RobotActuators.LEFT_DRIVE.set(speed);
	}
	public static void drive(double rightSpeed, double leftSpeed){
		RobotActuators.RIGHT_DRIVE.set(rightSpeed);
		RobotActuators.LEFT_DRIVE.set(leftSpeed);
	}
	//100% 180 right, -100% 180 left
	public static void turn(double speed ,double percent/*Angle/360*/){
		if(percent >= 0){
			RobotActuators.RIGHT_DRIVE.set(speed);
			RobotActuators.LEFT_DRIVE.set(speed - percent);
		}else{
			RobotActuators.LEFT_DRIVE.set(speed - percent);
			RobotActuators.RIGHT_DRIVE.set(speed);
		}
	}
    public static void robotStop() {
            RobotActuators.LEFT_DRIVE.set(STOP);
            RobotActuators.RIGHT_DRIVE.set(STOP);
    }
}
