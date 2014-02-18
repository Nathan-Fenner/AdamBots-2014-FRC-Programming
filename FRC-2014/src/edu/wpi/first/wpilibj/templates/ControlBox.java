/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Nathan
 */
public class ControlBox {
	/*
	 #1       #2     #3

	 #4            B1            B2

	 #5

	 #6            B1            B2

	 #7

	 #8            B1            B2

	 */

	public static DriverStation driverStation;

	public static void initialize() {
		driverStation = DriverStation.getInstance();
	}

	public static boolean getDigitalIn(int channel) {
		return driverStation.getDigitalIn(channel);
	}

	public static double getAnalogIn(int channel) {
		return driverStation.getAnalogIn(channel);
	}
	public static void update() {
		for (int i = 1; i <= 14; i++) {
			SmartDashboard.putBoolean("Switch " + i,driverStation.getDigitalIn(i));
		}
	}
}
