package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/**
 *
 * @author Josh and Debjit
 */
public class RobotPickupShooterSequence {
////VARIABLES-------------------------------------------------------------------
    public static boolean upperLimit;
    public static boolean lowerLimit;
    public static boolean ballInPickupLimit;
    public static boolean readyToShootButton;
////CONSTANTS-------------------------------------------------------------------
    public static double armEncoder;
    public static double armMotorSpeed;
    public static double rollerMotorSpeed;
    public static double armPotentiometer;
    public static int allowedStage; //has nothing to do with current stage
    public static int currentStage; //here it is
    public static final double idealPotPosition = 0.6; //must determine actual value
    public static final double potError = 0.01; //range of accepted values (+/-)
////INIT------------------------------------------------------------------------
    //initializes the encoder and motor speeds
    public static void initialize(){
        rollerMotorSpeed = 0.5;
        armMotorSpeed = 0.5;
        allowedStage = 0;
        readyToShootButton = false;
    }
    
    //checks if shooter is loaded
    public static boolean ifLoaded() {
        return RobotSensors.shooterAtBack.get();   
    }
    
    //stage 1- moves arm to game piece
    public static void moveArmTowardsGamePiece(){
        currentStage = 1;
        if(!lowerLimit)
            armMotorSpeed = 1.0;
        else {
            armMotorSpeed = 0.0;
            allowedStage = 1;
        }
    }
    
    //stage 2- rotates rollers to bring game piece in to pickup
    public static void rotateRollerBackwards() {
        currentStage = 2;
        if(!ballInPickupLimit)
            rollerMotorSpeed = 1.0;
        else {
            rollerMotorSpeed = 0; 
            allowedStage = 2;
        }
    }
    
    //stage 3- moves pickup to shooting position
    public static void moveShooterUp() {
        currentStage = 3;
        if(ifLoaded()){
            if(armPotentiometer < idealPotPosition - potError)
                armMotorSpeed = 1.0;
            else if(armPotentiometer > idealPotPosition + potError)
                armMotorSpeed = -1.0;
            else
                armMotorSpeed = 0.0;
                allowedStage = 3;
        }           
    }
    
    //stage 4- moves roller arm for shooting
    public static void rollerArmLift() {
        currentStage = 4;
        RobotActuators.rollerArmUp.set(true);
        RobotActuators.rollerArmDown.set(false);
        allowedStage = 4;
    }    
    
    //stage 5- returns roller arm after shooting
    public static void rollerArmReturn() {
        currentStage = 5;
        RobotActuators.rollerArmUp.set(false);    
        RobotActuators.rollerArmDown.set(true);
        allowedStage = 5;
    }
    
    //stage 6- returns pickup to lower position
    public static void lowerPickupPosition() {
        currentStage = 6;
        if (!lowerLimit)
            armMotorSpeed = -1.0;
        else {
            armMotorSpeed = 0.0;
            allowedStage = 6;
        }
    }
    
    //sequence of events for shooting
    public static void shooterSequence() {
        if(allowedStage == 0)
            moveArmTowardsGamePiece();
        else if(allowedStage == 1)
            rotateRollerBackwards();
        else if(allowedStage == 2)
            moveShooterUp();
        else if(allowedStage == 3)
            rollerArmLift();
        else if(allowedStage == 4)
            rollerArmReturn();
        else if(allowedStage == 5)
            lowerPickupPosition();
        else
            allowedStage = 0;
    }

    //Determines the direction the pickup must move to get in shooting position
    //NOT USED
    public static void directionOfMovement() {
        if(armPotentiometer < idealPotPosition - potError)
            armMotorSpeed = 1.0;
        else if(armPotentiometer > idealPotPosition + potError)
            armMotorSpeed = -1.0;
        else
            armMotorSpeed = 0;
    }
    
    //updates variables
    public static void update() {
        upperLimit = RobotSensors.pickupSystemUpLim.get();
        lowerLimit = RobotSensors.pickupSystemDownLim.get();
        ballInPickupLimit = RobotSensors.ballReadyToLiftLim.get();
        
        armPotentiometer = RobotSensors.pickupPotentiometer.get();
        
        RobotActuators.pickupRollerArmMotor.set(rollerMotorSpeed);
        RobotActuators.pickupSystemMotor.set(armMotorSpeed);
        
        readyToShootButton = FancyJoystick.secondary.getRawButton(FancyJoystick.BUTTON_A);
        
        SmartDashboard.putBoolean("upperLimit", upperLimit);
        SmartDashboard.putBoolean("lowerLimit", lowerLimit);
        SmartDashboard.putBoolean("ballInPickupLimit", ballInPickupLimit);
        SmartDashboard.putNumber("armPotentiometer", armPotentiometer);
        SmartDashboard.putNumber("Current Stage", currentStage);
    }
}
