package com.game.interfacesAndAbstracts;

import java.awt.Color;
import java.awt.Graphics;

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
	
	public static void renderGraphicFloatingBar(Color backgroundColor, Color fillingColor, Color borderColor, 
			float startX, float startY, int deltaX, int deltaY, double ratioToFill, Graphics g)
	{
		g.setColor(backgroundColor);
		g.fillRect((int)startX, (int)startY, deltaX, deltaY);
		g.setColor(fillingColor);
		g.fillRect((int)startX, (int)startY, (int) (ratioToFill * deltaX), deltaY);
		g.setColor(borderColor);
		g.drawRect((int)startX, (int)startY, deltaX, deltaY);
	}
}
