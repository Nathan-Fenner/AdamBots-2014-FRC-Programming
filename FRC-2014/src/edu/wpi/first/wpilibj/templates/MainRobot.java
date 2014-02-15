/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the IterativeRobot documentation. If you change the name of this class
 * or the package after creating this project, you must also update the manifest file in the
 * resource directory.
 */
public class MainRobot extends IterativeRobot {

	/**
	 * This function is run when the robot is first started up and should be used for any
	 * initialization code.
	 */
	public void robotInit() {
		RobotActuators.initialize();
		RobotSensors.initialize();
		RobotDrive.initialize();
		RobotPickup.initialize();
		RobotShoot.initialize();
		RobotVision.initialize();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		//runCompressor();
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		//RobotPickup.update();
		//runCompressor();
//      RobotDrive.update();
//      RobotDrive.driveStraight(0.5);
		//RobotDrive.update();
//      RobotDrive.test();
		//RobotDrive.joystickDrive(); //HOLD X -> HIGH GEAR, HOLD Y -> STOP
		//runCompressor();
        RobotDrive.update();
//        RobotDrive.driveStraight(0.5);
		//RobotShoot.update();
		//RobotShoot.automatedShoot();
		//RobotShoot.manualShoot();
		//System.out.println("Shooter Encoder: " + RobotSensors.shooterWinchEncoder.get());
		//RobotPickUp.update();
		//RobotPickUp.test(true, false, false);

		//RobotPickUp.update();
		//RobotPickUp.test(false, false, true);
		//RobotTeleop.update();
		//RobotDrive.update();
		RobotTeleop.update();
		RobotPickup.update();
		//RobotShoot.manualWind(FancyJoystick.primary.getRawButton(FancyJoystick.BUTTON_A), FancyJoystick.primary.getRawButton(FancyJoystick.BUTTON_B));

		runCompressor();


	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
	}

	private void runCompressor() {
		SmartDashboard.putBoolean("Pressure Switch", RobotSensors.pressureSwitch.get());
		if (!RobotSensors.pressureSwitch.get()) {
			RobotActuators.compressor.set(Relay.Value.kOn);
			System.out.println("Setting the compressor to ON");
		} else {
			RobotActuators.compressor.set(Relay.Value.kOff);
		}
		System.out.println("runCompressor finished");
	}
}
