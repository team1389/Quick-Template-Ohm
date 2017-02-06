package org.usfirst.frc.team1389.robot;

import java.util.concurrent.CompletableFuture;

import com.team1389.hardware.inputs.hardware.JoystickHardware;
import com.team1389.hardware.inputs.hardware.SwitchHardware;
import com.team1389.hardware.inputs.software.DigitalIn;
import com.team1389.hardware.outputs.hardware.DoubleSolenoidHardware;
import com.team1389.hardware.outputs.hardware.VictorHardware;
import com.team1389.hardware.outputs.software.DigitalOut;
import com.team1389.hardware.registry.Registry;
import com.team1389.hardware.registry.port_types.DIO;
import com.team1389.hardware.registry.port_types.PCM;
import com.team1389.hardware.registry.port_types.PWM;
import com.team1389.watch.Watcher;

import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {
	Watcher watcher = new Watcher();
	Registry registry = new Registry();

	// Add hardware here
	VictorHardware victor = new VictorHardware(false, new PWM(0), registry);
	DigitalOut piston = new DoubleSolenoidHardware(new PCM(4), new PCM(5), registry).getDigitalOut();// registry).getDigitalOut();
	DigitalIn hallEffect = new SwitchHardware(new DIO(1), registry).getSwitchInput();

	DigitalIn button = new JoystickHardware(0).getButton(2).getToggled();

	/**
	 * This function is run when the robot is first started up and should be used for any
	 * initialization code.
	 */
	@Override
	public void robotInit() {
		watcher.setLogLocation(Watcher.DEFAULT_LOG); // will overwrite the log file every time the
														// code reboots
		watcher.outputToDashboard();
		CompletableFuture.runAsync(Watcher::updateWatchers);
		watcher.watch(hallEffect.getWatchable("hallEffect"));
		watcher.watch(button.getWatchable("button"));
		watcher.watch(piston.getWatchable("piston val"));
		CompletableFuture.runAsync(Watcher::updateWatchers);
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
		piston.set(button.get());
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}

}
