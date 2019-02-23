package com.game.main;

import java.awt.Color;
import java.awt.Graphics;

import com.game.controllers.Camera;
import com.game.controllers.Game;
import com.game.gameObjects.creatures.Player;
import com.game.interfacesAndAbstracts.StaticCalculator;

public class HUD {
	
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
		
		//Health Bar
		StaticCalculator.renderGraphicFloatingBar(Color.gray, Color.red, Color.white, 
				xOffset+9, yOffset+9, 100, 32, mainPlayer.health/(double)mainPlayer.maxHealth, g);
		
		//Mana Bar
		StaticCalculator.renderGraphicFloatingBar(Color.gray, Color.blue, Color.white, 
				510 + xOffset+9, yOffset+9, 100, 32, mainPlayer.mana/(double)mainPlayer.maxMana, g);
		
		StaticCalculator.renderGraphicFloatingBar(Color.DARK_GRAY, Color.green, Color.white, 
				xOffset, yOffset-3, Game.WINDOW_WIDTH, 6, mainPlayer.experience/(double)mainPlayer.experiencePerLevel, g);
		
		
	}
}
