/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.auton;

import edu.wpi.first.wpilibj.templates.auton.tasks.TaskForward;
import edu.wpi.first.wpilibj.templates.auton.tasks.TaskTurn90;

/**
 *
 * @author Nathan
 */
public abstract class Auton {

	private static AutonList list = new AutonList();

	public static void setup() {
		list.add(new TaskForward(12));
		list.add(new TaskTurn90());
	}

	public static void run() {
		AutonTask task = list.peek();
		while (task != null && task.isDone()) {
			task.cleanUp();
			list.pop();
			if (list.length() == 0) {
				task = null;
				break;
			}
			task = list.peek();
		}
		if (task != null) {
			if (!task.isInit()) {
				task.setup();
				task.doInit();
			}
			task.run();
		}
	}
}
