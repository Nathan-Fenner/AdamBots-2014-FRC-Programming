package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Roi and Simon
 */
public class RobotShoot {

    ////VARIABLES AND CONSTANTS-------------------------------------------------
    private static boolean needsToBeWound;
    private static boolean needsToBeUnwound;
    private static boolean latched;
    private static boolean windMax;
    public static double WIND_SPEED;
    private static Timer timerRelatch;
    public static final int ENCODER_REVOLUTIONS = 5;
    public static final double UNWIND_SPEED = -.5;
    public static int revolutionsOfShooter;
    private static double time;
    private static final double rewindMaxRevolutions = 5000;

    public static void initialize() {
        time = 0;
    }

    /**
     * It is a test method to test the shooter
     */
    public static void testShooter() {
        RobotShoot.automatedShoot();
    }

    /**
     * This is an automated Shooter to be used
     */
    public static void automatedShoot() {
        //RobotShoot.releaseBall();
        if (timerRelatch == null) {
            timerRelatch = new Timer();
            timerRelatch.start();
            time = timerRelatch.get();
        }
        RobotShoot.unwindShooter();
    }

    /**
     * This releases the ball if it is loaded, and if it is not, it displays
     * that it is not.
     */
    public static void releaseBall() {
        if (true || RobotPickUp.ifLoaded()) { //TODO: Remove true||, and start the timer here
            RobotActuators.latch.set(false);
            System.out.println("Success: Ball has been shot");
        } else {
            SmartDashboard.putString("Error", "Ball not loaded");
            //or blink a light
        }
    }

    /**
     * it gets the encoder value of the shooter wheel
     *
     * @returns the encoderValue
     */
    /*private static int getEncoderValue() {
     *  revolutionsOfShooter = RobotSensors.shooterWinchEncoder.get();
     return revolutionsOfShooter;
     } */
    /**
     * This will unwind the shooter if .5 seconds have passed, and until the
     * limit switch is reached or until it has exceeded the max number of
     * revolutions.
     */
    public static void unwindShooter() {
        if (timerRelatch == null && !latched) {
            needsToBeUnwound = true;
        }

    }

    /**
     * It rewinds the shooter until the limit switch has been reached or again
     * max number of revolutions
     */
    public static void rewindShooter() {
        if (!windMax) {
            needsToBeWound = true;
        }
    }

    public static void stopShooter() {
        RobotActuators.shooterWinch.set(0);
    }

    /**
     * This will get all of the new values that we need as well as setting the
     * shooter speed
     */
    public static void update() {
        if (timerRelatch != null) {
            double b = timerRelatch.get();
            if (b - time > .5) {
                timerRelatch = null;
                unwindShooter();
            }
        }
        /* if (RobotShoot.getEncoderValue() >= rewindMaxRevolutions) {
         needsToBeUnwound = false;
         needsToBeWound = false;
         }*/
        if (needsToBeWound && latched) {
            RobotActuators.shooterWinch.set(WIND_SPEED);
        }
        if (needsToBeUnwound && !latched) {
            RobotActuators.shooterWinch.set(UNWIND_SPEED);
        }
        if (!needsToBeUnwound && latched) {
            RobotShoot.rewindShooter();
        }
        windMax = RobotSensors.shooterLoadedLim.get();   //SHOOTER_LOADED_LIM
        latched = RobotSensors.shooterAtBack.get();   //SHOOTER_LATCHED_LIM
        if (latched && !windMax) {
            RobotActuators.latch.set(true);  //true means it goes in
        }
        System.out.println("\nlatched: " + latched);
        System.out.println("needsToBeUnwound: " + needsToBeUnwound);
        System.out.println("needsToBeWound: " + needsToBeWound);
        System.out.println("windMax: " + windMax);
    }
}
//IF WE WANT A MANUAL SHOT YOU WILL NEED TO SET THE MOTOR IN TELEOP
