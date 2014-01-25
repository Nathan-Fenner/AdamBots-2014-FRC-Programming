/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package robot.drive;
import edu.wpi.first.wpilibj.Joystick;
/**
 *
 * @author Robin Onsay
 */
public class NotSoFancyJoystick extends Joystick {

	public NotSoFancyJoystick(int port) {
		super(port);
	}
	public NotSoFancyJoystick(int port, final double deadZone){
		super(port);
		
	}
	
}
