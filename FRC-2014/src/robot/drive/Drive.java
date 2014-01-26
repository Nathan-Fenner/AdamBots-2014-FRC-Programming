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
	public static final double WHEEL_DIAMETER = 0.1624;
		
	////CONSTRUCTOR-------------------------------------------------------------
	public void Drive(){
		
	}
	
	////SUBCLASS----------------------------------------------------------------
	public class DistanceCorrection{
	////CLASS METHODS-----------------------------------------------------------
		public double setTargetDistance(double target){
			targetDistance = target;		
			return targetDistance;
		}
		public double setDistance(double dist){
			distance = dist;
			return distance;
		}
		void startEncoder(){
			encoderRight.start();
			encoderLeft.start();
		}
		public double setDistancePerTick(double wheelDiameterM /*in meters*/){
			double circ = wheelDiameterM * Math.PI;
			double distPerTick = circ/360;
			encoderRight.setDistancePerPulse(distPerTick);
			encoderLeft.setDistancePerPulse(distPerTick);
			return distPerTick;
		}
		public void correctDistance(double speed){
			if(targetDistance != distance){			
				double distPerTick = this.setDistancePerTick(Drive.WHEEL_DIAMETER);
				double difference = targetDistance - distance;
				double RequiredTicks = difference * (1/distPerTick);				
				this.startEncoder();
				if(difference < 0){
					while(encoderRight.get()!= RequiredTicks || encoderLeft.get() != RequiredTicks){
						right1Victor.set(speed);
						left1Victor.set(speed);
						}
				}else if(difference > 0){
					while(encoderRight.get()!= RequiredTicks || encoderLeft.get() != RequiredTicks){
						right1Victor.set(-speed);
						left1Victor.set(-speed);
					}
				}else{
					right1Victor.set(0);
					left1Victor.set(0);
					}
				}		
			}
		}
        ////METHODS-------------------------------------------------------------
        public void driveTeleop(double trigger ) {
            
        }
}
