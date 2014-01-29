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
    public static double a;
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
    public static void init() {
        a = 0;
        b = 0;
    }
    
    public static void AutomatedShoot(){
        RobotShoot.update();
        RobotShoot.releaseBall();
        RobotShoot.unwindShooter();
        RobotShoot.rewindShooter();
    }
    public static void releaseBall() {
        if (RobotPickup.isBallLoaded()) {
            RobotActuators.latchSolenoid.set(false);
            
        } else {
            SmartDashboard.putString("Error", "Ball not loaded");
            //or blink a light
        }
        
    }

    /*public static void timerForRelatch() {
        timerRelatch = new Timer();
        a = timerRelatch.get();
    }*/

    private static int getEncoderValue() {
        revolutionsOfShooter = RobotSensors.shooterEncoder.get();
        return revolutionsOfShooter;
    }
    
    public static void unwindShooter() {
        timerLatch = new Timer();
        while (!limitLatched) {
            RobotActuators.motorShooter.set(UNIWIND_SPEED);
            if(RobotShoot.getEncoderValue()>= rewindMaxRevolutions){
                RobotActuators.motorShooter.set(0);
            }
            double a = timerLatch.get();
            if(a>500){
                RobotShoot.rewindShooter();
                SmartDashboard.putString("Error", "The shooter is experiencing dificulties, your next shot may be off");
            }
        }
    }
    public static void rewindShooter() {
        if(RobotSensors.limitLatched.get()){
            while (!limitBuckleValue) {
                RobotActuators.motorShooter.set(WIND_SPEED);
        }
    }
        else{
            unwindShooter();
            SmartDashboard.putString("Error", "It did not latch properly:trying again");
        }
    }
    public static void update() {
        if (timerRelatch != null) {
            b = timerRelatch.get();
            if (a - b > 500) {
                RobotActuators.latchSolenoid.set(true);
                timerRelatch = null;
            }
        }
        limitBuckleValue = RobotSensors.limitBuckle.get();
        limitShooter = RobotSensors.limitShooter.get();    
        limitLatched = RobotSensors.ShooterLatched.get();
    }
    public static void TerminateShooter(){
        RobotActuators.motorShooter.set(0);
        SmartDashboard.putString("Error", "You have just killed the shooter.");
    }
}   
