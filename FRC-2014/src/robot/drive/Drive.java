/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robot.drive;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.templates.RobotSensors;
import edu.wpi.first.wpilibj.templates.RobotActuators;

/**
 *
 * @author Robin Onsay
 */
public class Drive {
////VARIABLES-------------------------------------------------------------------	

    private static Timer time;
    public static double targetDistance;
    public static double distance;
////CONSTANTS-------------------------------------------------------------------
    public static final double WHEEL_DIAMETER = 0.1624;
    public static final double NO_SPEED = 0.0;
    public static final double MAX_SPEED = 1.0;
    public static final double MIN_SPEED = -1.0;
////INIT------------------------------------------------------------------------

    public static void initialize() {
        RobotSensors.RIGHT_DRIVE_ENCODER.start();
        RobotSensors.LEFT_DRIVE_ENCODER.start();
    }

////SUBCLASS--------------------------------------------------------------------
    public class DistanceCorrection {
////CLASS METHODS---------------------------------------------------------------

        double difference = targetDistance - distance;

        public double setTargetDistance(double target) {
            targetDistance = target;
            return targetDistance;
        }

        public double setDistance(double dist) {
            distance = dist;
            return distance;
        }

        public double setDistancePerTick(double wheelDiameterM /*in meters*/) {
            final double CIRC = wheelDiameterM * Math.PI;
            double distPerTick = CIRC / 360;
            RobotSensors.RIGHT_DRIVE_ENCODER.setDistancePerPulse(distPerTick);
            RobotSensors.LEFT_DRIVE_ENCODER.setDistancePerPulse(distPerTick);
            double RequiredTicks = difference * (1 / distPerTick);
            return RequiredTicks;
        }

        double[] convertToTime() {
            double[] speed = {RobotSensors.RIGHT_DRIVE_ENCODER.getRate(), RobotSensors.LEFT_DRIVE_ENCODER.getRate()};
            double[] time = new double[2];
            for (int i = 0; i < speed.length; i++) {
                time[i] = (1 / speed[i]) * this.setDistancePerTick(WHEEL_DIAMETER);
            }
            return time;
        }

        public void correct(double speed) {
            double[] targetTime = this.convertToTime();
            time.start();
            while (time.get() != targetTime[0] || time.get() != targetTime[1]) {
                RobotActuators.rightDrive.set(speed);
                RobotActuators.leftDrive.set(speed);
            }
            RobotActuators.rightDrive.set(NO_SPEED);
            RobotActuators.leftDrive.set(NO_SPEED);
        } 
    }


    public static void drive(double axisTrigger, double leftJoy) {
        //Robin, plz help, I need your wisdom!		
        double turnRightVic = -axisTrigger - leftJoy;
        double turnLeftVic = axisTrigger + leftJoy;

        RobotActuators.rightDrive.set(speedLimiter(turnRightVic));
        RobotActuators.leftDrive.set(speedLimiter(turnLeftVic));
    }

    public static double speedLimiter(double trigger) {
        if (trigger < MIN_SPEED) {
            trigger = MIN_SPEED;
        }
        if (trigger > MAX_SPEED) {
            trigger = MAX_SPEED;
        }
        return trigger;
    }

    public static void robotStop() {
            RobotActuators.leftDrive.set(NO_SPEED);
            RobotActuators.rightDrive.set(NO_SPEED);
    }
}
