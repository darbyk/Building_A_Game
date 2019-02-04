package com.game.controllers;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.game.controllers.Game.STATE;
import com.game.gameObjects.creatures.Bullet;
import com.game.interfacesAndAbstracts.GameObject;
import com.game.interfacesAndAbstracts.ID;
import com.game.main.Handler;

public class MouseInput extends MouseAdapter{
	
	public Handler handler;
	private Camera camera;
	SpriteSheet bulletSpriteSheet;
	Game game;
	
	public MouseInput(Handler handler, Camera camera, SpriteSheet bulletSpriteSheet, Game game)
	{
		this.handler = handler;
		this.camera = camera;
		this.bulletSpriteSheet = bulletSpriteSheet;
		this.game = game;
	}
	
	public void mousePressed(MouseEvent e)
	{
		int mx = e.getX();
		int my = e.getY();
		GameObject thePlayer = null;
		
		if(game.getGameState() == STATE.Game)
		{
			for(int i = 0; i < handler.getObjects().size(); i++)
			{
				GameObject myObj = handler.getObjects().get(i);
				if(myObj != null && myObj.getId() == ID.Player)
					thePlayer = myObj;
			}
			new Bullet(mx, my, ID.Bullet, handler, thePlayer, camera, bulletSpriteSheet);
		}
	}
	
}
