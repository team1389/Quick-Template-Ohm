package org.usfirst.frc.team1389.robot;

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

	PercentOut voltageOut = talon.getVoltageOutput();

	/**
	 * This function is run when the robot is first started up and should be used for any
	 * initialization code.
	 */
	@Override
	public void robotInit() {
		watcher.watch(voltageOut.getSettingWatchable("voltageOut", 0.0),
				talon.getPositionInput().getWatchable("position"));
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
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
}
