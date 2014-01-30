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
    public static Gyro GYRO;
    public static AnalogPotentiometer PICK_UP_SYSTEM_POT;
    public static AnalogChannel CURRENT_SENSORS;
    public static AnalogChannel CONFIG_SWICTH_A;
    public static AnalogChannel CONFIG_SWICTH_B;
    public static AnalogChannel CONFIG_SWICTH_C;
    public static Encoder RIGHT_DRIVE_ENCODER;
    public static Encoder LEFT_DRIVE_ENCODER;
    public static Encoder PICKUP_SYSTEM_ENCODER;
    public static Encoder SHOOTER_WHINCH_ENCODER;
    public static DigitalInput BALL_READY_TO_LIFT_LIM;
    public static DigitalInput PICKUP_ROLLER_ARM_DOWN_LIM;
    public static DigitalInput PICKUP_ROLLER_ARM_UP_LIM;
    public static DigitalInput SHOOTER_LOADED_LIM;
    public static DigitalInput SHOOTER_UNLOADED_LIM;
    public static DigitalInput SHOOTER_LATCHED_LIM;
    public static DigitalInput PREASSURE_SWITCH;
    public static ADXL345_I2C ACCELEROMETER;

    public void initialize() {
        //INSERT CODE HERE
    }
}
