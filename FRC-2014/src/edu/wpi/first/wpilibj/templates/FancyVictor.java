/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Victor;

/**
 *
 * @author Tyler
 */
public class FancyVictor {
    //// VARIABLES -------------------------------------------------------------
    private Victor v1;
    private Victor v2;
    
    //// CONSTRUCTORS ----------------------------------------------------------
    FancyVictor(int port1, int port2) {
	v1 = new Victor(port1);
	v2 = new Victor(port2);
    }
    
    FancyVictor() {
	v1 = null;
	v2 = null;
    }
    
    //// METHODS ---------------------------------------------------------------
    // sets the speed of both motors
    public void set(double speed) {
	v1.set(speed);
	v2.set(speed);
    }
	
}
