package org.usfirst.frc.team1389.robot;

import com.team1389.hardware.inputs.hardware.JoystickHardware;
import com.team1389.hardware.inputs.software.DigitalIn;
import com.team1389.hardware.outputs.hardware.VictorHardware;
import com.team1389.hardware.registry.Registry;
import com.team1389.hardware.registry.port_types.PWM;
import com.team1389.system.SystemManager;
import com.team1389.watch.Watcher;

import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {
	Watcher watcher = new Watcher();
	Registry registry = new Registry();

	// Add hardware here
	VictorHardware victor = new VictorHardware(false, new PWM(0), registry);
	DigitalIn switchingCamerasButton = new JoystickHardware(0).getButton(2).latched();
	SwitchingCameras switchingCameras = new SwitchingCameras(switchingCamerasButton);
	SystemManager manager = new SystemManager(switchingCameras);

	/**
	 * This function is run when the robot is first started up and should be used for any
	 * initialization code.
	 */

	@Override
	public void robotInit() {
		manager.init();
		watcher.watch(switchingCameras);
		watcher.outputToDashboard();
	}

	@Override
	public void autonomousInit() {

	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {

	}

	/**
	 * This function is called periodically during operator control
	 */

	@Override
	public void teleopPeriodic() {
		Watcher.update();
		switchingCameras.update();
		manager.update();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {

	}
}