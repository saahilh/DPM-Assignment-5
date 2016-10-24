package ev3Objects;

import ev3Odometer.Odometer;
import ev3WallFollower.UltrasonicController;
import ev3WallFollower.UltrasonicPoller;
import lejos.hardware.motor.EV3LargeRegulatedMotor;

public class Motors {
	
	private EV3LargeRegulatedMotor leftMotor;
	private EV3LargeRegulatedMotor rightMotor;
	private double wheelRadius;
	private double axleLength;
	
	
	public Motors (EV3LargeRegulatedMotor pLeftMotor, EV3LargeRegulatedMotor pRightMotor, double pWheelRadius, double pAxleLength)
	{
		leftMotor 					= pLeftMotor;
		rightMotor 					= pRightMotor;
		wheelRadius 				= pWheelRadius;
		axleLength 					= pAxleLength;
	}

	public EV3LargeRegulatedMotor getLeftMotor() {
		return leftMotor;
	}

	public EV3LargeRegulatedMotor getRightMotor() {
		return rightMotor;
	}

	public double getWheelRadius() {
		return wheelRadius;
	}

	public double getAxleLength() {
		return axleLength;
	}
	
	

}
