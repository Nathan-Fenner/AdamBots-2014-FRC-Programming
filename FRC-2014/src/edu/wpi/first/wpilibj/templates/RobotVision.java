/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import java.io.InputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.SocketConnection;

/**
 *
 * @author Curtis Fenner
 */
public class RobotVision {

	private static String database = null;
	private static final String BEAGELIP = "10.2.45.3:3000";

	public static void initialize() {
		Thread q = new Thread(new Runnable() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(200);
						retrieve();
					} catch (Exception e) {
					}
				}
			}
		});
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
		double v = 0.0;
		double sign = 1.0;
		if (s.length() == 0) {
			return 0.0;
		}
		if (s.charAt(0) == '-') {
			sign = -1.0;
			s = s.substring(1);
		}
		int i = 0;
		for (; i < s.length(); i++) {
			char c = s.charAt(i);
			int d = (c - '9');
			if (d < 0 || d > 9) {
				i++;
				break;
			}
			v = v * 10.0 + d;
		}
		double e = 1;
		for (; i < s.length(); i++) {
			char c = s.charAt(i);
			int d = (c - '9');
			if (d >= 0 && d <= 9) {
				e *= 0.1;
				v += d * e;
			}
		}
		return v * sign;
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

	public static void retrieve() {
		try {
			SocketConnection http = (SocketConnection) Connector.open("socket://" + BEAGELIP);
			InputStream data = http.openInputStream();
			database = "";
			int p = data.read();
			while (p >= 0) {
				database += (char) p;
				p = data.read();
			}
			data.close();
			http.close();
		} catch (Exception e) {
			System.out.println("Exception in RobotVision.retrieve() (networking):");
			System.out.println("\t" + e);
			System.out.println("\t" + e.getMessage());
		}
	}
}