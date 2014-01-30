/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.templates.RobotSensors;
import edu.wpi.first.wpilibj.templates.RobotActuators;

/**
 *
 * @author Tyler
 */
public class RobotPickUp {

////VARIABLES-------------------------------------------------------------------
    public static boolean upperLimit;
    public static boolean ballInPickUpLimit;
    public static boolean lowerLimit;
////CONSTANTS-------------------------------------------------------------------
    public static double armEncoder;
    public static final double MIN_SPEED = -1.0;
    public static final double NO_SPEED = 0.0;
    public static final double MAX_SPEED = 1.0;

////INIT------------------------------------------------------------------------
    public static void initialize() { //initializes encoder
        //RobotSensors.PICKUP_SYSTEM_ENCODER.start();  //assumed that it is initialized in RobotSensors class
    }

    public static void update() { //updates arm encoder value
        armEncoder = RobotSensors.PICKUP_SYSTEM_ENCODER.get();
        upperLimit = RobotSensors.PICKUP_ROLLER_ARM_UP_LIM.get();
        ballInPickUpLimit = RobotSensors.BALL_READY_TO_LIFT_LIM.get();
        lowerLimit = RobotSensors.PICKUP_ROLLER_ARM_DOWN_LIM.get();
    }

    public static boolean ifLoaded() {
        if (ballInPickUpLimit) {

            return true;
        } else {
            return false;
        }
    }

    //INSERT CODE HERE

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
    public static void pass() { //automated pass process, if necessary
        //INSERT CODE IF NECESSARY- Drivers should be able to control process
    }

    public static void liftRollerArm() { //raises upper roller arm for shooting
        RobotActuators.ROLLER_ARM_UP.set(true);
        RobotActuators.ROLLER_ARM_DOWN.set(false);
    }

    public static void lowerRollerArm() { //lowers upper roller arm
        RobotActuators.ROLLER_ARM_UP.set(false);
        RobotActuators.ROLLER_ARM_DOWN.set(true);
    }

    /*
     public static void stopMovingRollerArm() {
     RobotActuators.ROLLER_ARM_UP.set(false);
     RobotActuators.ROLLER_ARM_UP.set(false);
     }
     */
    public static void moveGamePiece(double speed) { //turns on pickup rollers for ball intake
        RobotActuators.PICKUP_ROLLER_ARM_MOTOR.set(speed);
    }

    public static void movePickUpMechanism(double speed) { //moves pickup mechanism up and down
        RobotActuators.PICKUP_SYSTEM_MOTOR.set(speed);
    }

    public static void moveToShootPosition() {  //automatically sucks in game piece and moves to shooting position
        if (!ballInPickUpLimit) {
            moveGamePiece(1.0);
        }
        if (!upperLimit) {
            movePickUpMechanism(1.0);
        }
    }

    public static void moveUpperArmToShoot() { //moves upper arm in the shoot process
        liftRollerArm(); //same as liftRollerArm class

    }

    public static void moveToCatchWithEncoder(double targetCatchPosition, double speed, boolean buttonCatch) { //moves pickup mechanism into position for catching
        double remainingDistance = targetCatchPosition - armEncoder; //assuming armEncoderVal is positive
        if ((!upperLimit || buttonCatch) && (remainingDistance > 0)) {
            movePickUpMechanism(speed);
        }
    }

    public static void moveFromCatchToShoot(double targetShootPosition, double speed, boolean buttonCatchToShoot) { //moves pickup mechanism into position for shooting, only to be used after catching ball
        double remainingDistance = targetShootPosition - armEncoder; //assuming armEncoderVal is negative

        if ((!upperLimit || buttonCatchToShoot) && (remainingDistance < 0)) {
            movePickUpMechanism(speed);

        }
    }

    public static void pickUpFailMode(boolean buttonFail) {
        if (buttonFail) {
            movePickUpMechanism(0);
            moveGamePiece(0);
            RobotActuators.ROLLER_ARM_UP.set(false);
            RobotActuators.ROLLER_ARM_UP.set(false);

        }

    }

    public static void test(boolean buttonTestA, boolean buttonTestB, boolean buttonTestC) {
        if (buttonTestA && !buttonTestB && !buttonTestC) {
            moveToShootPosition();
        }

        if (buttonTestB && !buttonTestA && !buttonTestC) {
            moveToCatchWithEncoder(1000, 1, true);
        }

        if (buttonTestC && !buttonTestA && !buttonTestB) {
            moveFromCatchToShoot(500, 1, true);
        }
    }

    /*public static void testMethodMoveToShoot(boolean buttonTestA) {
     if(buttonTestA) 
     moveToShootPosition();
     }
        
     public static void testMethodCatchEncoder(boolean buttonTestB) {
     if(buttonTestB)
     moveToCatchWithEncoder(1000,1,true);
     }
    
     public static void testMethodCatchToShootEncoder(boolean buttonTestC) {
     if(buttonTestC)
     moveFromCatchToShoot(500, 1, true);
        
     }
     /*
     public static void automatedGamePieceIntake() {
        
     }
     */
}
