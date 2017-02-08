package org.usfirst.frc.team1389.robot;

import java.util.concurrent.CompletableFuture;

import com.team1389.command_framework.CommandUtil;
import com.team1389.command_framework.command_base.Command;
import com.team1389.hardware.inputs.hardware.AnalogUltrasonicHardware;
import com.team1389.hardware.inputs.software.RangeIn;
import com.team1389.hardware.outputs.hardware.VictorHardware;
import com.team1389.hardware.registry.Registry;
import com.team1389.hardware.registry.port_types.Analog;
import com.team1389.hardware.registry.port_types.PWM;
import com.team1389.hardware.value_types.Position;
import com.team1389.motion_profile.MotionProfile;
import com.team1389.motion_profile.ProfileUtil;
import com.team1389.watch.Watcher;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;

public class Robot extends IterativeRobot {
	Watcher watcher = new Watcher();
	Registry registry = new Registry();

	// Add hardware here
	AnalogUltrasonicHardware ultra = new AnalogUltrasonicHardware(new Analog(3), registry);
	RangeIn<Position> ultraDistanceInCM = ultra.getDistanceIn();


	public final static long initialTimeMillis = System.currentTimeMillis();
	private static final int RADIUS = 3; //cm/s
	private static final int SEPERATION = 5;
	private Timer timer = new Timer();

	MotionProfile currentTrapezoid = ProfileUtil.trapezoidal(0, 0, 5, 5, 10);
	private double offset = 0;


	/**
	 * This function is run when the robot is first started up and should be used for any
	 * initialization code.
	 */
	@Override
	public void robotInit() {
		CompletableFuture.runAsync(Watcher::updateWatchers);
		CompletableFuture.runAsync(() -> CommandUtil.executeCommand(CommandUtil.createCommand(() ->{
			offset += currentTrapezoid.getPosition(timer.get());
			currentTrapezoid = ProfileUtil.trapezoidal(ultraDistanceInCM.get() - SEPERATION, currentTrapezoid.getVelocity(timer.get()),100,100,1000);
			return false;
		}), 500));

		watcher.watch(new RangeIn<Position>(Position.class, 
				() -> currentTrapezoid.getPosition(timer.get()) + offset,
						0, 2 * Math.PI * RADIUS).getWatchable("Position"));
				watcher.watch(ultraDistanceInCM.getWatchable("Ultra CM"));
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
