/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.templates.RobotSensors;
import edu.wpi.first.wpilibj.templates.RobotActuators;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.templates.RobotSensors;
import edu.wpi.first.wpilibj.templates.RobotActuators;

/**
 *
 * @author Robin Onsay
 */
public class RobotDrive {
////VARIABLES-------------------------------------------------------------------	
	
    private static Timer time;
   
////CONSTANTS-------------------------------------------------------------------
    public static final double WHEEL_DIAMETER = 0.1624;/*In Meters*/
	private static double encoderR;
	private static double encoderL;
    public static final double NO_SPEED = 0.0;
    public static final double MAX_SPEED = 1.0;
    public static final double MIN_SPEED = -1.0;
////INIT------------------------------------------------------------------------    
	public static void initialize() {
        RobotSensors.RIGHT_DRIVE_ENCODER.start();
        RobotSensors.LEFT_DRIVE_ENCODER.start();
    }
////METHODS--------------------------------------------------------------------
	public static void update(){
		encoderL = RobotSensors.LEFT_DRIVE_ENCODER.get();
		encoderR = RobotSensors.RIGHT_DRIVE_ENCODER.get();
	}
	public static void distanceCorrection(double targetDist, double dist,double speed, double distPerTick){		
		
		double displacement = dist-targetDist;		
		double encoderValR =encoderL;
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
    public static void robotStop() {
            RobotActuators.LEFT_DRIVE.set(NO_SPEED);
            RobotActuators.RIGHT_DRIVE.set(NO_SPEED);
    }
}
