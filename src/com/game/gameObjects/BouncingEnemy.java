package com.game.gameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import com.game.controllers.SpriteSheet;
import com.game.interfacesAndAbstracts.GameObject;
import com.game.lootObjects.DropBundle;
import com.game.lootObjects.DropBundle.ITEM_LIST;
import com.game.lootObjects.DropItemInstruction;
import com.game.main.Handler;

public class BouncingEnemy extends Enemy{

	public static final int X = 64;
	public static final int Y = 64;
	
	private int maxHitpoints = 100;
	private int hitPoints = maxHitpoints;
	private DropBundle enemyDropTable;
	private GameObject tagger;
	
	public BouncingEnemy(int x, int y, ID id, Handler handler, SpriteSheet ss) {
		super(x, y, id, handler, ss);
		
		Random r = new Random();
		velX = r.nextInt(3)+1;
		velY = r.nextInt(3)+1;
		
		createDropBundle();
		
//		DropItemInstruction dii1 = new DropItemInstruction(ITEM_LIST.GOLD_COIN, .2, 1, 1);
//		
//		DropItemInstruction dii2 = new DropItemInstruction(ITEM_LIST.SILVER_COIN, .5, 1, 1);
//		DropItemInstruction dii3 = new DropItemInstruction(ITEM_LIST.SILVER_COIN, .5, 1, 1);
//		DropBundle b1 = new DropBundle(ITEM_LIST.BUNDLE, .8, 1, 1);
//		DropBundle finalBundle = new DropBundle();
//		
//		b1.add(dii2);
//		b1.add(dii3);
//		
//		finalBundle.add(dii1);
//		finalBundle.add(b1);
		
		charDisplay = ss.grabImage(1, 3, X, Y);
	}

	public int tickHelper(int index) {
		x += velX;
		y += velY;
		
		return index;
	}
	
	public void collision()
	{
		
		
		for(int i = 0; i < handler.getObjects().size(); i++)
		{
			GameObject tempObject = handler.getObjects().get(i);
			if(tempObject.getId() == ID.Bullet && !place_free((x + velX), (y+velY), getBounds(), tempObject.getBounds()))
			{
				if(hitPoints == maxHitpoints)
				{
					tagger = tempObject;
				}
				hitPoints -= 25;
				
				handler.removeObject(tempObject);
				if(hitPoints <= 0)
				{
					onDeath();
					i--;
				}
			}
			else if(tempObject.getId() == ID.Block)
			{
				if(!place_free((int) (x + velX), y, getBounds(), tempObject.getBounds()))
				{
					velX *= -1;
				}
				else if(!place_free(x, y+velY, getBounds(), tempObject.getBounds()))
				{
					velY *= -1;
				}
			}
		}
	}
	
	private void createDropBundle()	{
		DropItemInstruction dii1 = new DropItemInstruction(ITEM_LIST.GOLD_COIN, .2, 1, 1);
		
		DropItemInstruction dii2 = new DropItemInstruction(ITEM_LIST.SILVER_COIN, .5, 1, 1);
		DropItemInstruction dii3 = new DropItemInstruction(ITEM_LIST.SILVER_COIN, .5, 1, 1);
		DropBundle b1 = new DropBundle(ITEM_LIST.BUNDLE, .8, 1, 1);
		DropBundle finalBundle = new DropBundle();
		
		b1.add(dii2);
		b1.add(dii3);
		
		finalBundle.add(dii1);
		finalBundle.add(b1);
		
		enemyDropTable = finalBundle;
	}
	
	public void onDeath()
	{
		//Dead = remove this guy
		handler.removeObject(this);
		
		if(tagger instanceof Player)
		{
			Player p = (Player) tagger;
			p.experience += 25;
		}

		//Loot tables should decide what loot to create in the world
		//Each enemy should have a 'Loot Table'
		//Generate Loot to Drop

		enemyDropTable.chooseItemToGenerate((int)x, (int)y, handler);
		
		//Loot created in the world should be managed via a separate gameObject array, but should probably still be gameObjects for if on the ground
		//Players should have an inventoryLoot variable
	}


	public void render(Graphics g) {
		
		g.drawImage(charDisplay, (int)x, (int)y, null);
		if(hitPoints != 100)
		{
			g.setColor(Color.gray);
			g.fillRect((int)x +5, (int)y - 10, 100/2, 10);
			g.setColor(Color.green);
			g.fillRect(5 + (int)x, -10 + (int)y, hitPoints/2, 10);
			g.setColor(Color.white);
			g.drawRect(5 + (int) x, -10 + (int) y, 100/2, 10);
		}
		
	}

	public Rectangle getBounds()
	{
		return new Rectangle((int)x, (int)y, BouncingEnemy.X, 39);
	}

}
