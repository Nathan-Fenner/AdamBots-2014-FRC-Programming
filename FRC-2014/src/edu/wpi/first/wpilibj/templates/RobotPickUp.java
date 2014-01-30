/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.templates.RobotSensors;
import edu.wpi.first.wpilibj.templates.RobotActuators;

/**
 *
 * @author Tyler
 */
public class RobotPickUp {

////VARIABLES-------------------------------------------------------------------
////CONSTANTS-------------------------------------------------------------------
    public static double armEncoder;
    public static final double MIN_SPEED = -1.0;
    public static final double NO_SPEED = 0.0;
    public static final double MAX_SPEED = 1.0;

////INIT------------------------------------------------------------------------
    public static void initialize() {
        RobotSensors.PICKUP_SYSTEM_ENCODER.start();
    }

    public static void update() {
        armEncoder = RobotSensors.PICKUP_SYSTEM_ENCODER.get();
    }

    public static boolean ifLoaded() {
        if (true) //loaded
        {
            return true;
        } else {
            return false;
        }
        //INSERT CODE HERE
    }

    /*public static void suckGamePiece() {
     RobotActuators.PICKUP_ROLLER_ARM_MOTOR.set(1); //Don't know if 1 or -1 right now
     }
    
     public static void spitGamePiece() {
     RobotActuators.PICKUP_ROLLER_ARM_MOTOR.set(-1);
     }
    
     public static void stopSuckingGamePiece() {
     RobotActuators.PICKUP_ROLLER_ARM_MOTOR.set(0);
     }
    
     public static void raisePickUpMechanism() {
     RobotActuators.PICKUP_SYSTEM_MOTOR.set(1);
     }
    
     public static void lowerPickUpMechanism() {
     RobotActuators.PICKUP_SYSTEM_MOTOR.set(-1);
     }
    
     public static void stopMovingPickUpMechanism() {
     RobotActuators.PICKUP_SYSTEM_MOTOR.set(0);
     } */
    public static void pass() {
        //INSERT CODE IF NECESSARY- Drivers should be able to control process
    }

    public static void liftRollerArm() {
        RobotActuators.ROLLER_ARM_UP.set(true);
        RobotActuators.ROLLER_ARM_DOWN.set(false);
    }

    public static void lowerRollerArm() {
        RobotActuators.ROLLER_ARM_UP.set(false);
        RobotActuators.ROLLER_ARM_DOWN.set(true);
    }

    /*
     public static void stopMovingRollerArm() {
     RobotActuators.ROLLER_ARM_UP.set(false);
     RobotActuators.ROLLER_ARM_UP.set(false);
     }
     */
    public static void moveGamePiece(double speed) {
        RobotActuators.PICKUP_ROLLER_ARM_MOTOR.set(speed);
    }

    public static void movePickUpMechanism(double speed) {
        RobotActuators.PICKUP_SYSTEM_MOTOR.set(speed);
    }

    public static void shootProcess() {
        
 
    }

    public static void moveToCatchWithEncoder(double targetCatchPosition, double position, double speed) {
        double armEncoderVal = armEncoder;
          double remainingDistance = targetCatchPosition - armEncoderVal; //assuming armEncoderVal is positive
        if (remainingDistance > 0) {
            movePickUpMechanism(speed);
        }
        else {
            movePickUpMechanism(0);
        }
    }

    public static void moveToShootWithEncoder(double targetShootPosition, double position, double speed) {
        double armEncoderVal = armEncoder;
         double remainingDistance =  targetShootPosition - armEncoderVal; //assuming armEncoderVal is negative
         if (remainingDistance < 0) {
             movePickUpMechanism(speed);
         }
         else {
             movePickUpMechanism(0);
         }
    }

    /*
     public static void automatedGamePieceIntake() {
        
     }
     */
}
