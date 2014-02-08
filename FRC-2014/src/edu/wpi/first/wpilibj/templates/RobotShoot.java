/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Tyler
 */
public class RobotShoot {
    ////VARIABLES---------------------------------------------------------------
    public static final double UNWIND_SPEED = 0.5;
    public static final double WAIT_TIME = 0.5;
    public static final double WIND_SHOOTER = -0.5;
    public static final double MAX_REVS = 50;
//  public static boolean unwindSafety;
    private static Timer timer;
    private static double speed;
    private static boolean stageOneDone;
    private static String stage;
    private static boolean automatedShootOnce;
    private static boolean stageThreeDone;
    
    //These next few lines are for getting the current and voltage through an analog channel
    public static double voltage;
    public static double current;
    
    //// INIT ------------------------------------------------------------------
    public static void initialize() {
	automatedShootOnce = false;
	timer = new Timer();
	speed = 0.0;
	stage = new String();
	RobotSensors.shooterWinchEncoder.start();
    }
    
    //// STAGES ----------------------------------------------------------------
    // releases the latch
    public static void stageOne() {
	if(RobotPickup.ifLoaded()){
            RobotActuators.latchRelease.set(false);
            timer.start();
            stageOneDone = true;
            stage = "1";
        }
}
    
    // Is Shown in our diagram as the shooter head moving forward
    // Nothing that is controlled is happening now
    public static void stageTwo() {
	stage = "2";
    }
    
    // waiting the 0.5 seconds before unwinding the shooter motor
    public static void stageThree() {
	double time = timer.get();
	if (time >= WAIT_TIME) {
	    timer.stop();
	    timer.reset();
	    stageThreeDone = true;
	}
	stage = "3";
    }
    
    // unwindes the shooter until it hits the back limit switch or reaches max revolutions
    //and returns the limit value
    public static boolean stageFour() {
	if (!RobotSensors.shooterAtBack.get()&& RobotSensors.shooterWinchEncoder.get()<= MAX_REVS) {
	    unwindShooter();
	}
	stage = "4";
	return (RobotSensors.shooterAtBack.get());
    }
    
    // relatches the shooter
    public static void stageFive() {
	RobotActuators.latchRelease.set(true);
	stage = "5";
    }
    
    // rewinds the shooter
    public static boolean stageSix() {
	if (RobotSensors.shooterWinchEncoder.get() <= MAX_REVS) {
	    windShooter();
	    return false;
	}
	automatedShootOnce = true;
	stage = "6";
	return true;
    }
    
    // Automated shoot
    public static void automatedShoot() {
	// shoots
	if (!automatedShootOnce) {
	    if (!stageOneDone) {
		stageOne();
		stageTwo();
	    } else {
		stageThree();
		if (stageThreeDone && stageFour()) {
		    stageFive();
		    stageSix();
		}
	    }
	} else {
	    stopMotors();
	}
    }
    
    // RobotShootShoot
    public static void manualShoot() {
	if (FancyJoystick.primary.getRawButton(FancyJoystick.BUTTON_A)) 
	    speed = 0.5;
	else if (FancyJoystick.primary.getRawButton(FancyJoystick.BUTTON_B)) 
	    speed = -0.5;
    }
    
    // resets to be able to shoot again
    public static void shootAgain() {
	automatedShootOnce = false;
    }
    
    //// PRIVATE METHODS -------------------------------------------------------
    // sets speed to the unwind speed
    private static void unwindShooter() {
	speed = UNWIND_SPEED;
    }
    
    // sets the speed to the wind speed
    private static void windShooter() {
	speed = WIND_SHOOTER;
    }
    
    // sets the speed to 0.0
    private static void stopMotors() {
	speed = 0.0;
    }
    
    //// UPDATE METHODS --------------------------------------------------------
    public static void update() {
	// checks the safety
	if(RobotSensors.shooterLoadedLim.get()){
	    stopMotors();
	}
	
	// sets motor
	RobotActuators.shooterWinch.set(speed);
	
	// prints to smart dashboard
	SmartDashboard.putNumber("Shooter Encoder", RobotSensors.shooterWinchEncoder.get());
	SmartDashboard.putString("Stage: ", stage);
	SmartDashboard.putNumber("Time: ", timer.get());
	SmartDashboard.putBoolean("Shooter at back", RobotSensors.shooterAtBack.get());
    }
//STOP METHOD
    
////CURRENT CHECK CODE (ask Debjit)
    public static void getCurrent() {
        voltage = RobotSensors.currentSensor.getVoltage();
        current = (voltage - 500) * 0.05 - 100;
        System.out.println("Current = " + current + " Voltage = " + voltage); //Not too sure about the units, though. (most likely milli-)
        
        //Where I got the equation from:
        //http://www.allegromicro.com/en/Products/Current-Sensor-ICs/Fifty-To-Two-Hundred-Amp-Integrated-Conductor-Sensor-ICs/ACS758/ACS758-Frequently-Asked-Questions.aspx#Q4
    }
}
