package com.game.controllers;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.game.gameObjects.Bullet;
import com.game.gameObjects.ID;
import com.game.interfacesAndAbstracts.GameObject;
import com.game.interfacesAndAbstracts.GameObject.DIRECTION;
import com.game.main.Handler;

public class KeyInput extends KeyAdapter{

	public Handler handler;
	private Camera camera;
	
	private boolean up = false;
	private boolean down = false;
	private boolean left = false;
	private boolean right = false;
	
	private int INTERNAL_VELOCITY = 4;
	
	public KeyInput(Handler handler, Camera camera)
	{
		this.handler = handler;
	}
	
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_ESCAPE)
			System.exit(1);
		
		for(int i = 0; i < handler.getObjects().size(); i++)
		{
			GameObject tempObject = handler.getObjects().get(i);
			if(tempObject.getId() == ID.Player)
			{
				if(key == KeyEvent.VK_UP) {
					tempObject.setVelY(-1 * INTERNAL_VELOCITY); 
					up = true;
				}
				if(key == KeyEvent.VK_DOWN) {
					tempObject.setVelY(INTERNAL_VELOCITY); 
					down = true;
				}
				if(key == KeyEvent.VK_LEFT) {
					tempObject.setVelX(-1 * INTERNAL_VELOCITY);
					left = true;
				}
				if(key == KeyEvent.VK_RIGHT) {
					tempObject.setVelX(INTERNAL_VELOCITY);
					right = true;
				}
			}
			tempObject.setDirection(calculateDirection(tempObject));
		}
	}
	
	public void keyReleased(KeyEvent e)
	{
		int key = e.getKeyCode();
		for(int i = 0; i < handler.getObjects().size(); i++)
		{
			GameObject tempObject = handler.getObjects().get(i);
			if(tempObject.getId() == ID.Player)
			{
				if(key == KeyEvent.VK_UP) 
				{
					tempObject.setVelY(0);
					up = false;
					if(down == true){
						tempObject.setVelY(INTERNAL_VELOCITY);
					}
					else{
						tempObject.setVelY(0);
					}
						
				}
				if(key == KeyEvent.VK_DOWN)
				{
					tempObject.setVelY(0);
					down = false;
					if(up == true){
						tempObject.setVelY(-1 * INTERNAL_VELOCITY);
					}
					else{
						tempObject.setVelY(0);
					}
				}
				if(key == KeyEvent.VK_LEFT) 
				{
					tempObject.setVelX(0);
					left = false;
					if(right == true){
						tempObject.setVelX(INTERNAL_VELOCITY);
					}
					else{
						tempObject.setVelX(0);
					}
				}
				if(key == KeyEvent.VK_RIGHT) 
				{
					tempObject.setVelX(0);
					right = false;
					if(left == true){
						tempObject.setVelX(-1 * INTERNAL_VELOCITY);
					}
					else
						tempObject.setVelX(0);
				}
			}
			tempObject.setDirection(calculateDirection(tempObject));
		}
	}
	
	private DIRECTION calculateDirection(GameObject go)
	{
		if(go.getVelX() > 0)
			return DIRECTION.EAST;
		else if (go.getVelX() < 0)
			return DIRECTION.WEST;
		else if (go.getVelY() > 0)
			return DIRECTION.SOUTH;
		else if(go.getVelY() < 0)
			return DIRECTION.NORTH;
		return go.getDirection();
	}
}
