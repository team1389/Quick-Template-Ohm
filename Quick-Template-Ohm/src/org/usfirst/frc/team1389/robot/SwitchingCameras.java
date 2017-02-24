package org.usfirst.frc.team1389.robot;

import com.team1389.hardware.inputs.software.DigitalIn;

import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;

public class SwitchingCameras{

	DigitalIn switchingCameraButton;
	MjpegServer switcher;
	UsbCamera camera0, camera1;
	UsbCamera CamAtMoment = camera0;

	public void init(){
		camera0 = CameraServer.getInstance().startAutomaticCapture("cam0", 0);
		camera1 = CameraServer.getInstance().startAutomaticCapture("cam1", 1);
		camera0.setResolution(640, 640);
		camera1.setResolution(640, 640);

	}
	
	public void update() {
			if(switchingCameraButton.get()){
				if(CamAtMoment == camera0){
					CamAtMoment = camera1;
					switcher.setSource(CamAtMoment);
				}
			}
			else{
				CamAtMoment = camera0;
				switcher.setSource(camera0);
			}
			
		}
	}

