/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

/**
 *
 * @author Nathan
 */
public class RobotShoot {
    private static double tensionTarget;
    private static double winchValue;
    public static double targetPosition = 0.0;//where it should be
    
    /**
     * This method returns the tension (in encoder ticks or something) in terms
     * of the distance in feet to the target.
     * @param distance The distance to the target in feet
     * @return The tension required to hit the target
     */
    public static double getTensionFromDistance(double distance){
        double tension = 0.0;
        return tension;
    }
    /**
     * this will initialize the victors limit switches and pneumatics 
     */
    public static void initialize(){
        
    }
    /**
     * we will initialize the encoders and then output the values
     * @return the angle that the shooter wheel has revolved
     */
    public static void encodersINIT() {
        
    }
    public static void updateTension(){
        //update the tension
    }
    public static void setTension(double amount){
        tensionTarget = amount;
    }
    public static double getTension() {
        return 0; //will return actual encoder value
    }
    
    public static void initializeWinch(){
        //we will initialize the winch
    }
    public static double currentWinchValue(double winchValue){
        return winchValue;
    }
    public static boolean isBallLoaded(){
        //initialize limit switch for this
        //use limit switch to check if ball is there
        boolean checkIfLoaded = true;
        
        return checkIfLoaded;
    }
    public static void ReleaseBall(){
        if(RobotShoot.isBallLoaded()){
            //initialize the limit switcha nd the pnuematic shifters
            //release the ball
        }
        else
            System.out.println("Shooter jammed");
        }
    }
    
    public static void initializeJamDetector(){
        //initialize switches or whatever they are using
    }
    public static double findCurrentPosition(){
        //find the position
        double currentPosition = 0.0;
        return currentPosition;
    }
    public static boolean isJammed(){
        if(RobotShoot.findCurrentPosition()== targetPosition){
            return false;
        }
        else{
            return true;
        }
    }
}