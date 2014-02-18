/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * TODO: RUN AUTOMATED SHOOT BY ENUMERATION
 *
 * @author Tyler
 */
public class RobotShoot {
	////VARIABLES---------------------------------------------------------------

	//// ADDED: SWITCHED THE SIGNS ON THE WIND AND UNWIND SPEED
	public static final double UNWIND_SPEED = -1.0;
	public static final double WAIT_TIME = 2.0;
	public static final double WIND_SPEED = 1.0;
	public static final double MAX_REVS = 1300;
	public static final double TENSION_TOLERANCE = 50;
	private static double tensionTargetTick = 1000;
	public static final double QUICK_SHOOT_REVS = .8 * MAX_REVS;
	public static final double BACKWARDS_REV = -(MAX_REVS + 500.0);
	private static Timer timer;
	private static double currentTime;
	private static double updatedSpeed;
	private static boolean stageOneDone;
	private static boolean beenZeroed;
	private static boolean latch;
	private static String currentStage;
	private static boolean automatedShootOnce;
	private static boolean stageThreeDone;
	private static boolean stageFiveDone;
	public static double voltage;
	public static double current;

	//// INIT ------------------------------------------------------------------
	public static void initialize() {
		latch();
		beenZeroed = false;
		automatedShootOnce = true; //True means that the robot will NOT shoot when it turns on.
		timer = new Timer();
		updatedSpeed = 0.0;
		currentTime = 0.0;
		currentStage = "";
		RobotSensors.shooterWinchEncoder.start();
		stageThreeDone = false;
		stageFiveDone = false;
	}

	//// STAGES ----------------------------------------------------------------
	// releases the latch
	public static void releaseBall() {
		if (!stageOneDone) {
			currentStage = "1";
			if (RobotPickup.isBallInPickup()) {
				releaseLatch();
				timer.start();
				stageOneDone = true;
			}
		}
	}

	// Is Shown in our diagram as the shooter head moving forward
	// Nothing that is controlled is happening now
	public static void ballInMotion() {
		currentStage = "2";
	}

	// waiting the 0.5 seconds before unwinding the shooter motor
	public static void waitToUnwind() {
		if (!stageThreeDone) {
			currentStage = "3";
			double time = timer.get();
			if (time >= WAIT_TIME) {
				releaseLatch();
				timer.stop();
				timer.reset();
				stageThreeDone = true;
			}
		}
	}

	// unwindes the shooter until it hits the back limit switch or reaches max revolutions
	//and returns the limit value
	public static boolean unwind() {
		if (!stageFiveDone) {
			currentStage = "4";
			releaseLatch();
			if (!stageThreeDone) {
				System.out.println("In stage Four");
			}
			if (!RobotSensors.shooterAtBack.get()/* && RobotSensors.shooterWinchEncoder.get() >= BACKWARDS_REV*/) {
				automatedUnwind();
			} else {
				stopMotors();
				return true;
			}
		}
		return false;
	}

	// relatches the shooter
	public static void latchShooter() {
		if (!stageFiveDone) {
			currentStage = "5";
			releaseLatch();
			if (timer.get() == 0.0) {
				timer.start();
			}
			// TODO: CHANGE THE TIME FROM 0.5 SECONDS TO WHATEVER WE THINK IT SHOULD BE LATER
			if (timer.get() <= 0.2 && RobotSensors.shooterWinchEncoder.get() >= BACKWARDS_REV/* && RobotSensors.shooterAtBack.get()*/) {
				automatedUnwind();
			} else {
				latch();
				updatedSpeed = 0.0;
				if (timer.get() - currentTime >= 1.5) {
					timer.stop();
					timer.reset();
					stageFiveDone = true;
				}

			}
			// TODO: CHANGE THE CONSTANT TIME LATER
			if (timer.get() > 5.0) {
				timer.stop();
				timer.reset();
				automatedShootOnce = true;
			}
		}
	}

	// rewinds the shooter
	public static boolean rewindShooter() {
		currentStage = "6";
		System.out.println("Stage 6 of shooter");
		if (RobotSensors.shooterWinchEncoder.get() <= tensionTargetTick - TENSION_TOLERANCE && !RobotSensors.shooterLoadedLim.get()) {
			automatedWind();
			return false;
		}
		if (RobotSensors.shooterWinchEncoder.get() >= tensionTargetTick + TENSION_TOLERANCE && !RobotSensors.shooterAtBack.get()) {
			automatedUnwind();
			return false;
		}

		stopMotors();

		if (RobotSensors.shooterLoadedLim.get()) {
			stopMotors();
			return false;
		}
		return true;

	}

	// reshoot method
	// needs to be called before reshooting
	public static void shoot() {
		automatedShootOnce = false;
		stageOneDone = false;
		stageThreeDone = false;
		stageFiveDone = false;
		timer.stop();
		timer.reset();
	}

