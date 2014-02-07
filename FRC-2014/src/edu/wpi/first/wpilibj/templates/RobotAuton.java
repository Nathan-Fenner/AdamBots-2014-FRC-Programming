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
public class RobotAuton {
    
    public static double averageEncoder;
    
    public static void autonOne() {
    }
    
    public static void update() {
	averageEncoder = (RobotSensors.leftDriveEncoder.get() + RobotSensors.rightDriveEncoder.get())/2.0;
    }
}
