/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Nathan
 */
public class RobotShoot {

    public static boolean limitBuckleValue;
    public static double WIND_SPEED;
    public static Timer timerRelatch = null;
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

    }

    public static void releaseBall() {
        if (RobotPickup.isBallLoaded()) {
            latchSolenoid.set(false);
        } else {
            SmartDashboard.putString("Error", "Ball not loaded");
            //or blink a light
        }
    }

    public static void relatch() {
        timerRelatch = new Timer();
        a = timerRelatch.get();
    }

    private static int getEncoderValue() {
        revolutionsOfShooter = shooterEncoder.get();
        return revolutionsOfShooter;
    }

    public static void unwindShooter() {
        //counter
        while (getEncoderValue() != ENCODER_REVOLUTIONS) {
            motorShooter.set(UNIWIND_SPEED);
        }
    }
    public static void rewindShooter() {
        while (!limitBuckleValue) {
            motorShooter.set(WIND_SPEED);
        }
    }

    public static void update() {
        if (timerRelatch != null) {
            b = timerRelatch.get();
            if (b - a > 500) {
                latchSolenoid.set(true);
                timerRelatch = null;
            }
        }
        limitBuckleValue = RobotSensors.limitBuckle.get();
    }
}
