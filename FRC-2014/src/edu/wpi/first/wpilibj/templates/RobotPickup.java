/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.templates.RobotSensors;
import edu.wpi.first.wpilibj.templates.RobotActuators;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Tyler
 */
public class RobotPickup {

////VARIABLES-------------------------------------------------------------------
    public static boolean upperLimit;
    public static boolean ballInPickUpLimit;
    public static boolean lowerLimit;
////CONSTANTS-------------------------------------------------------------------
    public static double armEncoder;
    public static final double MIN_SPEED = -1.0;
    public static final double NO_SPEED = 0.0;
    public static final double MAX_SPEED = 1.0;
    public static double armMotorSpeed;
    public static double rollerMotorSpeed;
    public static double armPotentiometer;
////INIT------------------------------------------------------------------------
    // initializes the encoder
    public static void initialize() {
        rollerMotorSpeed = 0.5;
        armMotorSpeed = 0.5;
        
        RobotSensors.pickupSystemEncoder.reset();
        RobotSensors.pickupSystemEncoder.start();
	//RobotSensors.PICKUP_SYSTEM_ENCODER.start();  //assumed that it is initialized in RobotSensors class
    }
    
    // checks if the shooter is loaded
    public static boolean ifLoaded() {
        return ballInPickUpLimit;
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
   /* public static void pass() { //automated pass process, if necessary
        //INSERT CODE IF NECESSARY- Drivers should be able to control process
    }*/

    //raises upper roller arm for shooting
    public static void liftRollerArm() { 
        RobotActuators.rollerArmUp.set(true);
        RobotActuators.rollerArmDown.set(false);
    }

    //lowers upper roller arm
    public static void lowerRollerArm() { 
        RobotActuators.rollerArmUp.set(false);
        RobotActuators.rollerArmDown.set(true);
    }

    /*
     public static void stopMovingRollerArm() {
     RobotActuators.ROLLER_ARM_UP.set(false);
     RobotActuators.ROLLER_ARM_UP.set(false);
     }
     */
    
    //turns on pickup rollers for ball intake
    public static void moveGamePiece(double speed) { 
        rollerMotorSpeed = speed;
    }

    //moves pickup mechanism up and down
    public static void movePickUpMechanism(double speed) { 
        armMotorSpeed = speed;
    }
    
    //moves pickup mechanism into position for catching
     public static void moveToShoot(double targetCatchPosition, double speed, boolean buttonShoot) { 
        double remainingDistance = targetCatchPosition - armEncoder;
        if (lowerLimit) {
            if(!ballInPickUpLimit) {
                moveGamePiece(1.0);
            } else if (buttonShoot && ballInPickUpLimit && (remainingDistance < 0)) {
                moveGamePiece(0);
                movePickUpMechanism(speed);
            } else if (buttonShoot && ballInPickUpLimit && (remainingDistance >= 0)) {
                moveGamePiece(0);
                movePickUpMechanism(0);
            }
       }
        
       if (upperLimit & buttonShoot && (remainingDistance > 0)) {
            movePickUpMechanism(-speed);
       } else if (buttonShoot && (remainingDistance <= 0)) {
           movePickUpMechanism(0);
       }
    }
    //move to shoot position with potentiometer values
    public static void moveToShootPotentiometer(double targetCatchPosition, double speed, boolean buttonShootPot) {
        double remainingDistance = targetCatchPosition -  armPotentiometer; 
        if(lowerLimit){
            if(!ballInPickUpLimit){
                moveGamePiece(1.0);
            } else if (buttonShootPot && ballInPickUpLimit && (remainingDistance < 0)) {
                moveGamePiece(0);
                movePickUpMechanism(speed);
            } else if (buttonShootPot && ballInPickUpLimit && (remainingDistance >= 0)) {
                moveGamePiece(0);
                movePickUpMechanism(0);
            }
        }
        if (upperLimit & buttonShootPot && (remainingDistance > 0)) {
                movePickUpMechanism(-speed);
            } else if (buttonShootPot && (remainingDistance <= 0)) {
                movePickUpMechanism(0);
        }
    }
     
    //automatically sucks in game piece and moves to shooting position
    public static void moveToCatchPosition() {  
        if (!upperLimit) {
            movePickUpMechanism(1.0);
        } else { 
            movePickUpMechanism(0.0); 
            liftRollerArm();
        }   
    }
    
    /*public static void moveToShootFromCatch(double targetCatchPosition, double speed, boolean buttonShootFromCatch) {
        double remainingDistance = targetCatchPosition - armEncoder;
        if (buttonShootFromCatch && (remainingDistance < 0)) {
            movePickUpMechanism(speed);
        }
    }*/

    /*public static void moveUpperArmToShoot() { //moves upper arm in the shoot process
        liftRollerArm(); //same as liftRollerArm class

    }*/
    
    // moves from the bottom position to the shoot position
    public static void moveToBottomPosition() {
        if (!lowerLimit) {
            movePickUpMechanism(-1.0);
        } else {
            movePickUpMechanism(0.0);
        }
    }

    /*public static void moveFromCatchToShoot(double targetShootPosition, double speed, boolean buttonCatchToShoot) { //moves pickup mechanism into position for shooting, only to be used after catching ball
        double remainingDistance = targetShootPosition - armEncoder; //assuming armEncoderVal is negative

        if ((!upperLimit || buttonCatchToShoot) && (remainingDistance < 0)) {
            movePickUpMechanism(speed);

        }
    }*/

    // checks the fail mode
    public static void pickUpFailMode(boolean buttonFail) {
        if (buttonFail) {
            movePickUpMechanism(0);
            moveGamePiece(0);
            RobotActuators.rollerArmUp.set(false);
            RobotActuators.rollerArmDown.set(false);

        }

    }

    // the test method
    public static void test(boolean buttonTestA, boolean buttonTestB, boolean buttonTestC) {
        if (buttonTestA && !buttonTestB && !buttonTestC) {
            moveToShootPotentiometer(2.375, 1.0, true);
        } else if (buttonTestB && !buttonTestA && !buttonTestC) {
            moveToBottomPosition();
        } else if (buttonTestC && !buttonTestA && !buttonTestB) {
            moveToCatchPosition();
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
    
        // normal update method
    public static void update() {
        armEncoder = RobotSensors.pickupSystemEncoder.get();
        upperLimit = RobotSensors.pickupSystemUpLim.get();
        ballInPickUpLimit = RobotSensors.ballReadyToLiftLim.get();
        lowerLimit = RobotSensors.pickupSystemDownLim.get();
        armPotentiometer = RobotSensors.pickupPotentiometer.get();
        
        RobotActuators.pickupRollerArmMotor.set(rollerMotorSpeed);
        RobotActuators.pickupSystemMotor.set(armMotorSpeed);
        
        System.out.println(armPotentiometer);
        
        /*SmartDashboard.putBoolean("upperLimit", upperLimit);
        SmartDashboard.putBoolean("lowerLimit", lowerLimit);
        SmartDashboard.putBoolean("ballInPickUpLimit", ballInPickUpLimit);
        SmartDashboard.putNumber("Arm Encoder", armEncoder);
        System.out.println("UpperLimit: " + upperLimit);
        System.out.println("LowerLimit: " + lowerLimit);
        System.out.println("BallInPickUpLimit" + ballInPickUpLimit);
        System.out.println("Arm Encoder" + armEncoder);*/

    }
}

