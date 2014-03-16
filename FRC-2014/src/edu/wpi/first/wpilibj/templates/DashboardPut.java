/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Blu
 */
public class DashboardPut {

	public static void put() {
		//Shooter diagnostics:
		SmartDashboard.putNumber("shooter WINCH CURRENT", RobotShoot.getCurrent());
		SmartDashboard.putBoolean("shooter IS AT BACK", !RobotSensors.shooterAtBack.get());
		SmartDashboard.putNumber("shooter EXPECT CURRENT", RobotShoot.getEncoder() * 0.01);
		SmartDashboard.putNumber("shooter ENCODER",RobotShoot.getEncoder());
		SmartDashboard.putBoolean("shooter MANUAL", RobotShoot.isManual());
		SmartDashboard.putBoolean("shoter TARGET MANUAL",MainRobot.targetInManualMode);
		//Pickup diagnostics:
		SmartDashboard.putNumber("pickup ARM ANGLE", RobotPickup.getArmAngleAboveHorizontal());
		SmartDashboard.putNumber("pickup ARM ANGLE TARGET", RobotPickup.getArmTargetAngle());
		SmartDashboard.putBoolean("pickup ARM LIMIT UPPER", RobotPickup.isUpperLimitReached());
		SmartDashboard.putBoolean("pickup ARM LIMIT LOWER", RobotPickup.isLowerLimitReached());
		//Drive
		//SmartDashboard.putBoolean("drive ESTOP",RobotDrive.isStopped());
		//General status
		DriverStation driverStation = DriverStation.getInstance();
		SmartDashboard.putNumber("status BATTERY", driverStation.getBatteryVoltage());
		SmartDashboard.putNumber("vision RED DISTANCE",RobotVision.redDistance());
		SmartDashboard.putNumber("vision BLUE DISTANCE",RobotVision.blueDistance());
		SmartDashboard.putNumber("vision DISTANCE",RobotVision.getDistance());

		SmartDashboard.putNumber("vision HOT NUMBER",RobotVision.getNumber("hot")); //this is on the robot

		// TESTING VARIABLES
		SmartDashboard.putNumber("pickup POTENTIOMETER", RobotSensors.pickupPotentiometer.get());
	}
}
