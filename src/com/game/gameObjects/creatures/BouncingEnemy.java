package com.game.gameObjects.creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import com.game.controllers.SpriteSheet;
import com.game.gameObjects.lootObjects.DropBundle;
import com.game.gameObjects.lootObjects.DropItemInstruction;
import com.game.gameObjects.lootObjects.DropBundle.ITEM_LIST;
import com.game.interfacesAndAbstracts.GameObject;
import com.game.interfacesAndAbstracts.ID;
import com.game.main.Handler;

public class BouncingEnemy extends Enemy{

	public static final int X = 64;
	public static final int Y = 64;
	
	public BouncingEnemy(int x, int y, ID id, Handler handler, SpriteSheet ss, int maxHitpoints) {
		super(x, y, id, handler, ss, maxHitpoints);
		createDropBundle();
	}

	public void collision()
	{
		
		
		for(int i = 0; i < handler.getObjects().size(); i++)
		{
			GameObject tempObject = handler.getObjects().get(i);
			if(tempObject.getId() == ID.Bullet && !place_free((x + velX), (y+velY), getBounds(), tempObject.getBounds()))
			{
				if(getHitPoints() == getMaxHitPoints())
				{
					Bullet b = (Bullet) tempObject;
					this.setTagger(b.firer);
				}
				setHitPoints(getHitPoints() - 25);
				
				handler.removeObject(tempObject);
				if(getHitPoints() <= 0)
				{
					onDeath();
					i--;
				}
			}
			else if(tempObject.getId() == ID.Block)
			{
				if(!place_free((int) (x + velX), y, getBounds(), tempObject.getBounds()))
				{
					setVelX(velX * -1);
				}
				else if(!place_free(x, y+velY, getBounds(), tempObject.getBounds()))
				{
					velY *= -1;
				}
			}
		}
	}
	
	private void createDropBundle()	{
		DropItemInstruction dii1 = new DropItemInstruction(ITEM_LIST.GOLD_COIN, .01, 1, 1);
		
		DropItemInstruction dii2 = new DropItemInstruction(ITEM_LIST.SILVER_COIN, .5, 1, 1);
		DropItemInstruction dii3 = new DropItemInstruction(ITEM_LIST.COPPER_COIN, .5, 1, 5);
		DropBundle b1 = new DropBundle(ITEM_LIST.BUNDLE, .99, 1, 1);
		DropBundle finalBundle = new DropBundle();
		
		b1.add(dii2);
		b1.add(dii3);
		
		finalBundle.add(dii1);
		finalBundle.add(b1);
		
		this.setDropTable(finalBundle);
	}
	
}
