/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package robot.drive;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Victor;
/**
 *
 * @author Robin Onsay
 */
public class Drive {
	////VARIABLES---------------------------------------------------------------
	private Victor right1Victor;
	//private Victor right2Victor;
	private Victor left1Victor;
	//private Victor left2Victor;
	private static Encoder encoderRight;
	private static Encoder encoderLeft;
	public static double targetDistance;
	public static double distance;
		
	////CONSTRUCTOR-------------------------------------------------------------
	public void Drive(){
		
	}
	
	////METHODS-----------------------------------------------------------------
	public double setTargetDistance(double target){
		targetDistance = target;		
		return targetDistance;
	}
	public double setDistance(double dist){
		distance = dist;
		return distance;
	}
	public static double setDistancePerTick(double wheelDiameterM){
		double circ = wheelDiameterM * Math.PI;
		double distPerTick = circ/360;
		encoderRight.setDistancePerPulse(distPerTick);
		return distPerTick;
	}
	public void correctDistance(){
		if(targetDistance != distance){
			if(targetDistance > distance){
				double dist = Drive.setDistancePerTick(0.1624);
				double difference = targetDistance - distance;
				double ticks = difference/dist;
				
				
			}else{
				
			}
		}
	}
}
