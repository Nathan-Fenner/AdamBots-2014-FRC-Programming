package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Josh and Debjit
 */
public class RobotPickupCatchingSequence {
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
    public static String warningMessage = "SHOOTER IS NOT LOADED";
////INIT------------------------------------------------------------------------
    //initializes the encoder and motor speeds

    public static void initialize() {
        rollerMotorSpeed = 0.5;
        armMotorSpeed = 0.5;
        allowedStage = 0;
    }

    //checks if shooter is loaded
    public static boolean ifLoaded() {
        return RobotSensors.shooterAtBack.get();
    }
    
    //stage 1- move to vertical position
    public static void moveToVerticalPosition() {
        currentStage = 1;
        if(ifLoaded() && !upperLimit)
            armMotorSpeed = 1.0;
        else if(!ifLoaded())
            SmartDashboard.putString("WARNING", warningMessage);
        else {
            armMotorSpeed = 0;
            allowedStage = 1;
        }
    }
    
    //stage 2-opens upper arm
    public static void openUpperArm() {
        currentStage = 2;
        RobotActuators.rollerArmUp.set(true);
        RobotActuators.rollerArmDown.set(false);
        allowedStage = 2;
    }
    
    //stage 3-closes upper arm
    public static void closeUpperArm() {
        currentStage = 3;
        RobotActuators.rollerArmUp.set(false);
        RobotActuators.rollerArmDown.set(true);
        allowedStage = 3;
    }
    //stage 4-move pickup to down position
    public static void lowerPickup() {
        currentStage = 4;
        if(!lowerLimit)
            armMotorSpeed = -1.0;
        else {
            armMotorSpeed = 0;
            allowedStage = 4;
        }
            
    }
    
    //combined catch sequence
    public static void catchingSequence() {
        if(allowedStage == 0)
            moveToVerticalPosition();
        else if(allowedStage == 1)
            openUpperArm();
        else if(allowedStage == 2)
            closeUpperArm();
        else if(allowedStage == 3)
            lowerPickup();
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
        
        //raiseUpperArmButton = FancyJoystick.secondary.getRawButton(FancyJoystick.BUTTON_X);

        SmartDashboard.putBoolean("upperLimit", upperLimit);
        SmartDashboard.putBoolean("lowerLimit", lowerLimit);
        SmartDashboard.putBoolean("ballInPickupLimit", ballInPickupLimit);
        SmartDashboard.putNumber("armPotentiometer", armPotentiometer);
        SmartDashboard.putNumber("Current Stage", currentStage);
    }
}
