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
import java.util.Calendar;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class MainRobot extends IterativeRobot {

	public static String logData = "";
	public static boolean shooterInManualMode = false;
	public static boolean targetInManualMode = true;

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
		RobotLights.underglowOn();
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
		SmartDashboard.putNumber("Target Ticks", 1200);
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

		//SmartDashboard.putBoolean("shooter AUTO ENCODER", ControlBox.getTopSwitch(3));
		if (!targetInManualMode) {
			RobotShoot.setTargetTicks(RobotVision.getEncoder());
			// reinstated the vision's encoder
			//RobotShoot.setTargetTicks(1300);
		} else {
			if (Gamepad.secondary.getLeftX() < -.8) {
				RobotShoot.adjustTargetDown();
			}
			if (Gamepad.secondary.getLeftX() > .8) {
				RobotShoot.adjustTargetUp();
			}
		}

		ControlBox.update();
		RobotDrive.update();
		RobotPickup.update();
		RobotShoot.update();

		RobotPickup.moveToShootPosition();

		RobotTeleop.update();
		//SmartDashboard.putBoolean("TOP SWITCH TWO", ControlBox.getTopSwitch(2));
		if (!shooterInManualMode) {
			RobotShoot.useAutomatic();
		} else {
			RobotShoot.useManual();
		}

		if (Gamepad.secondary.getBack()) {
			shooterInManualMode = true;
		}
		if (Gamepad.secondary.getStart()) {
			shooterInManualMode = false;
		}
		if (Gamepad.primary.getBack()) {
			targetInManualMode = true;
		}
		if (Gamepad.primary.getStart()) {
			targetInManualMode = false;
		}
		
		if (Gamepad.secondary.getA() && Gamepad.secondary.getB()) {
			RobotShoot.zeroedBefore = false;
		}

		runCompressor();

		DashboardPut.put();
	}

	private int counterOnTest; //Used in testPeriodic, testInit for debug.

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		runCompressor();
		DashboardPut.put();
		RobotPickup.closeRollerArm();
		if (counterOnTest <= 15) {
			RobotActuators.shooterWinch.set(-0.3);
			RobotActuators.latchRelease.set(false);
			if (!RobotSensors.shooterAtBack.get()) {
				counterOnTest++;
			}
		} else {
			RobotActuators.latchRelease.set(true);
		}
		if (counterOnTest >= 16 && counterOnTest <= 30) {
			RobotActuators.shooterWinch.set(0.3);
			counterOnTest++;
			RobotActuators.latchRelease.set(true);
		}
		if (counterOnTest >= 31) {
			RobotActuators.shooterWinch.set(0.0);
		}

		System.out.println("counterOnTest: " + counterOnTest);
	}

	public void testInit() {
		counterOnTest = 0;
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
		if (logData.length() != 0) {
			FileWrite.writeFile("log" + Calendar.HOUR + "_" + Calendar.MINUTE + ".txt", logData);
		}
		logData = "";
	}

	public void autonomousInit() {
		RobotShoot.reset();
		RobotAuton.initialize();
	}
}
