/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.auton;

/**
 *
 * @author Nathan
 */
public abstract class AutonTask {

	public void setup() {
	}

	public abstract void run();
	private boolean _done = false;
	private boolean _init = false;

	public void end() {
		_done = true;
	}

	public final boolean isInit() {
		return _init;
	}

	public final void doInit() {
		_init = true;
	}

	public final boolean isDone() {
		return _done;
	}

	public void cleanUp() {
	}
}
