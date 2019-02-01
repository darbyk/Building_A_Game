package com.game.lootObjects;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.game.controllers.SpriteSheet;
import com.game.gameObjects.Bullet;
import com.game.gameObjects.ID;
import com.game.interfacesAndAbstracts.Droppable;
import com.game.interfacesAndAbstracts.GameObject;
import com.game.main.Handler;

public class GoldCoin extends GameObject{

	public final static int X = 16;
	public final static int Y = 16;
	
	public GoldCoin(int x, int y, ID id, Handler handler, SpriteSheet ss)
	{
		super(x, y, id, handler, ss);
		charDisplay = ss.grabImage(1, 1, X, Y);
	}
	
//	public GoldCoin()
//	{
//		
//	}

	public void render(Graphics g) {
		g.drawImage(charDisplay, (int)x, (int)y, null);
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, GoldCoin.X, GoldCoin.Y);
	}

	public void collision() {
		
	}

	public int tickHelper(int index) {
		return index;
	}

}