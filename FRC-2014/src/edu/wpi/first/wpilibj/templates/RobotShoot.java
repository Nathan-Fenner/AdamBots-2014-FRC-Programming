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
    public static final double WIND_SPEED = -0.5;
    public static final double MAX_REVS = 50;
    public static final double QUICK_SHOOT_REVS =.8*MAX_REVS;
//  public static boolean unwindSafety;
    private static Timer timer;
    private static double updatedSpeed;
    private static boolean stageOneDone;
    private static String currentStage;
    private static boolean automatedShootOnce;
    private static boolean stageThreeDone;
     //These next few lines are for getting the current and voltage through an analog channel
    public static double voltage;
    public static double current;

    //// INIT ------------------------------------------------------------------
    public static void initialize() {
	automatedShootOnce = false;
	timer = new Timer();
	updatedSpeed = 0.0;
	currentStage = "";
	RobotSensors.shooterWinchEncoder.start();
    }

    //// STAGES ----------------------------------------------------------------
    // releases the latch
    public static void releaseBall() {
	currentStage = "1";
	if(RobotPickup.isBallInPickup()){
            RobotActuators.latchRelease.set(false);
            timer.start();
            stageOneDone = true;
        }
    }

    // Is Shown in our diagram as the shooter head moving forward
    // Nothing that is controlled is happening now
    public static void ballInMotion() {
	currentStage = "2";
    }

    // waiting the 0.5 seconds before unwinding the shooter motor
    public static void waitToUnwind() {
	currentStage = "3";
	double time = timer.get();
	if (time >= WAIT_TIME) {
	    timer.stop();
	    timer.reset();
	    stageThreeDone = true;
	}
    }

    // unwindes the shooter until it hits the back limit switch or reaches max revolutions
    //and returns the limit value
    public static boolean unwind() {
	currentStage = "4";
	if (!RobotSensors.shooterAtBack.get() && RobotSensors.shooterWinchEncoder.get() <= MAX_REVS) {
	    manualUnwind();
	}
	return (RobotSensors.shooterAtBack.get());
    }

    // relatches the shooter
    public static void latchShooter() {
	currentStage = "5";
	RobotActuators.latchRelease.set(true);
    }

    // rewinds the shooter
    public static boolean rewindShooter() {
	currentStage = "6";
	if (RobotSensors.shooterWinchEncoder.get() <= MAX_REVS) {
	    manualWind();
	    return false;
	}
	automatedShootOnce = true;
	return true;

    }
    /**
     * This is a method for a quick shot, it will be pretensioned by another method
     * once this is called it will finish tensioning and then it will shoot the ball
     */
    public static void quickShoot(){
       if(RobotSensors.shooterWinchEncoder.get() <= QUICK_SHOOT_REVS){
            RobotActuators.shooterWinch.set(WIND_SPEED);
       }
       else{
           RobotActuators.latchRelease.set(true);
       }
    }
    // Automated shoot
    public static void automatedShoot() {
	// shoots
	if (!automatedShootOnce) {
	    if (!stageOneDone) {
		releaseBall();
		ballInMotion();
	    } else {
		waitToUnwind();
		if (stageThreeDone && unwind()) {
		    latchShooter();
		    rewindShooter();
		}
	    }
	} else {
	    stopMotors();
	}
    }

    // RobotShootShoot
    // If issue with triggers, uncomment the code below
    public static void manualShoot() {
	/*if (FancyJoystick.primary.getRawButton(FancyJoystick.BUTTON_A))
	    updatedSpeed = 0.5;
	else if (FancyJoystick.primary.getRawButton(FancyJoystick.BUTTON_B))
	    updatedSpeed = -0.5;
        else
            updatedSpeed = 0.0;*/

        updatedSpeed = Gamepad.primary.getTriggers();
    }

    // resets to be able to shoot again
    public static void shootAgain() {
	automatedShootOnce = false;
    }

    //// PRIVATE METHODS -------------------------------------------------------
    // sets speed to the unwind speed
    //// TODO: CHANGE NAME
    private static void manualUnwind() {
	updatedSpeed = UNWIND_SPEED;
    }

    // sets the speed to the wind speed
    //// TODO: CHANGE NAME
    private static void manualWind() {
	updatedSpeed = WIND_SPEED;
    }

    // sets the speed to 0.0
    private static void stopMotors() {
	updatedSpeed = 0.0;
    }

    //// UPDATE METHODS --------------------------------------------------------
    public static void update() {
	// checks the safety
	if(RobotSensors.shooterLoadedLim.get()){
	    stopMotors();
	}

	// sets motor
	RobotActuators.shooterWinch.set(updatedSpeed);

	// prints to smart dashboard
	SmartDashboard.putNumber("Shooter Encoder", RobotSensors.shooterWinchEncoder.get());
	SmartDashboard.putString("Stage: ", currentStage);
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
