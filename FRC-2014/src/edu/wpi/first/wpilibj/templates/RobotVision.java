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

    public static boolean isHot() {
        return database != null && database.toLowerCase().indexOf("hot") >= 0;
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