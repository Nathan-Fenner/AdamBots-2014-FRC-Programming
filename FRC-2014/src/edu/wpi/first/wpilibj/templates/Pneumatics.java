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
	public static void shifting(){
		
	}
	////METHODS-----------------------------------------------------------------
	public static void shiftIt(boolean buttonShift/**true = high, false = low**/){
		if(buttonShift){

			RobotActuators.shifterPiston.set(true);
		}else{		
			RobotActuators.shifterPiston.set(false);
		}
	}
	
	public static void runCompressor(){
		if(RobotSensors.pressureSwitch.get()){
			RobotActuators.compressor.set(Relay.Value.kOff);
		}else{
			RobotActuators.compressor.set(Relay.Value.kOn);
		}
	}
	public static void autoShift(double sensitvity, boolean shiftLow /* in G's*/){
		double xAcceleration = RobotSensors.accelerometer.getAcceleration(ADXL345_I2C.Axes.kX);
		double zAcceleration = RobotSensors.accelerometer.getAcceleration(ADXL345_I2C.Axes.kZ);
		if(xAcceleration > sensitvity && zAcceleration > sensitvity){
			RobotActuators.shifterPiston.set(false);
		}else if(shiftLow == true || xAcceleration < sensitvity && zAcceleration < sensitvity){
			RobotActuators.shifterPiston.set(true);

			RobotActuators.shifterPiston.set(true);
		}else{		
			RobotActuators.shifterPiston.set(false);
		}
	}	
}
