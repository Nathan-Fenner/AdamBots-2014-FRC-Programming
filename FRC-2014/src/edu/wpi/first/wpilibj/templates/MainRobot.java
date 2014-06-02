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

	public static String cumulativeErrorList = "";

	public static void handleException(Exception e, String from) {
		cumulativeErrorList += "{EXCEPTION FROM " + from + "}\n";
		DriverStation ds = null;
		try {
			ds = DriverStation.getInstance();
		} catch (Exception u) {
		}
		if (ds != null) {
			cumulativeErrorList += "\tmatch time\t" + ds.getMatchTime() + "\n";
		}
		cumulativeErrorList += e.getClass() + "\n\t" + e.getMessage() + "\n" + "\t" + e + "\n\n\n";
		try {
			//FileWrite.writeFile("exceptions.txt", cumulativeErrorList);
		} catch (Exception u) {
		}
		System.out.println("EXCEPTIONZ!!!!!!");
		System.out.println(cumulativeErrorList);
	}
	public static String logData = "";
	public static boolean shooterInManualMode = false;
	public static boolean targetInManualMode = true;
	public static boolean previousShooterLeft = false;
	public static boolean previousShooterRight = false;
	public static Timer timer;
	public static int frames;

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
		//// ADDED: UNDERGLOW FROM THE LINE BELOW
		RobotLights.underglowOn();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		try {
			RobotShoot.useAutomatic();
			runCompressor();
			RobotAuton.update();
			RobotDrive.update();
			RobotPickup.update();													// TODO: UNDISABLE WHEN IT CAN DRIVE AGAIN
			RobotShoot.update();
			DashboardPut.put();
		} catch (Exception e) {
			handleException(e, "autonomousPeriodic");
		}
	}

	public void teleopInit() {
		SmartDashboard.putNumber("Target Ticks", 1200);
		RobotDrive.enableSmoothing();
		RobotLights.underglowOn();
		timer = new Timer();
		timer.start();
		frames = 0;
	}

	public void disabledInit() {
		StandardOneBallAuton.timer.stop();
		StandardOneBallAuton.timer.reset();
		StandardOneBallAuton.secondTimer.stop();
		StandardOneBallAuton.secondTimer.reset();
		RobotLights.underglowOn();
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		try {

			frames++;
			//System.out.println("FPS: " + frames / timer.get());
			if (RobotShoot.gameTime.get() == 0) {
				RobotShoot.gameTime.start();
			}

			//SmartDashboard.putBoolean("shooter AUTO ENCODER", ControlBox.getTopSwitch(3));
			if (!targetInManualMode) {
				//Automatic targetting Mode (Using camera to figure out encoder)
				RobotShoot.setTargetTicks(RobotVision.getEncoder());
				// reinstated the vision's encoder
				//RobotShoot.setTargetTicks(1300);
			} else {
				//Manual targetting mode (using driver to tap left and right)
				if (Gamepad.secondary.getA()) {
					RobotShoot.setTargetTicks(1000);
				}
				if (Gamepad.secondary.getB()) {
					RobotShoot.setTargetTicks(1300);
				}

				if (Gamepad.secondary.getDPadLeft()) {
					if (!previousShooterLeft) {
						RobotShoot.adjustTargetDown();
					}
					previousShooterLeft = true;
				} else {
					previousShooterLeft = false;
				}
				if (Gamepad.secondary.getDPadRight()) {
					if (!previousShooterRight) {
						RobotShoot.adjustTargetUp();
					}
					previousShooterRight = true;
				} else {
					previousShooterRight = false;
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

			if (Gamepad.primary.getX() && Gamepad.primary.getY()) {
				RobotShoot.zeroedBefore = false;
			}

			runCompressor();

			DashboardPut.put();
		} catch (Exception e) {
			handleException(e, "teleopPeriodic");
		}
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
		if (counterOnTest >= 16 && counterOnTest <= 50) {
			RobotActuators.shooterWinch.set(0.3);
			counterOnTest++;
			RobotActuators.latchRelease.set(true);
		}
		if (counterOnTest >= 51) {
			RobotActuators.shooterWinch.set(0.0);
		}
		
		// CHANGED: from RobotDrive.stopDrive();
		RobotActuators.leftDrive.set(0.0);
		RobotActuators.rightDrive.set(0.0);
		
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
		try {
			RobotDrive.stopDrive();
			RobotShoot.stopMotors();
			AutonZero.reset();
			DashboardPut.put();
			//maxTrueCount = 0;
			/*if (logData.length() != 0) {
				FileWrite.writeFile("log" + Calendar.HOUR + "_" + Calendar.MINUTE + ".txt", logData);
			}
			logData = "";*/
		} catch (Exception e) {
			handleException(e, "disabledPeriodic");
		}
	}

	public void autonomousInit() {
		RobotShoot.reset();
		RobotAuton.initialize();
		RobotLights.underglowOn();
	}
}
