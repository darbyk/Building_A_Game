package com.game.gameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.game.controllers.SpriteSheet;
import com.game.interfacesAndAbstracts.GameObject;
import com.game.interfacesAndAbstracts.ID;
import com.game.main.Handler;

public class Block extends GameObject{

	public Block(int x, int y, ID id, Handler handler, SpriteSheet ss) {
		super(x, y, id, handler, ss);
		charDisplay = ss.grabImage(1, 1, 32, 32);
	}

	public int tickHelper(int index) {
		//no tickHelper
		return index;
	}
	
	public void collision()
	{
		//no collision
	}

	public void render(Graphics g) {
//		g.setColor(Color.CYAN);
//		g.fillRect((int)x, (int)y, 32, 32);
		
		g.drawImage(charDisplay, (int)x, (int)y, null);
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x,(int)y,32,32);
	}

}
