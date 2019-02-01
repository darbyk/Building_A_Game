package com.game.main;

import java.awt.Color;
import java.awt.Graphics;

import com.game.controllers.Camera;
import com.game.controllers.Game;
import com.game.gameObjects.Player;

public class HUD {
//	public static final int MAX_HEALTH = 100;
//	public static int HEALTH = MAX_HEALTH;
	
	public static final int HELPER_BAR_WIDTH = Game.WINDOW_WIDTH;
	public static final int HELPER_BAR_HEIGHT = 50;
	
	private Camera camera;
	Player mainPlayer;
	
	public HUD(Camera camera, Player mainPlayer)
	{
		this.camera = camera;
		this.mainPlayer = mainPlayer;
		
	}
	
	public void tick(){
	}
	
	public void render(Graphics g)
	{
		int yOffset = (int) camera.getY() + Game.WINDOW_HEIGHT - HELPER_BAR_HEIGHT - 29;
		int xOffset =(int) camera.getX(); 
		
		g.setColor(Color.gray);
		g.fillRect(xOffset, yOffset, HELPER_BAR_WIDTH, HELPER_BAR_HEIGHT);
		
		g.setColor(Color.red);
		g.fillRect(9 + xOffset, 9 + yOffset, mainPlayer.health, 32);
		g.setColor(Color.white);
		g.drawRect(9 + xOffset, 9 + yOffset, mainPlayer.maxHealth, 32);
		
		g.setColor(Color.red);
		g.fillRect(9 + xOffset, 9 + yOffset, mainPlayer.health, 32);
		g.setColor(Color.white);
		g.drawRect(9 + xOffset, 9 + yOffset, mainPlayer.maxHealth, 32);
		
		
	}
}
