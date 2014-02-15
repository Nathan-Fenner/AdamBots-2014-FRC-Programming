/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Tyler
 */
public class RobotActuators {

	public static Victor leftDrive;
	public static Victor rightDrive;
	public static Talon pickupRollerArmMotor;
	public static Talon pickupMechMotor;
	public static Talon shooterWinch;
	public static Relay compressor;
	public static Relay cameraLEDs;
	public static Solenoid shifter;
	public static Solenoid rollerArmUp;
	public static Solenoid rollerArmDown;
	public static Solenoid latchRelease;
	public static Solenoid greenLEDStrip;
	public static Solenoid redLEDStrip;
	public static Solenoid yellowGroundFXStrip;
	public static Relay groundLEDStrip1;
	public static Relay groundLEDStrip2;
	public static Relay groundLEDStrip3;
	public static Relay groundLEDStrip4;
	public static Relay LEDStripRightPanel;
	public static Relay LEDStripLeftPanel;

	public static void initialize() {
		// Motors
		rightDrive = new Victor(1);
		leftDrive = new Victor(2);
		pickupMechMotor = new Talon(4);
		pickupRollerArmMotor = new Talon(3);
		shooterWinch = new Talon(5);

		// Relays
		compressor = new Relay(1, Relay.Direction.kForward);
		cameraLEDs = new Relay(2, Relay.Direction.kForward);

		// Solenoids
		shifter = new Solenoid(1);
		rollerArmUp = new Solenoid(2);
		rollerArmDown = new Solenoid(3);
		latchRelease = new Solenoid(4);
		greenLEDStrip = new Solenoid(5);
		redLEDStrip = new Solenoid(6);
		yellowGroundFXStrip = new Solenoid(7);

		// Output
		System.out.println("RobotActuautors init done");
	}
}
