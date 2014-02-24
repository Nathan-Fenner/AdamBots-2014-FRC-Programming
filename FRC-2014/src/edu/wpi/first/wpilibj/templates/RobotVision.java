/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Timer;
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
		return getNumber("hot") > 0.5 && getNumber("hot") < 1.5;
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
			int p = data.read();
			while (p >= 0) {
				mdatabase += (char) p;
				p = data.read();
			}
			data.close();
			http.close();

			database = mdatabase;

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