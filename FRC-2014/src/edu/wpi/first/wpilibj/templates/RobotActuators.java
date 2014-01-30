/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;

/**
 *
 * @author Tyler
 */
public class RobotActuators {

    public static Victor leftDrive;
    public static Victor rightDrive;
    public static Talon pickupRollerArmMotor;
    public static Talon pickupSystemMotor;
    public static Talon shooterWinch;
    public static Relay compressor;
    public static Solenoid shifterPiston;
    public static Solenoid rollerArmUp;
    public static Solenoid rollerArmDown;
    public static Solenoid latch;

    public void initialize() {
        //INSERT CODE HERE
        rightDrive = new Victor(1);
        leftDrive = new Victor(2);
        pickupSystemMotor = new Talon(3);
        pickupRollerArmMotor = new Talon(4);
        shooterWinch = new Talon(5);
        
        compressor = new Relay(1);
        shifterPiston = new Solenoid(1);
        rollerArmUp = new Solenoid(2);
        rollerArmDown = new Solenoid(3);
        latch = new Solenoid(4); //IS THIS ONE RIGHT?
    }
}
