package org.usfirst.frc.team1389.robot;

import java.util.concurrent.CompletableFuture;

import com.team1389.hardware.inputs.hardware.DashboardScalarInput;
import com.team1389.hardware.outputs.hardware.CANTalonHardware;
import com.team1389.hardware.outputs.software.PercentOut;
import com.team1389.hardware.registry.Registry;
import com.team1389.hardware.registry.port_types.CAN;
import com.team1389.watch.Watcher;

import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {
	Watcher watcher = new Watcher();
	Registry registry = new Registry();

	CANTalonHardware talon = new CANTalonHardware(true, new CAN(9), registry);
	//CANTalonHardware talon2 = new CANTalonHardware(true, new CAN(5), registry);

	DashboardScalarInput voltage = new DashboardScalarInput("voltage", Watcher.DASHBOARD, 0.0);
	PercentOut voltageOut = talon.getVoltageOutput()/*.addFollowers(talon2.getVoltageOutput())*/;

	/**
	 * This function is run when the robot is first started up and should be used for any
	 * initialization code.
	 */
	@Override
	public void robotInit() {
		CompletableFuture.runAsync(Watcher::updateWatchers);
		watcher.watch(voltageOut.getWatchable("voltageOut"), talon.getPositionInput().getWatchable("position"));
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
		voltageOut.set(voltage.get());
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
}
