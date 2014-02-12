package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Josh and Debjit
 */
public class RobotPickupPassingSequence {
////VARIABLES-------------------------------------------------------------------
    public static boolean upperLimit;
    public static boolean lowerLimit;
    public static boolean ballInPickupLimit;
////CONSTANTS-------------------------------------------------------------------
    public static double armEncoder;
    public static double armMotorSpeed;
    public static double rollerMotorSpeed;
    public static double armPotentiometer;
    public static int allowedStage; //has nothing to do with current stage
    public static int currentStage; 
////INIT------------------------------------------------------------------------
    //initializes the encoder and motor speeds
    public static void initialize(){
        rollerMotorSpeed = 0.5;
        armMotorSpeed = 0.5;
        allowedStage = 0;
    }
    
    //checks if shooter is loaded
    public static boolean ifLoaded() {
        return RobotSensors.shooterAtBack.get();   
    }
    
    //stage 1- sets arm in ground pickup postition
    public static void moveToGround() {
        currentStage = 1;
        if(!lowerLimit)
            armMotorSpeed = -1.0;
        else {
            armMotorSpeed = 0;
            allowedStage = 1;
        }
    }
    
    //stage 2- roll game piece into pickup
    public static void rollGamePieceIn() {
        currentStage = 2;
        if(!ballInPickupLimit)
            rollerMotorSpeed = 1.0;
        else {
            rollerMotorSpeed = 0;
            allowedStage = 2;
        }
    }
        
    //stage 3- driver tells robot to spit game piece out
    public static void rollGamePieceOut() {
        currentStage = 3;
        rollerMotorSpeed = -1.0;
        allowedStage = 3;
    }
    
    //combined method for passing
    public static void passingSequence() {
        if(allowedStage == 0)
            moveToGround();
        else if(allowedStage == 1)
            rollGamePieceIn();
        else if(allowedStage == 2)
            rollGamePieceOut();
        else
            allowedStage = 0;
    }
    
    //updates variables
        public static void update() {
        upperLimit = RobotSensors.pickupSystemUpLim.get();
        lowerLimit = RobotSensors.pickupSystemDownLim.get();
        ballInPickupLimit = RobotSensors.ballReadyToLiftLim.get();
        
        armPotentiometer = RobotSensors.pickupPotentiometer.get();
        
        RobotActuators.pickupRollerArmMotor.set(rollerMotorSpeed);
        RobotActuators.pickupMechMotor.set(armMotorSpeed);
        
        //rollGamePieceOutButton = FancyJoystick.secondary.getRawButton(FancyJoystick.BUTTON_B);
        
        SmartDashboard.putBoolean("upperLimit", upperLimit);
        SmartDashboard.putBoolean("lowerLimit", lowerLimit);
        SmartDashboard.putBoolean("ballInPickupLimit", ballInPickupLimit);
        SmartDashboard.putNumber("armPotentiometer", armPotentiometer);
        SmartDashboard.putNumber("Current Stage", currentStage);
    }
}
    