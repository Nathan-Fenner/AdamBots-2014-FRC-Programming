/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.drive;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.templates.RobotSensors;
import edu.wpi.first.wpilibj.templates.RobotActuators;

/**
 *
 * @author Robin Onsay
 */
public class Drive {
////VARIABLES-------------------------------------------------------------------	
	public static double targetDistance;
	public static double currentDistance;
    private static Timer time;
   
////CONSTANTS-------------------------------------------------------------------
    public static final double WHEEL_DIAMETER = 0.1624;
    public static final double NO_SPEED = 0.0;
    public static final double MAX_SPEED = 1.0;
    public static final double MIN_SPEED = -1.0;
////INIT------------------------------------------------------------------------    
	public static void initialize() {
        RobotSensors.RIGHT_DRIVE_ENCODER.start();
        RobotSensors.LEFT_DRIVE_ENCODER.start();
    }

	public static void driveStraight(double speed){
		RobotActuators.rightDrive.set(-speed);
		RobotActuators.leftDrive.set(speed);
	}
    public static void robotStop() {
            RobotActuators.leftDrive.set(NO_SPEED);
            RobotActuators.rightDrive.set(NO_SPEED);
    }
}
