/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.AnalogChannel;

/**
 *
 * @author Tyler
 */
public class AnalogSwitch {
	
	//// VARIABLES -------------------------------------------------------------
	private AnalogChannel swit; // truely named switch but that's reserved
	
	//// CONSTRUCTORS ----------------------------------------------------------
	// constructor that takes a channel
	public AnalogSwitch(int channel) {
		swit = new AnalogChannel(channel);
	}
	
	// constructor that takes a channel and a sidecard number
	public AnalogSwitch(int card, int channel) {
		swit = new AnalogChannel(card, channel);
	}
	
	//// METHODS ---------------------------------------------------------------
	// gets wether the switch is considered on or off
	public boolean get() {
		return swit.getVoltage() >= 2.5;
	}
}
