package com.game.gameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import com.game.controllers.SpriteSheet;
import com.game.interfacesAndAbstracts.GameObject;
import com.game.interfacesAndAbstracts.StaticCalculator;
import com.game.lootObjects.DropBundle;
import com.game.lootObjects.DropBundle.ITEM_LIST;
import com.game.lootObjects.DropItemInstruction;
import com.game.main.Handler;

public class Enemy extends GameObject{

	public static final int X = 64;
	public static final int Y = 64;
	
	private int maxHitpoints = 100;
	private int hitPoints = maxHitpoints;
	private DropBundle enemyDropTable;
	private GameObject tagger;
	
	public Enemy(int x, int y, ID id, Handler handler, SpriteSheet ss) {
		super(x, y, id, handler, ss);
		
		Random r = new Random();
		velX = r.nextInt(3)+1;
		velY = r.nextInt(3)+1;
		
		charDisplay = ss.grabImage(1, 3, X, Y);
		createDropBundle();
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
					onDeath(tagger);
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
	
	public void onDeath(GameObject go)
	{
		//Dead = remove this guy
		handler.removeObject(this);
		
		if(go instanceof Player)
		{
			Player p = (Player) go;
			p.addExperience(25);
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
		int lengthOfHealthBar = 50;
		if(hitPoints != maxHitpoints)
		{
			StaticCalculator.renderGraphicFloatingBar(Color.gray, Color.green, Color.white, 
					x+5, y-10, lengthOfHealthBar, 10, hitPoints/(double)maxHitpoints, g);
		}
		
	}

	public Rectangle getBounds()
	{
		return new Rectangle((int)x, (int)y, BouncingEnemy.X, 39);
	}

}
