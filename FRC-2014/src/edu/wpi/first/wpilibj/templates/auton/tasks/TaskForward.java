/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.auton.tasks;

import edu.wpi.first.wpilibj.templates.auton.AutonTask;
import edu.wpi.first.wpilibj.templates.RobotActuators;

/**
 *
 * @author Nathan
 */
public class TaskForward extends AutonTask {

	private double _distance;

	public TaskForward(double feet) {
		_distance = feet;
	}

	public void setup() {

	}
	public void run() {
		RobotActuators.wheels.set(1);
	}
	public void cleanUp() {
		RobotActuators.wheels.set(0);
	}
}
