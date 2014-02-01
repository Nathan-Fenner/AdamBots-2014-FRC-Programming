/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.Gyro;

/**
 *
 * @author Tyler
 */
public class RobotSensors {

////VARIABLES-------------------------------------------------------------------
    public static Gyro gyro;
    public static AnalogPotentiometer pickUpSystemPot;
    public static AnalogChannel currentSensor;
    public static AnalogChannel configSwitchA;
    public static AnalogChannel configSwitchB;
    public static AnalogChannel configSwitchC;
    public static Counter rightDriveEncoder;
    public static Counter leftDriveEncoder;
    public static Counter pickupSystemEncoder;
    public static Counter shooterWinchEncoder;
    public static DigitalInput ballReadyToLiftLim;
    public static DigitalInput pickupSystemDownLim;
    public static DigitalInput pickupSystemUpLim;
    public static DigitalInput shooterLoadedLim;
    public static DigitalInput shooterUnloadedLim;
    public static DigitalInput shooterLatchedLim;
    public static DigitalInput pressureSwitch;
    public static ADXL345_I2C accelerometer;

    public void initialize() {
	//// Analog
        gyro = new Gyro(1);
	pickUpSystemPot = new AnalogPotentiometer(2);
	currentSensor = new AnalogChannel(3);
	configSwitchA = new AnalogChannel(4);
	configSwitchB = new AnalogChannel(5);
	configSwitchC = new AnalogChannel(6);
	
	//// Digital In 1
	rightDriveEncoder = new Counter(1);
	leftDriveEncoder = new Counter(2);
	ballReadyToLiftLim = new DigitalInput(3);
	shooterWinchEncoder = new Counter(4);
	pickupSystemDownLim = new DigitalInput(5);
	pickupSystemUpLim = new DigitalInput(6);
	shooterLoadedLim = new DigitalInput(7);
	shooterUnloadedLim = new DigitalInput(8);
	shooterLatchedLim = new DigitalInput(9);
	pickupSystemEncoder = new Counter(10);
	pressureSwitch = new DigitalInput(14);
	
	//// Digital 1 Serial
	accelerometer = new ADXL345_I2C(1, ADXL345_I2C.DataFormat_Range.k2G);
    }
}
