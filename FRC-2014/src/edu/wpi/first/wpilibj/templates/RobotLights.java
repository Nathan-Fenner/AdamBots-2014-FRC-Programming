/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;
import edu.wpi.first.wpilibj.DigitalInput;

/**
 *
 * @author Tyler
 */
public class RobotLights {
    
////VARIABLES-------------------------------------------------------------------
    private DigitalInput liar;
    
    
    
////CONSTANTS-------------------------------------------------------------------
    
    public void init() {
	//INSERT CODE HERE
    }
    
    public void shootingLights() {
	//INSERT CODE HERE
    }
    
    public boolean ifReady() {
	//INSERT CODE HERE
	return
    }
    
    public boolean ifJammed() {
	//INSERT CODE HERE
	return
    }
    
    public void lightResponse() {
	boolean ifReady = ifReady();
	boolean ifJammed = ifJammed();
	if(ifReady)
	    ;
	else
	    ; //Error message
	if(ifJammed)
	    ; //Error message
	else
	    ;

	//INSERT CODE HERE
    }
    
    public void underglow() {
	//INSERT CODE HERE
    }
    
    public void cameraSpotlight() {
	//INSERT CODE HERE
    }
    
    public void confuse() {
	//INSERT CODE HERE
	for(int loop = 0; loop < 100; loop++)
	{
	    //turn on
	    //turn off
	    delay(50);
	}
    }
    
    public void delay(int time) {
	try {
	    Thread.sleep(time);
	} catch (InterruptedException e) {
	    System.out.println(e);
	
    }
    }
}
