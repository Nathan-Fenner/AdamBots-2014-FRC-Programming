/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package robot.drive;
import edu.wpi.first.wpilibj.*;

/**
 *
 * @author Robin Onsay
 */
public class Drive {
////VARIABLES-------------------------------------------------------------------
	private Timer time;
	private Victor right1Victor;	
	private Victor left1Victor;	
	private Pneumatics shift;
	private static Encoder encoderR;
	private static Encoder encoderL;
	public static double targetDistance;
	public static double distance;
////CONSTANTS-------------------------------------------------------------------
	public static final double WHEEL_DIAMETER = 0.1624;
	public static final double NO_SPEED = 0.0;
	public static final double MAX_SPEED = 1.0;
	public static final double MIN_SPEED = -1.0;		
////INIT------------------------------------------------------------------------
	public void init(){
		encoderR.start();
		encoderL.start();
		
	}
////CONSTRUCTOR-----------------------------------------------------------------
	public void Drive(){
		shift = new Pneumatics();
	}
	
////SUBCLASS--------------------------------------------------------------------
	public class DistanceCorrection{
////CLASS METHODS---------------------------------------------------------------
		double difference = targetDistance - distance;
		
		public double setTargetDistance(double target){
			targetDistance = target;		
			return targetDistance;
		}
		public double setDistance(double dist){
			distance = dist;
			return distance;
		}		
		public double setDistancePerTick(double wheelDiameterM /*in meters*/){
			final double CIRC = wheelDiameterM * Math.PI;
			double distPerTick = CIRC/360;
			encoderR.setDistancePerPulse(distPerTick);
			encoderL.setDistancePerPulse(distPerTick);			
			double RequiredTicks = difference * (1/distPerTick);
			return RequiredTicks;
		}		
		double[] convertToTime(){
			double[] speed = {encoderR.getRate(), encoderL.getRate()};
			double[] time = new double[2]; 
			for(int i = 0; i < speed.length; i++){
				time[i]= (1/speed[i]) * this.setDistancePerTick(WHEEL_DIAMETER);
			}
			return time;
		}
		public void correct(double speed){
			double[] targetTime = this.convertToTime();
			time.start();
			while(time.get() != targetTime[0] || time.get() != targetTime[1]){
				right1Victor.set(speed);
				left1Victor.set(speed);
			}
			right1Victor.set(NO_SPEED);
			left1Victor.set(NO_SPEED);
		}
	}
	
	
	public class DriveTele{
		public void shift( boolean bttn1, boolean bttn2){
			if(bttn1){
				shift.shiftIt(true);//Shift to high gear
			}else if(bttn2){
				shift.shiftIt(false);//Shift to low gear
			}	
		}
		public void drive(double axisTrigger, double leftJoy) {           
            //Robin, plz help, I need your wisdom!		
		double turnRightVic = -axisTrigger - leftJoy;
		double turnLeftVic = axisTrigger + leftJoy;	
		
		this.speedLimiter(turnRightVic);
		this.speedLimiter(turnLeftVic);			
		
		right1Victor.set(turnRightVic);		
		left1Victor.set(turnLeftVic); 
		}        
        public double speedLimiter(double trigger) {
			if(trigger < MIN_SPEED){
				trigger = MIN_SPEED;
			}
			if(trigger< MAX_SPEED){
				trigger = MAX_SPEED;
			}
			return trigger;
			}
	}
}
