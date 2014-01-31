package edu.wpi.first.wpilibj.templates;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/**
  
 * @author Roi and Simon
 */
public class RobotShoot {
    ////VARIABLES AND CONSTANTS-------------------------------------------------
    public static boolean hasBeenUnwinded;
    public static boolean needsToBeWound;
    public static boolean needsToBeUnwound;
    public static boolean limitShooter;
    public static boolean limitLatched;
    public static boolean limitBuckleValue;
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
    public static void testShooter(){  //this is just for testing purposes and will be ccommented out
        RobotShoot.update();
        RobotShoot.releaseBall();
        RobotShoot.unwindShooter();
        RobotShoot.rewindShooter();
    }
    public static void automatedShoot(){  //this does an automatic shot and reload
        RobotShoot.update();
        RobotShoot.releaseBall();
        RobotShoot.unwindShooter();
        RobotShoot.rewindShooter();
    }
    public static void releaseBall() {  //this will release the ball when it is loaded
        if (RobotPickUp.ifLoaded()) {
            RobotActuators.latch.set(false);
            hasBeenUnwinded = false;
        } else {
            SmartDashboard.putString("Error", "Ball not loaded");
            //or blink a light
        }
    }
    private static int getEncoderValue() {  //this gets the encoder value to be used by the unwindShooter method
        revolutionsOfShooter = RobotSensors.shooterWinchEncoder.get();
        return revolutionsOfShooter;
    }
    //true meaning unwind, false means dont unwind
    public static void unwindShooter() {     
       if(timerRelatch==null && !limitShooter){
           hasBeenUnwinded = false;
           needsToBeUnwound = true;
            }
       if(RobotShoot.getEncoderValue()>= rewindMaxRevolutions){
           needsToBeUnwound = false;
           hasBeenUnwinded = true;
        }
       else{
           needsToBeUnwound = false;
       }
    }
    public static void rewindShooter() {  //this rewinds the shooter at the wind speed limitBuckleValue
        if(RobotShoot.getEncoderValue() == rewindMaxRevolutions){
            needsToBeWound = false;
        }
        if(!limitBuckleValue){
            needsToBeWound = true;
        }
    }

    public static void update() {  //this is the update method to get all of our values that are needed
        if (timerRelatch != null) {
            b = timerRelatch.get();
            if (time - b > 500) {
                RobotActuators.latch.set(true);
                timerRelatch = null;
            }
        }
        if(needsToBeWound && hasBeenUnwinded){
            RobotActuators.shooterWinch.set(WIND_SPEED);
        }
        if(needsToBeUnwound && !hasBeenUnwinded){
            RobotActuators.shooterWinch.set(WIND_SPEED);
        }
        limitBuckleValue = RobotSensors.shooterLoadedLim.get();   //SHOOTER_LOADED_LIM
        limitShooter = RobotSensors.shooterUnloadedLim.get();    //SHOOTER_UNLOADED_LIM
        limitLatched = RobotSensors.shooterLatchedLim.get();   //SHOOTER_LATCHED_LIM
    }
}
//IF WE WANT A MANUAL SHOT YOU WILL NEED TO SET THE MOTOR IN TELEOP