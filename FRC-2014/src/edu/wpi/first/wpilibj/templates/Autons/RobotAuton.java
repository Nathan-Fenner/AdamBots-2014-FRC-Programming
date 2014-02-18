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
	//public static double averageDriveEncoder;
	public static double distanceMultiplier;
	public static final double DRIVE_TOLERANCE = 2;
	public static final double DISTANCE_TO_TRUSS = 50;
	public static final int SHOOTER_TOLERANCE = 2;
	public static boolean movingBack;
	public static boolean atPosition;
	public static boolean shot;
	public static boolean switch1;
	public static boolean switch2;
	public static boolean switch3;
	public static int num;
	public static Timer time;

	// initiates everything
	public static void initialize() {
		//averageDriveEncoder = 0.0;
		switch1 = RobotSensors.configSwitchA.getVoltage() >= 2.5;
		switch2 = RobotSensors.configSwitchB.getVoltage() >= 2.5;
		switch3 = RobotSensors.configSwitchC.getVoltage() >= 2.5;
		time = null;
		StandardOneBallAuton.initialize();
	}

	// update method
	public static void update() {
		// get values
		//averageDriveEncoder = (RobotSensors.rightDriveEncoder.get() + RobotSensors.leftDriveEncoder.get()) / 2.0;
		//StandardOneBallAuton.update();
		AutonZero.update();
		RobotDrive.update();
		RobotPickup.update();
		RobotShoot.update();
	}
}
