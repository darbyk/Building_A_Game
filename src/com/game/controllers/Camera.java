package com.game.controllers;

import java.awt.Graphics;

import com.game.interfacesAndAbstracts.GameObject;
import com.game.main.HUD;

public class Camera {

	private float x, y;
	
	public Camera(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void tick(GameObject object) 
	{
		x += ((object.getX() - x) - Game.WINDOW_WIDTH/2.0) * 0.25f;
		y += ((object.getY() - y) - Game.WINDOW_HEIGHT/2.0) * 0.25f;
		
//		System.out.println("camera x: " + x + ", player x: " + object.getX());
		
		x = Game.clamp((int)x, 0, Game.GAME_WIDTH - Game.WINDOW_WIDTH);
		y = Game.clamp((int)y, 0, Game.GAME_HEIGHT - Game.WINDOW_HEIGHT + 28 + HUD.HELPER_BAR_HEIGHT);
		
//		if(x <= 0) x = 0;
//		if(x >= Game.WINDOW_WIDTH) x = Game.WINDOW_WIDTH;
//		if(y <= 0) y = 0;
//		if(y >= Game.WINDOW_HEIGHT) x = Game.WINDOW_HEIGHT;
	}
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void render(Graphics g)
	{
	}
}
