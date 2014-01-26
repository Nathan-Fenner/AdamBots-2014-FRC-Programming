/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package robot.drive;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Victor;
import robot.drive.*;
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
        public static final double noMove = 0.0;
        public static final double maxSpeed = 1.0;
        public static final double minSpeed = -1.0;
		
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
        public void driveTeleop(double leftTrigger, double rightTrigger, double leftJoy, double BTTN_X, double BTTN_A /*Figure out what to put here later*/) {
            if(true) //BTTN_X
                ;//Shift to high gear
            else     //BTTN_A
                ;//Shift to low gear
            
            if(leftTrigger < minSpeed)
                leftTrigger = minSpeed;
            else if(leftTrigger > maxSpeed)
                leftTrigger = maxSpeed;
            if(rightTrigger > maxSpeed)
                rightTrigger = maxSpeed;
            else if(rightTrigger < minSpeed)
                rightTrigger = minSpeed;
            
            if(leftTrigger == noMove && rightTrigger != noMove)
            {
                if(leftJoy == noMove)
                {
                    right1Victor.set(maxSpeed);
                    left1Victor.set(maxSpeed);
                }
                else
                {
                    right1Victor.set(rightTrigger - leftJoy);
                    left1Victor.set(rightTrigger + leftJoy);
                }
            }
            else if(leftTrigger != noMove && rightTrigger == noMove)
            {
                if(leftJoy == noMove)
                {
                    right1Victor.set(minSpeed);
                    left1Victor.set(minSpeed);
                }
                else
                {
                    right1Victor.set(leftTrigger - leftJoy);
                    left1Victor.set(leftTrigger + leftJoy);
                }
            }
            else if(leftTrigger == noMove && rightTrigger == noMove)
            {
                if(leftJoy == noMove)
                {
                    right1Victor.set(noMove);
                    left1Victor.set(noMove);
                }
                else
                {
                    right1Victor.set(-leftJoy);
                    left1Victor.set(leftJoy);
                }
            }
        }
}
