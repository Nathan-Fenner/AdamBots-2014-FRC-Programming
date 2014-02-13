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
    public static final double SHOOTER_POSITION = 50.0;
    public static final double PICKUP_TOLERANCE = 2.0;
    public static final double MAX_SPEED = 1.0;
    public static final double MIN_SPEED = -1.0;
    public static final double LOWER_LIMIT_POSITION = 100.0;
    public static final double UPPER_LIMIT_POSITION = 0.0;
    
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
    public static double offset;
    
    // initializes everything
    public static void init() {
	rollerArmUp = false;
	rollerArmDown = true;
	pickupMechSpeed = 0.0;
	gamePieceIntakeSpeed = 0.0;
	offset = 0.0;
    }
    
    // moves to the desired shoot location
    // Might have to be done using PIDs later on
    public static void moveToShoot() {
	if (pickupEncoder - offset <= SHOOTER_POSITION - PICKUP_TOLERANCE) {
	    pickupMechSpeed = MAX_SPEED;
	} else if (pickupEncoder - offset >= SHOOTER_POSITION + PICKUP_TOLERANCE) {
	    pickupMechSpeed = MIN_SPEED;
	} else {
	    pickupMechSpeed = 0.0;
	}
    }
    
    // moves the pickup mech to the floor
    public static void moveToFloor() {
	if (!lowerLimit) {
	    pickupMechSpeed = MIN_SPEED;
	} else {
	    resetEncoder();
	    pickupMechSpeed = 0.0;
	}
    }
    
    // moves the pickup mech to catch
    public static void moveToCatch() {
	if (!upperLimit) {
	    pickupMechSpeed = MAX_SPEED;
	} else {
	    resetEncoder();
	    pickupMechSpeed = 0.0;
	}
    }
    
    // moves the pickup mech while ignoring the Encoder
    public static void movePickupIgnoreEncoder(double speed) {
	pickupMechSpeed = speed;
    }
    
    // moves the pickup mech and doesnt ignore the encoder
    //// TODO: TAKE THE || TRUE OUT WHEN EVERYTHING IS WORKING
    public static void movePickup(double speed) {
	if ((pickupEncoder - offset < SHOOTER_POSITION - PICKUP_TOLERANCE || pickupEncoder - offset > SHOOTER_POSITION + PICKUP_TOLERANCE) && (true || RobotShoot.unwind())) {
            pickupMechSpeed = speed;
        } else {
	    pickupMechSpeed = 0.0;
	}
        safety();
    }
    
    // checks if the gamePiece is in the position to be loaded
    public static boolean isLoaded() {
	return loaded;
    }
    
    // raises the top arm
    public static void raiseTopArm() {
	if (pickupMechSpeed == 0 && lowerLimit) {
	    resetEncoder();
	    rollerArmUp = true;
	    rollerArmDown = false;
	}
    }
    
    // lowers the top arm
    public static void lowerTopArm() {
	rollerArmUp = false;
	rollerArmDown = true;
    }
    
    // intakes the game piece
    public static void intakeGamePiece() {
	if (!loaded) {
	    gamePieceIntakeSpeed = MAX_SPEED;
	} else {
	    gamePieceIntakeSpeed = 0.0;
	}
    }
    
    // passes the game piece
    public static void passGamePiece() {
	gamePieceIntakeSpeed = MIN_SPEED;
    }
    
    // stops the pickup mech
    public static void stopMovingMech() {
	pickupMechSpeed = 0.0;
    }
    
    // stops the game piece intake mech
    public static void stopGamePieceMech() {
	gamePieceIntakeSpeed = 0.0;
    }
    
    // Estops the entire pickup
    public static void estopPickup() {
	pickupMechSpeed = 0.0;
	gamePieceIntakeSpeed = 0.0;
	rollerArmUp = false;
	rollerArmDown = false;
    }
    
    // Resets the Encoder
    public static void resetEncoder() {
	if (upperLimit) {
	    offset = RobotSensors.pickupPotentiometer.get() + UPPER_LIMIT_POSITION;
	} else if (lowerLimit) {
	    offset = -RobotSensors.pickupPotentiometer.get() + LOWER_LIMIT_POSITION;
	}
    }
    
    // Safety to make sure that it stops when it hits the limit switch
    public static void safety() {
	if (lowerLimit && pickupMechSpeed < 0) {
	    pickupMechSpeed = 0;
	    resetEncoder();
	} else if (upperLimit && pickupMechSpeed > 0) {
	    pickupMechSpeed = 0;
	    resetEncoder();
	}
	if (loaded) {
	    gamePieceIntakeSpeed = 0;
	}
    }
    
    // Updates everything
    public static void update() {
	// gets all the sensors needed
	pickupEncoder = RobotSensors.pickupPotentiometer.get();
	lowerLimit = RobotSensors.pickupSystemDownLim.get();
	upperLimit = RobotSensors.pickupSystemUpLim.get();
	loaded = RobotSensors.shooterLoadedLim.get();
	
	// sets all the actuators needed
	RobotActuators.rollerArmUp.set(rollerArmUp);
	RobotActuators.rollerArmDown.set(rollerArmDown);
	RobotActuators.pickupMechMotor.set(pickupMechSpeed);
	RobotActuators.pickupRollerArmMotor.set(gamePieceIntakeSpeed);
    }
}