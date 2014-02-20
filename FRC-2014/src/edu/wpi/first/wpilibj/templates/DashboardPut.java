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
		SmartDashboard.putNumber("shooter WINCH CURRENT",RobotShoot.getCurrent());
		SmartDashboard.putBoolean("shooter IS AT BACK",RobotSensors.shooterAtBack.get());
		//SmartDashboard.putNumber("shooter EXPECT CURRENT", RobotShoot.getEncoder());
		//SmartDashboard.putBoolean("shooter MANUAL", RobotShoot.isManual());
		//Pickup diagnostics:
		SmartDashboard.putNumber("pickup ARM ANGLE",RobotPickup.getArmAngleAboveHorizontal());
		//SmartDashboard.putNumber("pickup ARM ANGLE TARGET",RobotPickup.getArmTarget());
		//SmartDashboard.putBoolean("pickup MANUAL",RobotPickup.isManual());
		//Drive
		//SmartDashboard.putBoolean("drive ESTOP",RobotDrive.isStopped());
		//General status
		DriverStation d = DriverStation.getInstance();
		SmartDashboard.putNumber("status BATTERY", d.getBatteryVoltage() );
	}
}
