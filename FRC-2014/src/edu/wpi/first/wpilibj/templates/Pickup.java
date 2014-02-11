/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;

/**
 *
 * @author Tyler
 */
public class Pickup {
    
    //// VARIABLES -------------------------------------------------------------
    public static final double SHOOTER_POSITION = 50;
    public static final double PICKUP_TOLERANCE = 2;
    
    //// SETTING VARIABLES -----------------------------------------------------
    public static boolean rollerArmUp;
    public static boolean rollerArmDown;
    public static double pickupMechSpeed;
    public static double gamePieceIntakeSpeed;
    
    //// GETTING VARIABLES -----------------------------------------------------
    public static double pickupEncoder;
    public static boolean lowerLimit;
    public static boolean upperLimit;
    public static boolean loaded;
    
    // initializes everything
    public static void init() {
	
    }
    
    // moves to the desired shoot location
    // Might have to be done using PIDs later on
    public static void moveToShoot() {
	if (pickupEncoder < SHOOTER_POSITION) {
	    pickupMechSpeed = 1.0;
	} else if (pickupEncoder > SHOOTER_POSITION) {
	    pickupMechSpeed = -1.0;
	} else {
	    pickupMechSpeed = 0.0;
	}
    }
    
    // moves the pickup mech to the floor
    public static void moveToFloor() {
	if (!lowerLimit) {
	    pickupMechSpeed = -1.0;
	} else {
	    pickupMechSpeed = 0.0;
	}
    }
    
    // moves the pickup mech to catch
    public static void moveToCatch() {
	if (!upperLimit) {
	    pickupMechSpeed = 1.0;
	} else {
	    pickupMechSpeed = 0.0;
	}
    }
    
    // moves the pickup mech
    public static void movePickup(double speed) {
	pickupMechSpeed = speed;
    }
    
    // checks if the gamePiece is in the position to be loaded
    public static boolean isLoaded() {
	return loaded;
    }
    
    // raises the top arm
    public static void raiseTopArm() {
	rollerArmUp = true;
        rollerArmDown = false;
    }
    
    // lowers the top arm
    public static void lowerTopArm() {
	rollerArmUp = false;
	rollerArmDown = true;
    }
    
    // intakes the game piece
    public static void intakeGamePiece() {
	if (!loaded) {
	    gamePieceIntakeSpeed = 1.0;
	} else {
	    gamePieceIntakeSpeed = 0.0;
	}
	
    }
    
    // passes the game piece
    public static void passGamePiece() {
	gamePieceIntakeSpeed = -1.0;
    }
    
    // stops the pickup mech
    public static void stopMovingMech() {
	pickupMechSpeed = 0.0;
    }
    
    // stops the game piece intake mech
    public static void stopGamePieceMech() {
	gamePieceIntakeSpeed = 0.0;
    }
    
    // updates everything
    public static void update() {
	// gets all the sensors needed
	pickupEncoder = RobotSensors.pickupPotentiometer.get();
	lowerLimit = RobotSensors.pickupSystemDownLim.get();
	upperLimit = RobotSensors.pickupSystemUpLim.get();
	loaded = RobotSensors.shooterLoadedLim.get();
	
	// sets all the actuators needed
	RobotActuators.rollerArmUp.set(rollerArmUp);
	RobotActuators.rollerArmDown.set(rollerArmDown);
	RobotActuators.pickupSystemMotor.set(pickupMechSpeed);
	RobotActuators.pickupSystemMotor.set(gamePieceIntakeSpeed);
    }
}
