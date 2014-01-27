/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;


/**
 *
 * @author Robin Onsay
 */
public class Pneumatics{
	////VARIABLES---------------------------------------------------------------	
	
	////CONSTRUCTOR-------------------------------------------------------------
	public void shifting(){
		
	}
	////METHODS-----------------------------------------------------------------
	public void shiftIt(boolean buttonShift/**true = high, false = low**/){
		if(buttonShift){
			RobotActuators.SHIFTER_PISTON.set(true);
		}else{		
			RobotActuators.SHIFTER_PISTON.set(false);
		}
	}
	
	public void runCompressor(){
		if(RobotSensors.PREASSURE_SWITCH.get()){
			RobotActuators.COMPRESSOR.set(Relay.Value.kOff);
		}else{
			RobotActuators.COMPRESSOR.set(Relay.Value.kOn);
		}
	}
	public void autoShift(double sensitvity, boolean shiftLow /* in G's*/){
		double xAcceleration = RobotSensors.ACCELEROMETER.getAcceleration(ADXL345_I2C.Axes.kX);
		double zAcceleration = RobotSensors.ACCELEROMETER.getAcceleration(ADXL345_I2C.Axes.kZ);
		if(xAcceleration > sensitvity && zAcceleration > sensitvity){
			RobotActuators.SHIFTER_PISTON.set(false);
		}else if(shiftLow == true || xAcceleration < sensitvity && zAcceleration < sensitvity){
			RobotActuators.SHIFTER_PISTON.set(true);
		}
	}
	
}
