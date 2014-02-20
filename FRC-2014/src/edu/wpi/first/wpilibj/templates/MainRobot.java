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
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		runCompressor();
		RobotAuton.update();
	}

	public void teleopInit() {
		SmartDashboard.putNumber("Target Ticks", 1000);
		RobotDrive.enableSmoothing();
	}

	public void disabledInit() {
		StandardOneBallAuton.timer.stop();
		StandardOneBallAuton.timer.reset();
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {

		RobotShoot.setTargetTicks(SmartDashboard.getNumber("Target Ticks"));

		ControlBox.update();

		//RobotDrive.update();
		RobotTeleop.update();
		//RobotPickup.update();
		//RobotShoot.update();
		if (Gamepad.secondary.getB()) {
			//RobotShoot.manualShoot();
		}
		SmartDashboard.putNumber("ANGLE ANGLE",RobotPickup.getArmAngleAboveHorizontal());

		SmartDashboard.putBoolean("PICKUP Upper Limit", RobotSensors.pickupSystemUpLim.get());
		SmartDashboard.putBoolean("PICKUP Lower Limit", RobotSensors.pickupSystemDownLim.get());
		//runCompressor();

		SmartDashboard.putNumber("Red Distance", RobotVision.redDistance());

	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
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
		RobotPickup.angle_I = 0;
	}

	public void autonomousInit() {
		RobotAuton.initialize();
	}
}
