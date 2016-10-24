package ev3ObjectDetector;


import java.util.Arrays;

import ev3Odometer.Odometer;
import ev3WallFollower.UltrasonicPoller;
import lejos.hardware.Sound;
import lejos.robotics.SampleProvider;

public class ObjectDetector{

	private SampleProvider colorValue;
	private Odometer odometer;
	private UltrasonicPoller ultraSonicPoller;
	public ObstacleAvoider obstacleAvoider;

	public enum OBJECT_TYPE { block, No_Block } 

	private float[] colorData;
	private final int FILTER_OUT = 5;
	private int filterControl;
	private final double defaultObstacleDistance = 20;
	private OBJECT_TYPE currentObject;
	private boolean objectDetected;

	private Object lock = new Object();

	public ObjectDetector(UltrasonicPoller pUltraSonicPoller, SampleProvider pColorValue, float[] pColorData, Odometer pOdometer, ObstacleAvoider pObstacleAvoider)
	{
		ultraSonicPoller  = pUltraSonicPoller;
		colorValue = pColorValue;
		colorData = pColorData;
		odometer = pOdometer;
		obstacleAvoider = pObstacleAvoider;
	}


	//This method checks for obstacles in front of the robot as it is moving forward
	public boolean detectedObject(int distance)
	{

		// rudimentary filter - checks 5 times to ensure obstacle is really ahead of robot
		if( ultraSonicPoller.getDistance() < distance)
		{
			synchronized(lock)
			{
				setObjectDetected(true);
			}
			return true;

		}
		
		synchronized(lock)
		{
			setObjectDetected(false);
			setCurrentObject(null);
		}
		return false;
	}

	public boolean detectedObject()
	{

		// rudimentary filter - checks 5 times to ensure obstacle is really ahead of robot
		if( ultraSonicPoller.getDistance() < defaultObstacleDistance)
		{
			synchronized(lock)
			{
				setObjectDetected(true);
			}
			return true;

		}
		
		synchronized(lock)
		{
			setObjectDetected(false);
			setCurrentObject(null);
		}
		return false;

	}

	public void processObject()
	{

		if(ultraSonicPoller.getDistance() <=4  && getCurrentObject() == null)
		{
			colorValue.fetchSample(colorData, 0);
			if(colorData[0]== 2){
				Sound.beep();
				setCurrentObject(OBJECT_TYPE.block);
			}
			else
			{
				Sound.beep();
				Sound.beep();
				setCurrentObject(OBJECT_TYPE.No_Block);
			}
		}
	}

	public boolean isObjectDetected()
	{
		boolean returnedValue;
		synchronized(lock)
		{
			returnedValue = objectDetected;
		}
		return returnedValue;
	}

	public void setObjectDetected(boolean objectDetected) {
		synchronized(lock)
		{
			this.objectDetected = objectDetected;
		}
	}		

	public void setCurrentObject(OBJECT_TYPE pObject)
	{
		synchronized(lock)
		{
			currentObject = pObject;
		}	
	}



	public double getDefaultObstacleDistance() {
		return defaultObstacleDistance;
	}


	public OBJECT_TYPE getCurrentObject() {
		OBJECT_TYPE returnedValue;
		synchronized(lock)
		{
			returnedValue = currentObject;
		}	
		return returnedValue;
	}

	public double getObjectDistance(){

		return ultraSonicPoller.getDistance();
	}


}
