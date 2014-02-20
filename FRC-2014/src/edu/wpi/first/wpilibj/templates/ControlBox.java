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
	 .	#9	#8	#7

	 #10I	#3I		#4I

	 #11I

	 #12I	#5I		#6I

	 #13I

	 #14I	#1I		#2I
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
			SmartDashboard.putBoolean("Switch " + i, driverStation.getDigitalIn(i));
		}
	}

	public static boolean getYellowButtonUpLeft() {
		return !getDigitalIn(3);
	}

	public static boolean getYellowButtonUpRight() {
		return !getDigitalIn(4);
	}

	public static boolean getYellowButtonDownLeft() {
		return !getDigitalIn(5);
	}

	public static boolean getYellowButtonDownRight() {
		return !getDigitalIn(6);
	}

	public static boolean getBlackButtonLeft() {
		return !getDigitalIn(1);
	}

	public static boolean getBlackButtonRight() {
		return !getDigitalIn(2);
	}

	/**
	 *
	 * @param which numbered from top to bottom (1-5)
	 * @return
	 */
	public static boolean getLeftSwitch(int which) {
		return !getDigitalIn(9 + which);
	}

	/**
	 *
	 * @param which from left to right (1-3)
	 * @return
	 */
	public static boolean getTopSwitch(int which) {
		return getDigitalIn(10 - which);
	}
}