	// quick shoot method
	// probably not going to be used
	public static void quickShoot() {
		if (RobotSensors.shooterWinchEncoder.get() <= QUICK_SHOOT_REVS) {
			RobotActuators.shooterWinch.set(WIND_SPEED);
		}
	}

	// TODO: RUN AUTOMATED SHOOT BY ENUMERATION
	// Automated shoot
	public static void automatedShoot() {
		// shoots
		if (!automatedShootOnce) {
			if (!stageOneDone) {
				releaseBall();
				ballInMotion();
			} else {
				// waitToUnwind();
				if (stageThreeDone && unwind()) {
					latchShooter();
					if (stageFiveDone) {
						rewindShooter();
					}
				} else {
					waitToUnwind();
				}
			}
		} else {
			stopMotors();
		}
	}

	// used for calibration
	public static void manualShoot() {
		updatedSpeed = Gamepad.primary.getTriggers();
		if (RobotSensors.shooterLoadedLim.get() && RobotSensors.shooterWinchEncoder.get() <= -100 && Gamepad.primary.getTriggers() >= 0.0) {
			updatedSpeed = 0.0;
			System.out.println("Can't move back");
		}

		if (Gamepad.primary.getA()) {
			releaseLatch();
		}
		if (Gamepad.primary.getB()) {
			RobotSensors.shooterWinchEncoder.reset();
		}
		if (Gamepad.primary.getX()) {
			RobotPickup.openRollerArm();
		} else if (Gamepad.primary.getY()) {
			RobotPickup.closeRollerArm();
		}
	}

	// sets speed to the unwind speed
	private static void automatedUnwind() {
		updatedSpeed = UNWIND_SPEED;
		if (RobotSensors.shooterLoadedLim.get()) {
			stopMotors();
		}
	}

	// sets the speed to the wind speed
	private static void automatedWind() {
		updatedSpeed = WIND_SPEED;
	}

	// sets the speed to 0.0
	public static void stopMotors() {
		updatedSpeed = 0.0;
		automatedShootOnce = true;
	}

	// Releases the pnuematic
	public static void releaseLatch() {
		latch = true;
		zeroEncoder();
	}

	// latches the pnuematic
	public static void latch() {
		latch = false;
	}

	// Zeroes the encoder
	// check to see if the encoder is bad with this
	private static void zeroEncoder() {
		if (!RobotSensors.shooterAtBack.get()) {
			beenZeroed = false;
		}
	}

	//// UPDATE METHODS --------------------------------------------------------
	public static void update() {
		// checks to see if the encoder should be zeroed
		if (!beenZeroed && RobotSensors.shooterAtBack.get()) {
			beenZeroed = true;
			RobotSensors.shooterWinchEncoder.reset();
			SmartDashboard.putString("Zeroed", "True");
		}

		if ((RobotSensors.shooterWinchEncoder.get() <= BACKWARDS_REV && updatedSpeed <= 0.0) || (RobotSensors.shooterWinchEncoder.get() >= MAX_REVS && updatedSpeed >= 0.0)) {
			updatedSpeed = 0.0;
		}

		/*if ((RobotSensors.shooterAtBack.get() && updatedSpeed <= 0) || (RobotSensors.shooterWinchEncoder.get() >= MAX_REVS && updatedSpeed >= 0.0)) {
		 updatedSpeed = 0.0;
		 }*/

		// sets pnuematics
		RobotActuators.latchRelease.set(latch);

		// sets motor
		RobotActuators.shooterWinch.set(updatedSpeed);
		SmartDashboard.putNumber("updatedSpeed: ", updatedSpeed);

		// prints to smart dashboard
		SmartDashboard.putNumber("Shooter Encoder", RobotSensors.shooterWinchEncoder.get());
		SmartDashboard.putString("Stage: ", currentStage);
		SmartDashboard.putNumber("Time: ", timer.get());
		SmartDashboard.putBoolean("Shooter at back", RobotSensors.shooterAtBack.get());
		//SmartDashboard.putString("Shooter at str back", MathUtils.rand(15) + "" + RobotSensors.shooterAtBack.get());
		SmartDashboard.putBoolean("Latched", RobotShoot.latch);
		SmartDashboard.putBoolean("beenZeroed", beenZeroed);
		SmartDashboard.putBoolean("Stage One", stageOneDone);
		SmartDashboard.putBoolean("Stage Three", stageThreeDone);
		SmartDashboard.putBoolean("Stage Five", stageFiveDone);

		// latches
		if (RobotSensors.shooterAtBack.get()) {
			latch();
		}

		if (!shootDone()) {
			automatedShoot();
		}
	}

	public static boolean shootDone() {
		return automatedShootOnce;
	}

	////CURRENT CHECK CODE (ask Debjit)
	public static void getCurrent() {
		voltage = RobotSensors.currentSensor.getVoltage();
		current = (voltage - 500) * 0.05 - 100;
		System.out.println("Current = " + current + " Voltage = " + voltage); //Not too sure about the units, though. (most likely milli-)
	}
}
