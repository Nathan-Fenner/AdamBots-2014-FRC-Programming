/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;

/**
 *
 * @author Tyler
 */
public class RobotPickUp {
    
////VARIABLES-------------------------------------------------------------------

    
////CONSTANTS-------------------------------------------------------------------

    
    public void init() {
	//INSERT CODE HERE
    }
    
    public boolean ifLoaded() {
	if(true) //loaded
	    return true;
	else
	    return false;
	//INSERT CODE HERE
	}
    
    public void suckGamePiece() {
	RobotActuators.PICKUP_ROLLER_ARM_MOTOR.set(1); //Don't know if 1 or -1 right now
    }
    
    public void spitGamePiece() {
        RobotActuators.PICKUP_ROLLER_ARM_MOTOR.set(-1);
    }
    
    public void stopSuckingGamePiece() {
        RobotActuators.PICKUP_ROLLER_ARM_MOTOR.set(0);
    }
    
    public void raisePickUpMechanism() {
	RobotActuators.PICKUP_SYSTEM_MOTOR.set(1);
    }
    
    public void lowerPickUpMechanism() {
        RobotActuators.PICKUP_SYSTEM_MOTOR.set(-1);
    }
    
    public void stopMovingPickUpMechanism() {
        RobotActuators.PICKUP_SYSTEM_MOTOR.set(0);
    }
    
    public void pass() {
	//INSERT CODE HERE
	//?
    }
    
    public void liftRollerArm() {
	RobotActuators.ROLLER_ARM_UP.set(true);
        RobotActuators.ROLLER_ARM_DOWN.set(false);
    }
    
    public void lowerRollerArm() {
        RobotActuators.ROLLER_ARM_UP.set(false);
        RobotActuators.ROLLER_ARM_DOWN.set(true);
    }
    
    /*
    public void stopMovingRollerArm() {
        RobotActuators.ROLLER_ARM_UP.set(false);
        RobotActuators.ROLLER_ARM_UP.set(false);
    }
    */
    
    
    public void moveGamePiece(double speed) {
        RobotActuators.PICKUP_ROLLER_ARM_MOTOR.set(speed);
    }
    
    public void movePickUpMechanism(double speed) {
        RobotActuators.PICKUP_SYSTEM_MOTOR.set(speed);
    }
    
    /*
    public void automatedGamePieceIntake() {
        
    }
    */
}
