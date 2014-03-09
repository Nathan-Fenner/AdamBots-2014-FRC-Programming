/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO;
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
	public static DriverStationEnhancedIO enhancedStation;

	public static boolean isRed() {
		return driverStation.getAlliance().value == Alliance.kRed_val;
	}

	public static void initialize() {
		driverStation = DriverStation.getInstance();
		enhancedStation = driverStation.getEnhancedIO();
	}

	public static boolean getDigitalIn(int channel) {
		return driverStation.getDigitalIn(channel);
		/*
		try {
			int lookAt = -1 - (int) enhancedStation.getDigitals();
			int pow = 1;
			for (int j = 0; j < channel; j++) {
				pow *= 2;
			}
			lookAt = lookAt % pow;
			lookAt /= (pow / 2);
			SmartDashboard.putNumber("Digitals", -1 - (int) enhancedStation.getDigitals());
			return lookAt <= 0;
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;*/
	}

	public static double getAnalogIn(int channel) {
		return driverStation.getAnalogIn(channel);
	}

	public static void update() {
		// nothing to do here
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
	private static boolean getLeftSwitch(int which) {
		return !getDigitalIn(9 + which);
	}

	/**
	 *
	 * @param which from left to right (1-3)
	 * @return
	 */
	private static boolean getTopSwitch(int which) {
		return getDigitalIn(10 - which);
	}
}
