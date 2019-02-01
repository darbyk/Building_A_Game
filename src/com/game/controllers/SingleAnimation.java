package com.game.controllers;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class SingleAnimation extends Animation {

	private int speed;
	private int frames;
	
	private int index = 0;
	private int count = 0;
	private boolean runningAnimation;
	
	private BufferedImage[] images;
	private BufferedImage currentImg;
	
	public SingleAnimation(int speed, BufferedImage... args)
	{
		super(speed, args);
		
		this.speed = speed;
		images = new BufferedImage[args.length];
		for(int i = 0; i < args.length; i++)
		{
			images[i] = args[i];
		}
		frames = args.length;
		
		runningAnimation = false;
	}
	
	public void runAnimation()
	{
		runningAnimation = true;
		index++;
		if(index > speed){
			index = 0;
			nextFrame();
		}
	}
	
	private void nextFrame()
	{
		currentImg = images[count];
		count++;
		if(count >= frames)
		{
			count = 0;
			runningAnimation = false;
		}
	}
	
	public boolean isRunningAnimation()
	{
		return runningAnimation;
	}
	
	public void drawAnimation(Graphics g, int x, int y)
	{
		g.drawImage(currentImg, x, y, null);
	}
	
	public void drawAnimation(Graphics g, int x, int y, int scaleX, int scaleY)
	{
		g.drawImage(currentImg, x, y, scaleX, scaleY, null);
	}
}
