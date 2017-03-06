package org.usfirst.frc.team1389.robot;

import java.util.concurrent.CompletableFuture;

import com.team1389.hardware.inputs.hardware.PotentiometerHardware;
import com.team1389.hardware.inputs.hardware.QuadEncoderHardware;
import com.team1389.hardware.inputs.software.AngleIn;
import com.team1389.hardware.inputs.software.EncoderIn;
import com.team1389.hardware.inputs.software.RangeIn;
import com.team1389.hardware.outputs.hardware.CANTalonHardware;
import com.team1389.hardware.outputs.hardware.VictorHardware;
import com.team1389.hardware.registry.Registry;
import com.team1389.hardware.registry.port_types.Analog;
import com.team1389.hardware.registry.port_types.CAN;
import com.team1389.hardware.registry.port_types.DIO;
import com.team1389.hardware.registry.port_types.PWM;
import com.team1389.hardware.value_types.Position;
import com.team1389.hardware.value_types.Speed;
import com.team1389.hardware.value_types.Value;
import com.team1389.watch.Watcher;

import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {
	Watcher watcher = new Watcher();
	Registry registry = new Registry();
		
	AngleIn<Speed> potentSpeed;
	EncoderIn<Position> encoderPos;

	// Add hardware here
	VictorHardware victor = new VictorHardware(false, new PWM(0), registry);
	PotentiometerHardware potent = new PotentiometerHardware(new Analog(0), registry);
	CANTalonHardware talon = new CANTalonHardware(false, new CAN(0), registry);
	QuadEncoderHardware encoder = new QuadEncoderHardware(false, new DIO(10), new DIO(8), registry);
	
	/**
	 * This function is run when the robot is first started up and should be used for any
	 * initialization code.
	 */
	
	@Override
	public void robotInit() {
		watcher.watch(potent.getAnalogInput().getWatchable("Potentiometer"), talon.getVoltageOutput().getSettingWatchable("talon", 0));
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
		
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
}
