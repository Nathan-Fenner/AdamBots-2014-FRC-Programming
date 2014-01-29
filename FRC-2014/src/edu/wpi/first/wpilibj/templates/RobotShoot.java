/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 *
 * @author Nathan
 */
public class RobotShoot {
    public static boolean limitShooter;
    public static boolean limitLatched;
    public static boolean limitBuckleValue;
    public static double WIND_SPEED;
    public static Timer timerRelatch = null;
    public static Timer timerLatch;
    public static final int ENCODER_REVOLUTIONS = 5;
    public static final double UNIWIND_SPEED = -.5;
    /*public static DigitalInput limitBuckle;
    public static DigitalInput limitLatched;
    public static Solenoid latchSolenoid;
    public static final int LIMIT_BUCKLE = 8;
    public static final int LIMIT_LATCHED = 9;
    public static Victor motorShooter;
    public static Encoder shooterEncoder;
    */
    public static int revolutionsOfShooter;
    public static double time;
    public static double b;
    public static double rewindMaxRevolutions = 5;
    
    /**
     * This method returns the tension (in encoder ticks or something) in terms
     * of the distance in feet to the target.
     *
     * @param distance The distance to the target in feet
     * @return The tension required to hit the target
     */

    /**
     * this will initialize the victors limit switches and pneumatics
     */
    public static void init() {  //this is the initialization method-surprise surpriise
        timerLatch = new Timer();
        time = 0;
        b = 0;
    }
    
    public static void AutomatedShoot(){  //this does an automatic shot and reload
        RobotShoot.update();
        RobotShoot.releaseBall();
        RobotShoot.unwindShooter();
        RobotShoot.rewindShooter();
    }
    public static void releaseBall() {  //this will release the ball when it is loaded
        if (RobotPickUp.ifLoaded()) {
            RobotActuators.LATCH.set(false);
            
        } else {
            SmartDashboard.putString("Error", "Ball not loaded");
            //or blink a light
        }
        
    }

    /*public static void timerForRelatch() {
        timerRelatch = new Timer();
        a = timerRelatch.get();
    }*/

    private static int getEncoderValue() {  //this gets the encoder value to be used by the unwindShooter method
        revolutionsOfShooter = RobotSensors.SHOOTER_WHINCH_ENCODER.get();
        return revolutionsOfShooter;
    }
    
    public static void unwindShooter() {    //this uwnids the shooter     
        boolean getOut = true;
        while (!limitLatched || !getOut) {
            RobotActuators.SHOOTER_WINCH.set(UNIWIND_SPEED);
            if(RobotShoot.getEncoderValue()== rewindMaxRevolutions){
                RobotActuators.SHOOTER_WINCH.set(0);
            }
            time = timerLatch.get();
            if(time>500){
                RobotActuators.SHOOTER_WINCH.set(0);
                SmartDashboard.putString("Error", "The shooter is reloading, try Shooting again");
                getOut = false;
            }
        }
    }
    public static void rewindShooter() {  //this rewinds the shooter at the wind speed
        while (!limitBuckleValue) {
            RobotActuators.SHOOTER_WINCH.set(WIND_SPEED);
        }
    }

    public static void update() {  //this is the update method to get all of our values that are needed
        if (timerRelatch != null) {
            b = timerRelatch.get();
            if (time - b > 500) {
                RobotActuators.LATCH.set(true);
                timerRelatch = null;
            }
        }
        limitBuckleValue = RobotSensors.SHOOTER_LOADED_LIM.get();   //SHOOTER_LOADED_LIM
        limitShooter = RobotSensors.SHOOTER_UNLOADED_LIM.get();    //SHOOTER_UNLOADED_LIM
        limitLatched = RobotSensors.SHOOTER_LATCHED_LIM.get();   //SHOOTER_LATCHED_LIM
    }
}
