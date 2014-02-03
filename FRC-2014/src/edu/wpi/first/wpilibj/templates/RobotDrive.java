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
////CONSTANTS-------------------------------------------------------------------

	public static final double WHEEL_DIAMETER = 0.5;//Feet
	private static double encoderR;//Single Channel
	private static double encoderL;//Single Channel	
	private static final double STOP = 0.0;
	private static double distanceToGo;
	//displacment = initial distance needed to travel to get to target
	private static double displacement;
	private static double encoderAvg;
////INIT------------------------------------------------------------------------    

	public static void initialize() {
		System.out.println("RobotDrive init");
		RobotSensors.rightDriveEncoder.start();
		RobotSensors.leftDriveEncoder.start();
		RobotSensors.rightDriveEncoder.setDistancePerPulse((Math.PI * 0.5) / 360);
		RobotSensors.leftDriveEncoder.setDistancePerPulse((Math.PI * 0.5) / 360);
	}
////TESTMETHOD------------------------------------------------------------------

	public static void test() {
		System.out.println("Encoder Left: " + encoderL + " Encoder Right: " + encoderR);
		distanceCorrection(10, 5, (Math.PI * 0.5) / 360);
		calcSpeed();
	}
////METHODS---------------------------------------------------------------------

	public static void update() {
		encoderL = RobotSensors.leftDriveEncoder.get();
                //encoderL = RobotSensors.rightDriveEncoder.get();
		encoderR = RobotSensors.rightDriveEncoder.get();
		encoderAvg = (encoderL + encoderR) / 2.0;
	}
	/*
	 Corrects distance	
	 targetDist = feet needed to be from Refrence point
	 dist = feet from Refrence point
	 speed = voltage
	 distPerTick = (PI*WHEELDIAMETER)/360
	 */

	public static void distanceCorrection(double targetDist, double dist, double distPerTick) {
		//distance in feet from start of encoder;
		double distanceTravled = encoderAvg * distPerTick;
		displacement = dist - targetDist;
		distanceToGo = Math.abs(displacement) - distanceTravled;
		if(distanceToGo == 0){
			return;
		}
	}

	public static void calcSpeed() {
		double rawSpeed = distanceToGo / displacement;
		//victor's can't take values less than 0.15
		double realSpeed = (rawSpeed >= 0.15 ? rawSpeed : 0.15);
		//sets the speed until displa
		if (displacement > 0.01) {
			driveStraight(displacement >= 0.01 ? realSpeed : -realSpeed);
		}
		if(distanceToGo==0){			
			robotStop();
			return;
		}
	}
	/*
	 sets victors to same value
	 */

	public static void driveStraight(double speed) {
		RobotActuators.rightDrive.set(-speed);
		RobotActuators.leftDrive.set(speed);
	}
	/*
	 sets victors to value in argument
	 */

	public static void drive(double rightSpeed, double leftSpeed) {
		RobotActuators.rightDrive.set(-rightSpeed);
		RobotActuators.leftDrive.set(leftSpeed);
	}

	//100% 180 right, -100% 180 left
	//percent in decimal
	public static void turn(double speed, double percent) {
		if (percent >= 0) {
			RobotActuators.rightDrive.set(-speed);
			RobotActuators.leftDrive.set(speed - percent);
		} else {
			RobotActuators.leftDrive.set(speed);
			RobotActuators.rightDrive.set(-speed + percent);
		}
	}
	/*
	 sets victors to zero
	 */

	public static void robotStop() {
		RobotActuators.leftDrive.set(STOP);
		RobotActuators.rightDrive.set(STOP);
	}
        
        public static void joystickDrive(/*double leftBumper, double rightBumper, double leftJoy, boolean gearShift*/) {
            double bumpers = FancyJoystick.primary.getDeadAxis(FancyJoystick.AXIS_TRIGGERS);
            //double rightBumper = RobotSensors.fancyJoy.getRawAxis(6);
            double leftJoy = FancyJoystick.primary.getDeadAxis(FancyJoystick.AXIS_LEFT_X);
            boolean gearShift = FancyJoystick.primary.getRawButton(3); //3 -> X Button
            boolean stopDrive = FancyJoystick.primary.getRawButton(4); //4 -> Y Button
            RobotPneumatics.shiftIt(gearShift);
            if(stopDrive){
                robotStop();
            }
            else 
            {
            if(bumpers != 0)
            {
                RobotActuators.leftDrive.set(bumpers + leftJoy);
                RobotActuators.rightDrive.set(-(bumpers - leftJoy));
            }
            else if(bumpers == 0 && leftJoy != 0)
            {
                RobotActuators.leftDrive.set(-leftJoy);
                RobotActuators.rightDrive.set(-(-(-leftJoy))); //:)
            }
            else
            {
                robotStop();
            }
                    
            }
        }
}
