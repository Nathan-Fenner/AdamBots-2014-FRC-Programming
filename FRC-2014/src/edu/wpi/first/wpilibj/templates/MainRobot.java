/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.Autons.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class MainRobot extends IterativeRobot {

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		RobotActuators.initialize();
		RobotSensors.initialize();
		RobotDrive.initialize();
		RobotPickup.initialize();
		RobotShoot.initialize();
		RobotVision.initialize();
		RobotAuton.initialize();
		ControlBox.initialize();
		System.out.println("Initialized");
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		RobotShoot.useAutomatic();
		runCompressor();
		RobotAuton.update();
		RobotDrive.update();
		RobotPickup.update();													// TODO: UNDISABLE WHEN IT CAN DRIVE AGAIN
		RobotShoot.update();
		DashboardPut.put();
	}
	
	public void teleopInit() {
		SmartDashboard.putNumber("Target Ticks", 1000);
		RobotDrive.enableSmoothing();
	}
	
	public void disabledInit() {
		StandardOneBallAuton.timer.stop();
		StandardOneBallAuton.timer.reset();
		StandardOneBallAuton.secondTimer.stop();
		StandardOneBallAuton.secondTimer.reset();
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		if (RobotShoot.gameTime.get() == 0) {
			RobotShoot.gameTime.start();
		}
		
		SmartDashboard.putBoolean("shooter AUTO ENCODER", ControlBox.getTopSwitch(3));
		if (ControlBox.getTopSwitch(3)) {
			RobotShoot.setTargetTicks(RobotVision.getEncoder());
		} else {
			if (ControlBox.getBlackButtonLeft()) {
				RobotShoot.adjustTargetDown();
			}
			if (ControlBox.getBlackButtonRight()) {
				RobotShoot.adjustTargetUp();
			}
		}
		
		ControlBox.update();
		RobotDrive.update();
		RobotPickup.update();
		RobotShoot.update();
		
		RobotPickup.moveToShootPosition();
		
		RobotTeleop.update();
		if (!ControlBox.getTopSwitch(2)) {
			RobotShoot.useAutomatic();
		} else {
			RobotShoot.useManual();
		}
		
		if (Gamepad.secondary.getTriggers() > .9) {
			RobotShoot.shoot();
		}
		
		runCompressor();
		
		SmartDashboard.putNumber("Red Distance", RobotVision.redDistance());
		
		SmartDashboard.putNumber("Trigger Values", Math.abs(Gamepad.secondary.getTriggers()));
		
		DashboardPut.put();
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		DashboardPut.put();
		if (RobotPickup.isPickupInShootPosition()) {
			RobotShoot.releaseLatch();
		}
		RobotPickup.moveToShootPosition();
		RobotShoot.useManual();
		RobotShoot.update();
		RobotPickup.update();
	}
	
	private void runCompressor() {
		SmartDashboard.putBoolean("Pressure Switch", RobotSensors.pressureSwitch.get());
		if (!RobotSensors.pressureSwitch.get()) {
			RobotActuators.compressor.set(Relay.Value.kOn);
			//System.out.println("Setting the compressor to ON");
		} else {
			RobotActuators.compressor.set(Relay.Value.kOff);
		}
		//System.out.println("runCompressor finished");
	}
	
	public void disabledPeriodic() {
		RobotDrive.stopDrive();
		RobotShoot.stopMotors();
		AutonZero.reset();
		DashboardPut.put();
		//maxTrueCount = 0;
	}
	
	public void autonomousInit() {
		RobotShoot.reset();
		RobotAuton.initialize();
	}
}
