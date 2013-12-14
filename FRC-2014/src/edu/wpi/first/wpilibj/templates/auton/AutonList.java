/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.auton;

/**
 *
 * @author Nathan
 */
public class AutonList {

	private AutonTask[] array = new AutonTask[10];
	private int end_index = 0;

	public int length() {
		return end_index;
	}

	public AutonList() {
	}

	public void add(AutonTask a) {
		if (end_index >= array.length) {
			AutonTask[] n = new AutonTask[array.length * 2];
			for (int i = 0; i < array.length; i++) {
				n[i] = array[i];
			}
			array = n;
		}
		array[end_index] = a;
		end_index++;
	}

	public AutonTask peek() {
		if (end_index > 0) {
			return array[end_index - 1];
		}
		return null;
	}

	public AutonTask pop() {
		if (end_index == 0) {
			return null;
		}
		AutonTask r = array[end_index];
		end_index--;
		return r;
	}
}
