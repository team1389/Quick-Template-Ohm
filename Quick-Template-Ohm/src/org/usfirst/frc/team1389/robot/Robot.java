package org.usfirst.frc.team1389.robot;

import com.team1389.configuration.PIDConstants;
import com.team1389.configuration.PIDInput;
import com.team1389.hardware.inputs.hardware.DashboardScalarInput;
import com.team1389.hardware.inputs.software.RangeIn;
import com.team1389.hardware.outputs.hardware.CANTalonHardware;
import com.team1389.hardware.outputs.hardware.VictorHardware;
import com.team1389.hardware.outputs.software.RangeOut;
import com.team1389.hardware.registry.Registry;
import com.team1389.hardware.registry.port_types.CAN;
import com.team1389.hardware.registry.port_types.PWM;
import com.team1389.hardware.value_types.Percent;
import com.team1389.hardware.value_types.Speed;
import com.team1389.watch.Watcher;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	Watcher watcher = new Watcher();
	Registry registry = new Registry();
	
	//Add hardware here
	CANTalonHardware talon = new CANTalonHardware(false, false, new CAN(1), registry);
	RangeOut<Speed> setSpeed;
	DashboardScalarInput getSpeed;	
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		watcher.setLogLocation(Watcher.DEFAULT_LOG); //will overwrite the log file every time the code reboots
		//PIDInput tuner = new PIDInput("SetConstants", new PIDConstants(0,0,0, 0.341), true, pc -> talon.getSpeedOutput(pc).s);
		setSpeed = talon.getSpeedOutput(new PIDConstants(0,0,0, 0.341));
		getSpeed = new DashboardScalarInput("Input Speed", watcher.DASHBOARD, 1.0);
		watcher.watch(talon.getSpeedInput().getWatchable("Actual Speed"));
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
		watcher.publish(Watcher.DASHBOARD);
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
