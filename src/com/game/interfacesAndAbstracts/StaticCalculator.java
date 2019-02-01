package com.game.interfacesAndAbstracts;

public class StaticCalculator {
	
	
	private StaticCalculator(){}
	
	public static double calculateAngleOfTwoPoints(float p1X, float p1Y, float p2X, float p2Y)
	{
		double angle = Math.atan2(p2Y - p1Y, p2X - p1X);
		return angle;
	}
	
	public static float calculateVelXByAngleAndBaseVelocity(double baseVelocity, double angle)
	{
		return (float) (baseVelocity * Math.cos(angle));
	}
	
	public static float calculateVelYByAngleAndBaseVelocity(double baseVelocity, double angle)
	{
		return (float) (baseVelocity * Math.sin(angle));
	}
}
