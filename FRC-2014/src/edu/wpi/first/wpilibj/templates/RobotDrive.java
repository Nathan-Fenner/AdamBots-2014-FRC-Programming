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
	private static double smooth_rate = 4*124/10000.0;
	private static double shift_rate = 4*67/10000;

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
		return -RobotSensors.rightDriveEncoder.get() * distancePerTick;
		// it's negative
	}
		public static int getEncoderRightTicks() {
		return RobotSensors.rightDriveEncoder.get();
	}

	public static void update() {

		SmartDashboard.putNumber("Current Left", currentSpeedLeft + RobotTeleop.r / 800.0);
		SmartDashboard.putNumber("Measured Left", pwmFromTPS(velocityLeft)+ RobotTeleop.r / 800.0);
		SmartDashboard.putNumber("Target Left", targetSpeedLeft + RobotTeleop.r / 800.0);

		SmartDashboard.putNumber("Current Right", currentSpeedRight + RobotTeleop.r / 800.0);
		SmartDashboard.putNumber("Measured Right", pwmFromTPS(velocityRight)+ RobotTeleop.r / 800.0);
		SmartDashboard.putNumber("Target Right", targetSpeedRight + RobotTeleop.r / 800.0);

		SmartDashboard.putNumber("Smooth Rate", smooth_rate * 10000);
		SmartDashboard.putNumber("Shift Rate",shift_rate * 10000);

		smooth_rate += Gamepad.primary.getRightX() / 10000.0;
		shift_rate += Gamepad.primary.getRightY() / 10000.0;

		currentSpeedLeft += smooth_rate * (targetSpeedLeft - currentSpeedLeft);
		currentSpeedRight += smooth_rate * (targetSpeedRight - currentSpeedRight);
		currentSpeedLeft += MathUtils.sign(targetSpeedLeft - currentSpeedLeft)
				* Math.min(Math.abs(targetSpeedLeft - currentSpeedLeft), shift_rate);
		currentSpeedRight += MathUtils.sign(targetSpeedRight - currentSpeedRight)
				* Math.min(Math.abs(targetSpeedRight - currentSpeedRight), shift_rate);

		double dt = clock.get();
		clock.reset();

		int leftEncoder = RobotSensors.leftDriveEncoder.get();
		int rightEncoder = -RobotSensors.rightDriveEncoder.get();

		velocityLeft = (leftEncoder - encoderLastLeft) / dt;
		velocityRight = (rightEncoder - encoderLastRight) / dt;

		encoderLastLeft = leftEncoder;
		encoderLastRight = rightEncoder;

		// Use currentSpeed and velocity to set raw
		double fastLeft = currentSpeedLeft - 0.5 * (pwmFromTPS(velocityLeft)
				- currentSpeedLeft);
		double fastRight = currentSpeedRight - 0.5 * (pwmFromTPS(velocityRight)
				- currentSpeedRight);
		SmartDashboard.putNumber("Fast Left", fastLeft);
		RobotDrive.driveSetRaw(fastLeft, fastRight);
	}

	public static double pwmFromRPM(double rpm) {
		return pwmFromTPS(rpm / 60 * 360);
	}

	public static double pwmFromTPS(double tps) {
		return 0.1139 * MathUtils.exp(0.0024 * Math.abs(tps)) * MathUtils.sign(tps);
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
		} else if (shiftLow == true || xAcceleration < sensitvity && zAcceleration
				< sensitvity) {
			RobotActuators.shifter.set(true);
		} else {
			RobotActuators.shifter.set(false);
		}
	}
}
