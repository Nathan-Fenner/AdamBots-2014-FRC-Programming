/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import java.util.Vector;

/**
 *
 * @author Blu
 */
public abstract class PeriodicRegister {

    private static Vector registered = new Vector();

    public static void register(Periodic per, String name) {
        registered.addElement(per);
        System.out.println("PeriodicRegister: " + name + " registered.");
    }

    public static void periodic() {
        for (int i = 0; i < registered.size(); i++) {
            Periodic p = (Periodic) registered.elementAt(i);
            p.periodic();
        }
    }
}
