/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.io.InputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.SocketConnection;

/**
 *
 * @author Curtis Fenner
 */
public class RobotVision {

	private static Timer timer = new Timer();
	private static String database = "";
	private static final String BEAGELIP = "10.2.45.3:3000";
	private static double previousEncoder = 1000;

	public static double getEncoder() {
		double d;
		double ticks;
		if (ControlBox.isRed()) {
			d = redDistance();
		} else {
			d = blueDistance();
		}
		if (d <= 5) {
			return previousEncoder;
		}
		ticks = 1.4674 * d * d - 27.253 * d + 1226.5;
		previousEncoder = ticks;
		return Math.max(500, Math.min(1500, previousEncoder));
	}

	public static void initialize() {
		timer.start();
		System.out.println("Robot Vision Intialize");
		Thread q = new Thread(new Runnable() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(30);
						retrieve();
					} catch (Exception e) {
						//System.out.println("Exception in thread: " + e);
					}
				}
			}
		});
		q.start();
	}

	public static String getProperty(String s) {
		if (database == null) {
			return "";
		}
		String key = "";
		String val = "";
		boolean mode = true;
		for (int i = 0; i < database.length(); i++) {
			char c = database.charAt(i);
			if (c == ':') {
				mode = false;
				continue;
			}
			if (c == '\n') {
				mode = true;
				if (key.equals(s)) {
					return val;
				}
				key = "";
				val = "";
				continue;
			}
			if (mode) {
				key += c;
			} else {
				val += c;
			}
		}
		return "";
	}

	public static double parseNumber(String s) {
		try {
			s = s.trim();
			return Double.parseDouble(s);
		} catch (Exception e) {
			return 0.0;
		}
	}

	public static double getNumber(String key) {
		return parseNumber(getProperty(key));
	}

	public static boolean isHot() {
		return getNumber("hot") > 40;
	}
	
	public static double getDistance() {
		if (ControlBox.isRed()) {
			return redDistance();
		}
		return blueDistance();
	}

	public static double redDistance() {
		return getNumber("red");
	}

	public static double blueDistance() {
		return getNumber("blue");
	}

	public static double redBallAngle() {
		return (getNumber("red ball") / 320 - 0.5) * 40;
	}

	public static double blueBallAngle() {
		return (getNumber("blue ball") / 320 - 0.5) * 40;
	}

	public static double highBlueBall() {
		return getNumber("blue high");
	}

	public static double highRedBall() {
		return getNumber("red high");
	}

	public static double blueBallDist() {
		return getNumber("blue ball dist");
	}

	public static double redBallDist() {
		return getNumber("red ball dist");
	}
	static SocketConnection http = null;
	static InputStream data = null;

	public static void retrieve() {
		boolean connectionFailure = true;
		try {
			http = (SocketConnection) Connector.open("socket://" + BEAGELIP);
			connectionFailure = false;
			data = http.openInputStream();
			String mdatabase = "";
			int p = 1;
			int length = 0;
			int failTime = 0;
			while (p >= 0 && length < 100 && failTime < 300) { // this is on the robot.
				if (data.available() > 0) {
					p = data.read();
					mdatabase += (char) p;
					length++;
					failTime = 0;
				} else {
					try {
						Thread.sleep(20);	
					} catch (Exception e) {
						
					}
					failTime += 20;
				}
			}
			System.out.println("RobotVision message received:\n\t" + length + "/100 , " + failTime + "/300ms");
			System.out.println("database:" + mdatabase);
			data.close();
			http.close();

			database = mdatabase;
			//SmartDashboard.putNumber("vision DATABASE SIZE",database.length());

		} catch (Exception e) {
			//System.out.println("Exception in RobotVision.retrieve() (networking):");
			//System.out.println("\t" + e);
			//System.out.println("\t" + e.getMessage());
		}
		try {
			data.close();
		} catch (Exception e) {
			//System.out.println("Error Closing Data: " + e);
		}
		try {

			http.close();
		} catch (Exception e) {
			//System.out.println("Error Closing HTTP: " + e);
		}
		if (connectionFailure) {
			//System.out.println("Connect Failure, gcing");
			double t = timer.get();
			System.gc();
			//System.out.println("GC took " + (timer.get() - t) + " seconds");
			try {
				Thread.sleep(30000);
			} catch (Exception e) {
			}
		}
	}
}
