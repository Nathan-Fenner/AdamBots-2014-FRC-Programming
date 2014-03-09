/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

/**
 *
 * @author Ben
 */
public class MathUtils {
    //// MATH CONSTANTS --------------------------------------------------------

    public static final double TWO_PI = Math.PI * 2;
    public static final double PI_OVER_FOUR = Math.PI / 4;
    public static final double PI_OVER_TWO = Math.PI / 2;
    public static final double PI_OVER_180 = Math.PI / 180;
    public static final double PI_UNDER_180 = 180 / Math.PI;

    //// RANDOM VARIABLES ------------------------------------------------------
    static long x = System.currentTimeMillis();
    static long y = System.currentTimeMillis() + 2065101653;
    static long z = System.currentTimeMillis() + 989498661;
    static long w = System.currentTimeMillis() + 626378301;
    static long v = System.currentTimeMillis() + 914577441;

    //// ROUNDING --------------------------------------------------------------
    /**
     * Rounds a double to a certain number of decimal places.
     *
     * @param n The number to round.
     * @param digit 10 to round to tens, 0.1 to round to tenths, etc.
     * @return
     */
    public static double roundTo(double n, double digit) {
	return Math.floor(n / digit + 0.5) * digit;
    }

    /**
     * Rounds down a double to a certain number of decimal places.
     *
     * @param n The number to round.
     * @param digit 10 to round to tens, 0.1 to round to tenths, etc.
     * @return
     */
    public static double floorTo(double n, double digit) {
	return Math.floor(n / digit) * digit;
    }

    /**
     * Rounds up a double to a certain number of decimal places.
     *
     * @param n The number to round.
     * @param digit .1 to round to tens, 10 to round to tenths.
     * @return
     */
    public static double ceilTo(double n, double digit) {
	return Math.ceil(n / digit) * digit;
    }

    //// SIGN ------------------------------------------------------------------
    public static double sign(double n) {
	return (n > 0.0) ? 1.0 : ((n < 0.0) ? -1.0 : 0.0);
    }

    public static float sign(float n) {
	return (n > 0.0f) ? 1.0f : ((n < 0.0f) ? -1.0f : 0.0f);
    }

    public static int sign(int n) {
	return (n > 0) ? 1 : ((n < 0) ? -1 : 0);
    }

    public static double exp(double x) {
	double s = 0;
	double a = 1;
	double[] r = {1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880, 3628800};
	for (int i = 0; i < r.length; i++) {
	    s += a / r[i];
	    a *= x;
	}
	return s;
    }
    
    //// RANDOM NUMBER GENERATOR -----------------------------------------------
    public static double rand(int high) {
	long t;
	t=(x^(x>>7));
	x=y;
	y=z;
	z=w;
	w=v; 
	v=(v^(v<<6))^(t^(t<<13));
	return (Math.abs((y+y+1)*v) % (20000 + Math.PI)) / (20000 + Math.PI) * high;
    }
}
