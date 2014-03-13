/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Relay;

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

    //randomly turning on and off lights
    /*public void swagMode() {
        int strand = (int) MathUtils.rand(6) + 1;
        switch (strand) {
            case 1:
                if (RobotActuators.groundLEDStrip1.get() == Relay.Value.kOn) {
                    RobotActuators.groundLEDStrip1.set(Relay.Value.kOff);
                } else {
                    RobotActuators.groundLEDStrip1.set(Relay.Value.kOn);
                }
                break;
            case 2:
                if (RobotActuators.groundLEDStrip2.get() == Relay.Value.kOn) {
                    RobotActuators.groundLEDStrip2.set(Relay.Value.kOff);
                } else {
                    RobotActuators.groundLEDStrip2.set(Relay.Value.kOn);
                }

                break;
            case 3:
                if (RobotActuators.groundLEDStrip3.get() == Relay.Value.kOn) {
                    RobotActuators.groundLEDStrip3.set(Relay.Value.kOff);
                } else {
                    RobotActuators.groundLEDStrip3.set(Relay.Value.kOn);
                }
                break;
            case 4:
                if (RobotActuators.groundLEDStrip4.get() == Relay.Value.kOn) {
                    RobotActuators.groundLEDStrip3.set(Relay.Value.kOff);
                } else {
                    RobotActuators.groundLEDStrip4.set(Relay.Value.kOn);
                }

                break;
            case 5:
                if (RobotActuators.LEDStripLeftPanel.get() == Relay.Value.kOn) {
                    RobotActuators.LEDStripLeftPanel.set(Relay.Value.kOff);
                } else {
                    RobotActuators.LEDStripLeftPanel.set(Relay.Value.kOn);
                }
                break;
            case 6:
                if (RobotActuators.LEDStripRightPanel.get() == Relay.Value.kOn) {
                    RobotActuators.LEDStripRightPanel.set(Relay.Value.kOff);
                } else {
                    RobotActuators.LEDStripRightPanel.set(Relay.Value.kOn);
                }
                break;
            default:
                System.out.println("rand: " + strand);
                break;
        }
    }*/

    public void shootingLights() {
        //INSERT CODE HERE
    }

    public boolean ifReady() {
        //INSERT CODE HERE
        return true;
    }

    public boolean ifJammed() {
        //INSERT CODE HERE
        return true;
    }

    public void lightResponse() {
        boolean ifReady = ifReady();
        boolean ifJammed = ifJammed();
        if (ifReady)
	    ; else
	    ; //Error message
        if (ifJammed)
	    ; //Error message
        else
	    ;

        //INSERT CODE HERE
    }

    public static void underglowOn() {
        //INSERT CODE HERE
        // turns on
        // RobotActuators.groundLEDStrip1.set(Relay.Value.kOn);
        // turns off
        // RobotActuators.groundLEDStrip1.set(Relay.Value.kOff);
        RobotActuators.groundLEDStrip1.set(true);
        RobotActuators.groundLEDStrip2.set(true);
        RobotActuators.groundLEDStrip3.set(true);
        RobotActuators.groundLEDStrip4.set(true);

    }

    public static void underglowOff() {
        RobotActuators.groundLEDStrip1.set(false);
        RobotActuators.groundLEDStrip2.set(false);
        RobotActuators.groundLEDStrip3.set(false);
        RobotActuators.groundLEDStrip4.set(false);
    }

    public void cameraSpotlight() {
        //INSERT CODE HERE
    }

    public void confuse() {
        //INSERT CODE HERE
        for (int loop = 0; loop < 100; loop++) {
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
