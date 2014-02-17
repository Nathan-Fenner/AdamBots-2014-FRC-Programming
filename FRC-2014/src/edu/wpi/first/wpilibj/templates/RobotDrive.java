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
 * @author Nathan Fenner
 */
public abstract class RobotDrive {

	public static final double distancePerTick = 0.066;
	public static final double WHEEL_DIAMETER = 0.5;//Feet
	private static int encoderLastLeft = 0;
	private static int encoderLastRight = 0;
	private static double velocityLeft = 0;
	private static double velocityRight = 0;
	private static double targetSpeedLeft = 0.0;
	private static double targetSpeedRight = 0.0;
	private static double currentSpeedLeft = 0.0;
	private static double currentSpeedRight = 0.0;
	private static Timer clock;

////INIT------------------------------------------------------------------------
	public static void initialize() {
		clock = new Timer();
		clock.start();
		System.out.println("RobotDrive init");
		RobotSensors.rightDriveEncoder.start();
		RobotSensors.leftDriveEncoder.start();
		RobotSensors.rightDriveEncoder.setDistancePerPulse((Math.PI * 0.5) / 360);
		RobotSensors.leftDriveEncoder.setDistancePerPulse((Math.PI * 0.5) / 360);
	}
////METHODS---------------------------------------------------------------------
	private static double shift_up = 3.0 / 10.0;
	private static double shift_down = 20.0 / 100.0;

	//4.283 smooth
	//6.540 shift
	/**
	 * In inches
	 *
	 * @return
	 */
	public static double getEncoderLeftInches() {
		return RobotSensors.leftDriveEncoder.get() * distancePerTick;
	}

	public static int getEncoderLeftTicks() {
		return RobotSensors.leftDriveEncoder.get();
	}

	/**
	 * In inches
	 *
	 * @return
	 */
	public static double getEncoderRightInches() {
		return RobotSensors.rightDriveEncoder.get() * distancePerTick;
	}

	public static int getEncoderRightTicks() {
		return -RobotSensors.rightDriveEncoder.get();
		// it's negative
	}

	public static void update() {

		double shift_left = (MathUtils.sign(targetSpeedLeft) == MathUtils.sign(targetSpeedLeft - currentSpeedLeft)) ? shift_up : shift_down;
		double shift_right = (MathUtils.sign(targetSpeedRight) == MathUtils.sign(targetSpeedRight - currentSpeedRight)) ? shift_up : shift_down;

		currentSpeedLeft += MathUtils.sign(targetSpeedLeft - currentSpeedLeft)
				* Math.min(Math.abs(targetSpeedLeft - currentSpeedLeft), shift_left);
		currentSpeedRight += MathUtils.sign(targetSpeedRight - currentSpeedRight)
				* Math.min(Math.abs(targetSpeedRight - currentSpeedRight), shift_right);

		double dt = clock.get();
		clock.reset();

		int leftEncoder = RobotSensors.leftDriveEncoder.get();
		int rightEncoder = -RobotSensors.rightDriveEncoder.get();

		velocityLeft = (leftEncoder - encoderLastLeft) / dt;
		velocityRight = (rightEncoder - encoderLastRight) / dt;

		encoderLastLeft = leftEncoder;
		encoderLastRight = rightEncoder;

		SmartDashboard.putNumber("Current Left", currentSpeedLeft + RobotTeleop.DEBUG_OSCILLATE / 800.0);
		SmartDashboard.putNumber("Measured Left", pwmFromTPS(velocityLeft) + RobotTeleop.DEBUG_OSCILLATE / 800.0);
		SmartDashboard.putNumber("Target Left", targetSpeedLeft + RobotTeleop.DEBUG_OSCILLATE / 800.0);

		SmartDashboard.putNumber("Current Right", currentSpeedRight + RobotTeleop.DEBUG_OSCILLATE / 800.0);
		SmartDashboard.putNumber("Measured Right", pwmFromTPS(velocityRight) + RobotTeleop.DEBUG_OSCILLATE / 800.0);
		SmartDashboard.putNumber("Target Right", targetSpeedRight + RobotTeleop.DEBUG_OSCILLATE / 800.0);
		SmartDashboard.putNumber("Drive Encoder Left", getEncoderLeftTicks());
		SmartDashboard.putNumber("Drive Encoder Right", getEncoderRightTicks());

		SmartDashboard.putNumber("Shift Up", shift_up * 10000);
		SmartDashboard.putNumber("Shift Down", shift_down * 10000);


		// Use currentSpeed and velocity to set raw
		RobotDrive.driveSetRaw(currentSpeedLeft, currentSpeedRight);
	}

	public static double pwmFromRPM(double rpm) {
		return pwmFromTPS(rpm / 60 * 360);
	}

	/**
	 * Transforms a rotation rate to a PWM value
	 * @param tps Ticks per second
	 * @return
	 */
	public static double pwmFromTPS(double tps) {
		return (0.1139 * MathUtils.exp(0.0024 * Math.abs(tps)) - .1139) * MathUtils.sign(tps) / (0.987642579 - .1139);
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
	 * Sets the left and right drive safely, which it fits into the [-1,1]
	 * range.
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

	public static void shiftHigh() {
		RobotActuators.shifter.set(true);
	}

	public static void shiftLow() {
		RobotActuators.shifter.set(false);
	}

	// autoshifts gears
	public static void autoShift(double sensitvity, boolean shiftLow /* in G's*/) {
		double xAcceleration = RobotSensors.accelerometer.getAcceleration(ADXL345_I2C.Axes.kX);
		double zAcceleration = RobotSensors.accelerometer.getAcceleration(ADXL345_I2C.Axes.kZ);
		if (xAcceleration > sensitvity && zAcceleration > sensitvity) {
			RobotActuators.shifter.set(false);
		} else if (shiftLow == true || xAcceleration < sensitvity && zAcceleration
				< sensitvity) {
			RobotActuators.shifter.set(true);
		} else {
			RobotActuators.shifter.set(false);
		}
	}

	public static void stopSmoothDrive() {
		targetSpeedLeft = 0.0;
		currentSpeedLeft = 0.0;
		targetSpeedRight = 0.0;
		currentSpeedRight = 0.0;
	}
}
