package com.game.gameObjects.lootObjects;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.game.controllers.SpriteSheet;
import com.game.gameObjects.creatures.Bullet;
import com.game.gameObjects.lootObjects.DropBundle.ITEM_LIST;
import com.game.interfacesAndAbstracts.Droppable;
import com.game.interfacesAndAbstracts.GameObject;
import com.game.interfacesAndAbstracts.ID;
import com.game.interfacesAndAbstracts.Lootable;
import com.game.main.Handler;

public class GoldCoin extends Loot implements Lootable{

	public final static int X = 16;
	public final static int Y = 16;
	
	public GoldCoin(int x, int y, ID id, Handler handler, SpriteSheet ss, int stackedAmount)
	{
		super(x, y, id, handler, ss, stackedAmount);
		setItemList(ITEM_LIST.GOLD_COIN);
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, GoldCoin.X, GoldCoin.Y);
	}
}
