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
import edu.wpi.first.wpilibj.Relay;

/**
 *
 * @author Tyler
 */
public class RobotSensors {
    
////VARIABLES-------------------------------------------------------------------
    public static final DigitalInput topLimit1;
    public static final DigitalInput topLimit2;
    public static final DigitalInput limitShooterUnloaded;
    public static final DigitalInput limitShooterLoaded;
    public static final DigitalInput limitShooterHatch;
    public static final DigitalInput limitPickupSystemUp;
    public static final DigitalInput limitPickupSysetmDown;
    public static final DigitalInput bottomLimit1;
    public static final DigitalInput bottomLimit2;
    public static final DigitalInput ballCheck;
    public static final DigitalInput cockBack;
    public static final DigitalInput releaseMech;
    public static final DigitalInput configSwitch1; //top
    public static final DigitalInput configSwitch2; //middle
    public static final DigitalInput configSwitch3; //bottom
    public static final DigitalInput compSwitch;
    public static final DigitalInput pressSwitch;
    public static final Encoder cockingEnc;
    public static final Encoder topArmEnc;
    public static final Encoder leftStraightEnc;
    public static final Encoder rightStraightEnc;
    public static final Encoder rightDriveAEnc;
    //public static final Encoder rightDriveBEnc;
    public static final Encoder leftDriveAEnc;
    //public static final Encoder leftDriveBEnc;
    public static final Encoder pickUpArmEnc;
    public static final Encoder rollerAcqEnc;
    //public static final Encoder rollerEnc;
    public static final ADXL345_I2C accel;
    public static final Gyro gyro;//1
    public static final Relay relay1;
    public static final Relay relay2;
    public static final Relay cameraLED;   
    
////CONSTANTS-------------------------------------------------------------------
    public static final int PORT_TOP_LIMIT_1 = 1;
    public static final int PORT_TOP_LIMIT_2 = 1;
    public static final int PORT_LIMIT_SHOOTER_UNLOADED = 1;
    public static final int PORT_LIMIT_SHOOTER_LOADED = 1;
    public static final int PORT_LIMIT_SHOOTER_HATCH = 1;
    public static final int PORT_LIMIT_PICK_UP_SYSTEM_UP = 1;
    public static final int PORT_LIMIT_PICK_UP_SYSTEM_DOWN = 1;
    public static final int PORT_BOTTOM_LIMIT1 = 1;
    public static final int PORT_BOTTOM_LIMIT2 = 1;
    public static final int PORT_BALL_CHECK = 1;
    public static final int PORT_COCK_BACK = 1;
    public static final int PORT_RELEASE_MECH = 1;
    public static final int PORT_CONFIG_SWITCH1 = 1;
    public static final int PORT_CONFIG_SWITCH2 = 1;
    public static final int PORT_CONFIG_SWITCH3 = 1;
    public static final int PORT_COMP_SWITCH = 1;
    public static final int PORT_PRESS_SWTICH = 1;
    public static final int PORT_COCKING_ENC = 1;
    public static final int PORT_TOP_ARM_ENC = 1;
    public static final int PORT_LEFT_STRAIGHT_ENC = 1;
    public static final int PORT_RIGHT_STRAIGHT_ENC = 1;
    public static final int PORT_RIGHT_DRIVE_A_ENC = 1;
    public static final int PORT_LEFT_DRIVE_A_ENC = 1;
    public static final int PORT_PICK_UP_ARM_ENC = 1;
    public static final int PORT_ROLLER_ACQ_ENC = 1;
    public static final int PORT_ACCEL = 1;
    public static final int PORT_GYRO = 1;
    public static final int PORT_RELAY_1 = 1;
    public static final int PORT_RELAY_2 = 1;
    public static final int PORT_CAMERA_LED = 1;
    
    
    
    public void init() {
	//INSERT CODE HERE
    }
    
}
