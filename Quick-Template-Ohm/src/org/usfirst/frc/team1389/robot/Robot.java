package org.usfirst.frc.team1389.robot;

import java.util.concurrent.CompletableFuture;

import com.team1389.configuration.PIDConstants;
import com.team1389.configuration.PIDInput;
import com.team1389.hardware.inputs.hardware.DashboardScalarInput;
import com.team1389.hardware.outputs.hardware.CANTalonHardware;
import com.team1389.hardware.outputs.software.RangeOut;
import com.team1389.hardware.registry.Registry;
import com.team1389.hardware.registry.port_types.CAN;
import com.team1389.hardware.value_types.Speed;
import com.team1389.watch.Watcher;

import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {
	Watcher watcher = new Watcher();
	Registry registry = new Registry();

	// Add hardware here
	CANTalonHardware talon = new CANTalonHardware(false, false, new CAN(2), registry);
	RangeOut<Speed> setSpeed;
	DashboardScalarInput getSpeed;

	/**
	 * This function is run when the robot is first started up and should be used for any
	 * initialization code.
	 */
	@Override
	public void robotInit() {
		watcher.setLogLocation(Watcher.DEFAULT_LOG); // will overwrite the log file every time the
														// code reboots
		PIDInput tuner = new PIDInput("SetConstants", new PIDConstants(2.0, 1.0e-5, 50, 0.4), true,
				pc -> setSpeed = talon.getSpeedOutput(pc));
		setSpeed = talon.getSpeedOutput(new PIDConstants(2.0, 1.0e-5, 50, 0.4));
		getSpeed = new DashboardScalarInput("Input Speed", Watcher.DASHBOARD, 1.0);
		
		watcher.watch(talon.getSpeedInput().getWatchable("Actual Speed"), tuner,
				talon.getVoltageInput().getWatchable("voltage"));
		
		watcher.outputToDashboard();
		CompletableFuture.runAsync(Watcher::updateWatchers);
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
		setSpeed.set(getSpeed.get());

	}

	@Override
	public void testInit() {
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
}
