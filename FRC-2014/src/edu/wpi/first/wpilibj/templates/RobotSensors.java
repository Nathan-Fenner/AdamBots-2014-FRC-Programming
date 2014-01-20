/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.ADXL345_I2C;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;

/**
 *
 * @author Tyler
 */
public class RobotSensors {
    
////VARIABLES-------------------------------------------------------------------
    public static final DigitalInput topLimit1;
    public static final DigitalInput topLimit2;
    public static final DigitalInput bottomLimit1;
    public static final DigitalInput bottomLimit2;
    public static final DigitalInput ballCheck;
    public static final DigitalInput cockBack;
    public static final DigitalInput releaseMech;
    public static final Encoder cockingEnc;
    public static final Encoder armEnc;
    public static final Encoder leftStraightEnc;
    public static final Encoder rightStraightEnc;
    public static final ADXL345_I2C accel;
    public static final Gyro gyro;
    
    
    
    
////CONSTANTS-------------------------------------------------------------------
    
    public void init() {
	//INSERT CODE HERE
    }
    
}
