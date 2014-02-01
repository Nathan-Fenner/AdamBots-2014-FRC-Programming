package edu.wpi.first.wpilibj.templates;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/**
  
 * @author Roi and Simon
 */
public class RobotShoot {
    ////VARIABLES AND CONSTANTS-------------------------------------------------
    public static boolean notNeedingToBeUnwound;
    public static boolean needsToBeWound;
    public static boolean needsToBeUnwound;
    public static boolean unwindMax;
    public static boolean latched;
    public static boolean windMax;
    public static double WIND_SPEED;
    public static Timer timerRelatch;
    public static Timer timerLatch;
    public static final int ENCODER_REVOLUTIONS = 5;
    public static final double UNIWIND_SPEED = -.5;
    public static int revolutionsOfShooter;
    public static double time;
    public static double b;
    public static double rewindMaxRevolutions = 5;
    public static void initialize() {  //this is the initialization method-surprise surpriise
        timerLatch = new Timer();
        time = 0;
        b = 0;
    }
    /**
     * It is a test method to test the shooter
     */
    public static void testShooter(){  
        RobotShoot.automatedShoot();
    }
    /**
     * This is an automated Shooter to be used
     */
    public static void automatedShoot(){  
        RobotShoot.update();
        RobotShoot.releaseBall();
        RobotShoot.unwindShooter();
        RobotShoot.rewindShooter();
    }
    /**
     * This releases the ball if it is loaded, and if it is not, it displays that it is not.
     */
    public static void releaseBall() {  
        if (RobotPickUp.ifLoaded()) {
            RobotActuators.latch.set(false);
            notNeedingToBeUnwound = false;
        } else {
            SmartDashboard.putString("Error", "Ball not loaded");
            //or blink a light
        }
    }
   /**
    * it gets the encoder value of the shooter wheel 
    * @returns the encoderValue
    */
    private static int getEncoderValue() { 
        revolutionsOfShooter = RobotSensors.shooterWinchEncoder.get();
        return revolutionsOfShooter;
    }
    /**
     * This will unwind the shooter if .5 seconds have passed, and until the limit switch
     * is reached or until it has exceeded the max number of revolutions.
     */
    public static void unwindShooter() {     
       if(timerRelatch==null && !unwindMax){
           notNeedingToBeUnwound = false;
           needsToBeUnwound = true;
            }
       if(RobotShoot.getEncoderValue()>= rewindMaxRevolutions){
           needsToBeUnwound = false;
           notNeedingToBeUnwound = true;
        }
       else{
           needsToBeUnwound = false;
       }
    }
    /**
     * It rewinds the shooter until the limit switch has been reached or again max number of revolutions
     */
    public static void rewindShooter() {  
        if(RobotShoot.getEncoderValue() == rewindMaxRevolutions){
            needsToBeWound = false;
        }
        if(!windMax){
            needsToBeWound = true;
        }
    }
    /**
     * this will get all of the new values that we need as well as setting the shooter speed
     */
    public static void update() {  //this is the update method to get all of our values that are needed
        if (timerRelatch != null) {
            b = timerRelatch.get();
            if (time - b > 500) {
                RobotActuators.latch.set(true);
                timerRelatch = null;
            }
        }
        if(needsToBeWound && notNeedingToBeUnwound){
            RobotActuators.shooterWinch.set(WIND_SPEED);
        }
        if(needsToBeUnwound && !notNeedingToBeUnwound){
            RobotActuators.shooterWinch.set(WIND_SPEED);
        }
        windMax = RobotSensors.shooterLoadedLim.get();   //SHOOTER_LOADED_LIM
        unwindMax = RobotSensors.shooterUnloadedLim.get();    //SHOOTER_UNLOADED_LIM
        latched = RobotSensors.shooterLatchedLim.get();   //SHOOTER_LATCHED_LIM
    }
}
//IF WE WANT A MANUAL SHOT YOU WILL NEED TO SET THE MOTOR IN TELEOP