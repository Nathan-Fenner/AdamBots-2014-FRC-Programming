/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
	private static int encoderCountLeft = 0;
	private static int encoderCountRight = 0;
	private static int encoderLastLeft = 0;
	private static int encoderLastRight = 0;
	private static double targetSpeedLeft = 0.0;
	private static double targetSpeedRight = 0.0;
	private static double currentSpeedLeft = 0.0;
	private static double currentSpeedRight = 0.0;

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

		int encoderNowLeft = RobotSensors.leftDriveEncoder.get();
		int encoderNowRight = RobotSensors.rightDriveEncoder.get();

		encoderCountLeft += (int) ((encoderNowLeft - encoderLastLeft) * MathUtils.sign(currentSpeedLeft));
		encoderCountRight += (int) ((encoderNowRight - encoderLastRight) * MathUtils.sign(currentSpeedRight));

		encoderLastLeft = encoderNowLeft;
		encoderLastRight = encoderNowRight;

		double smooth_rate = 0.1;
		double shift_rate = 0.0;

		currentSpeedLeft += smooth_rate * (targetSpeedLeft - currentSpeedLeft);
		currentSpeedRight += smooth_rate * (targetSpeedRight - currentSpeedRight);
		currentSpeedLeft += MathUtils.sign(targetSpeedLeft - currentSpeedLeft) * Math.min(Math.abs(targetSpeedLeft - currentSpeedLeft), shift_rate);
		currentSpeedRight += MathUtils.sign(targetSpeedRight - currentSpeedRight) * Math.min(Math.abs(targetSpeedRight - currentSpeedRight), shift_rate);

		RobotDrive.driveSetRaw(currentSpeedLeft, currentSpeedRight);

		//encoderL = RobotSensors.leftDriveEncoder.get();
		//encoderL = RobotSensors.rightDriveEncoder.get();
		//encoderR = RobotSensors.rightDriveEncoder.get();
		//System.out.println("Left: " + encoderL + "\tRight: " + encoderR);
		//encoderAvg = (encoderL + encoderR) / 2.0;

		System.out.println("Enc Left: " + encoderCountLeft + "\nEnc Right: " + encoderCountRight);
		System.out.println("Tru Left: " + RobotSensors.leftDriveEncoder.get() + "\nTru Right: " + RobotSensors.rightDriveEncoder.get());

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
		if (distanceToGo == 0) {
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
		if (distanceToGo == 0) {
			stopDrive();
			return;
		}
	}

	/**
	 * Sets drive speed to go forward
	 *
	 * @param speed
	 */
	public static void driveStraight(double speed) {
		drive(speed, speed);
	}

	/**
	 * Sets the left and right drive safely, which it fits into the [-1,1] range.
	 *
	 * @param leftSpeed
	 * @param rightSpeed
	 */
	public static void drive(double leftSpeed, double rightSpeed) {
		leftSpeed = Math.max(-1, Math.min(1, leftSpeed));
		rightSpeed = Math.max(-1, Math.min(1, rightSpeed));

		targetSpeedLeft = leftSpeed;
		targetSpeedRight = rightSpeed;
	}

	/**
	 * Raw setting speed, not smooth: avoid use whenever possible
	 *
	 * @param left
	 * @param right
	 */
	public static void driveSetRaw(double leftSpeed, double rightSpeed) {
		leftSpeed = Math.max(-1, Math.min(1, leftSpeed));
		rightSpeed = Math.max(-1, Math.min(1, rightSpeed));
		RobotActuators.leftDrive.set(leftSpeed);
		RobotActuators.rightDrive.set(-rightSpeed);
	}

	/**
	 * Sets the robot to turn in an arc
	 *
	 * @param turnRate Positive values turn right (clockwise)
	 * @param forwardSpeed Positive values go forward
	 */
	public static void turn(double turnRate, double forwardSpeed) {
		drive(forwardSpeed + turnRate, forwardSpeed - turnRate);
	}
	/*
	 sets victors to zero
	 */

	//Stops the robot from moving while the button Y is held down
	public static void stopDrive() {
		RobotActuators.leftDrive.set(STOP);
		RobotActuators.rightDrive.set(STOP);
		targetSpeedLeft = 0.0;
		targetSpeedRight = 0.0;
		currentSpeedLeft = 0.0;
		currentSpeedRight = 0.0;
	}

	public static void shiftHigh() {
		RobotActuators.shifter.set(false);
	}

	public static void shiftLow() {
		RobotActuators.shifter.set(true);
	}

	// autoshifts gears
	public static void autoShift(double sensitvity, boolean shiftLow /* in G's*/) {
		double xAcceleration = RobotSensors.accelerometer.getAcceleration(ADXL345_I2C.Axes.kX);
		double zAcceleration = RobotSensors.accelerometer.getAcceleration(ADXL345_I2C.Axes.kZ);
		if (xAcceleration > sensitvity && zAcceleration > sensitvity) {
			RobotActuators.shifter.set(false);
		} else if (shiftLow == true || xAcceleration < sensitvity && zAcceleration < sensitvity) {
			RobotActuators.shifter.set(true);
		} else {
			RobotActuators.shifter.set(false);
		}
	}
}
