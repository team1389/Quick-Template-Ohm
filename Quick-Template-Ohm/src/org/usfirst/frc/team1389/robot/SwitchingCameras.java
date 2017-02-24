package org.usfirst.frc.team1389.robot;

import com.team1389.hardware.inputs.software.DigitalIn;

import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;

public class SwitchingCameras{

	DigitalIn switchingCamerasButton;
	MjpegServer switcher;
	UsbCamera camera0 = new UsbCamera("cam0", 0);
	UsbCamera camera1 = new UsbCamera("cam1", 1);
	UsbCamera CamAtMoment = camera0;

	public void init(){
		camera0 = CameraServer.getInstance().startAutomaticCapture();
		camera1 = CameraServer.getInstance().startAutomaticCapture();
		camera0.setResolution(640, 640);
		camera1.setResolution(640, 640);

	}
	
	public void update() {
		while(true){
			if(switchingCamerasButton.get()){
				if(CamAtMoment == camera0){
					//CamAtMoment = camera1;
					switcher.setSource(camera1);
				}
			}
			else{
				//CamAtMoment = camera0;
				switcher.setSource(camera0);
			}
			
		}
	}
}

