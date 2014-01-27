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
			RobotActuators..set(true);
		}else{		
			shift.set(false);
		}
	}
	
	public void runCompressor(){
		if(pressureSwitch.get()){
			compressor.set(Relay.Value.kOff);
		}else{
			compressor.set(Relay.Value.kOn);
		}
	}
	public void autoShift(double sensitvity, boolean shiftLow /* in G's*/){
		double xAcceleration = shiftAuto.getAcceleration(ADXL345_I2C.Axes.kX);
		double zAcceleration = shiftAuto.getAcceleration(ADXL345_I2C.Axes.kZ);
		if(xAcceleration > sensitvity && zAcceleration > sensitvity){
			shift.set(false);
		}else if(shiftLow == true || xAcceleration < sensitvity && zAcceleration < sensitvity){
			shift.set(true);
		}
	}
	
}
