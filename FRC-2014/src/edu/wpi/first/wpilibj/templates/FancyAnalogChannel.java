package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.PIDSource;
/**
 *
 * @author roi
 */
public class FancyAnalogChannel extends AnalogPotentiometer implements PIDSource{
    public FancyAnalogChannel(int channel){
        super(channel);
    }
    public double pidGet(){
        return get() * 73.015 - 179.257;
        // return RobotSensors.pickupPotentiometer.get() * 74.522 - 258.68;
    }
}
