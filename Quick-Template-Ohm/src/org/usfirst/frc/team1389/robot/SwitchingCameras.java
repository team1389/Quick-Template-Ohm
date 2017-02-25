package org.usfirst.frc.team1389.robot;

import com.team1389.hardware.inputs.software.DigitalIn;
import com.team1389.system.Subsystem;
import com.team1389.util.list.AddList;
import com.team1389.watch.Watchable;
import com.team1389.watch.info.StringInfo;

import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;

public class SwitchingCameras extends Subsystem {

	DigitalIn switchingCamerasButton;
	MjpegServer switcher;
	UsbCamera camera0;
	UsbCamera camera1;
	UsbCamera camAtMoment;

	public SwitchingCameras(DigitalIn button) {
		this.switchingCamerasButton = button;
	}

	public void init() {
		camera0 = new UsbCamera("camera 0", 0);
		camera1 = new UsbCamera("camera 1", 1);
		camera0.setResolution(640, 480);
		camera1.setResolution(640, 480);

		camAtMoment = camera0;
		switcher = new MjpegServer("myserver", 5800);
	}

	public void update() {
		if (switchingCamerasButton.get()) {
			camAtMoment = camAtMoment == camera0 ? camera1 : camera0;
			switcher.setSource(camAtMoment);
		}
	}

	@Override
	public AddList<Watchable> getSubWatchables(AddList<Watchable> stem) {
		return stem.put(new StringInfo("selected cam", () -> camAtMoment.getName()));
	}

	@Override
	public String getName() {
		return "Camera Manager";
	}
}
