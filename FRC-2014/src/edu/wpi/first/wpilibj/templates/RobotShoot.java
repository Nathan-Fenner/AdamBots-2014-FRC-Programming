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
    public static double speed;
    public static double WIND_SPEED = .3;
    public static final double UNWIND_SPEED = -.3;
    private static Timer timerRelatch;
    public static final int ENCODER_REVOLUTIONS = 5;
    public static int revolutionsOfShooter;
    private static double time;
    private static final double rewindMaxRevolutions = 5000;

    public static void initialize() {
        time = 0;
		RobotSensors.shooterWinchEncoder.start();
		latched = false;
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
            //timerRelatch.start();
            //time = timerRelatch.get();
        }
        RobotShoot.unwindShooter();
    }

    /**
     * This releases the ball if it is loaded, and if it is not, it displays
     * that it is not.
     */
    public static void releaseBall() {
        if (RobotPickUp.ifLoaded()|| true) { //TODO: Remove true||, and start the timer here
            RobotActuators.latchRelease.set(false);
			timerRelatch.start();
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
    private static int getEncoderValue() {
     return RobotSensors.shooterWinchEncoder.get();
     } 
    /**
     * This will unwind the shooter if .5 seconds have passed, and until the
     * limit switch is reached or until it has exceeded the max number of
     * revolutions.
     */
    public static void unwindShooter() {
        if (timerRelatch == null && !latched) {
            needsToBeUnwound = true;
        } else {
            needsToBeUnwound = false;
        }
    }

    /**
     * It rewinds the shooter until the limit switch has been reached or again
     * max number of revolutions
     */
    public static void rewindShooter() {
        if (!windMax) {
            needsToBeWound = true;
        } else {
            needsToBeWound = false;
        }
    }

    public static void stopShooter() {
        RobotActuators.shooterWinch.set(0);
    }

    static int rc = 0;

    /**
     * This method will set the movement of the shooter winch based on two
     * booleans which represent two buttons
     *
     * @param forward
     * @param backward
     */
    public static void manualWind(boolean forward, boolean backward) {

        if (forward && !backward) {
            speed = WIND_SPEED;
            System.out.println("Wind Speed: " + WIND_SPEED);
        } else if (!forward && backward) {
            speed = UNWIND_SPEED;
            System.out.println("Wind Speed: " + UNWIND_SPEED);
        } else {
            speed = 0;
        }
        RobotActuators.shooterWinch.set(speed);
    }
    /**
     * This will get all of the new values that we need as well as setting the
     * shooter speed
     */
    public static void update() {
        if (timerRelatch != null) {
            double b = timerRelatch.get();
            if (b /*- time*/ > .5) {
                timerRelatch = null;
                RobotSensors.shooterWinchEncoder.reset();
				System.out.println("reset encoder");
                unwindShooter();
            }
        }
        if (RobotShoot.getEncoderValue() >= rewindMaxRevolutions) {
            needsToBeUnwound = false;
         }
        if (RobotShoot.getEncoderValue() <= 0){
            needsToBeWound = true;
        }
        if (needsToBeWound && latched) {
            RobotActuators.shooterWinch.set(WIND_SPEED); //TODO: Change speed to constant value WIND_SPEED
        }
        if (needsToBeUnwound && !latched) {
            RobotActuators.shooterWinch.set(UNWIND_SPEED); //TODO: Change speed to constant value UNWIND_SPEED
        }
        if (!needsToBeUnwound && latched) {
            RobotShoot.rewindShooter();
        }

        windMax = RobotSensors.shooterLoadedLim.get();   //SHOOTER_LOADED_LIM
        latched = RobotSensors.shooterAtBack.get();   //SHOOTER_LATCHED_LIM
        if (latched && !windMax) {
            RobotActuators.latchRelease.set(true);  //true means it goes in
        }

        //System.out.println("sneedsToBeUnwound: " + needsToBeUnwound);
        //System.out.println("needsToBeWound: " + needsToBeWound);
        //System.out.println("latched: " + latched + rc++);

        //System.out.println("windMax: " + windMax + rc++);
        
        System.out.println(getEncoderValue()); //FOR TESTING PURPOSES ONLY
    }
}
//IF WE WANT A MANUAL SHOT YOU WILL NEED TO SET THE MOTOR IN TELEOP
