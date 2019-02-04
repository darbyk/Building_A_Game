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
import com.game.interfacesAndAbstracts.StaticCalculator;
import com.game.main.Handler;

public class Enemy extends GameObject{

	public static final int X = 64;
	public static final int Y = 64;
	
	private int maxHitPoints;
	private int hitPoints;
	private DropBundle dropTable;
	private GameObject tagger;
	private int lengthOfHealthBar;
	
	
	/* Constructors */
	public Enemy(int x, int y, ID id, Handler handler, SpriteSheet ss, int maxHitpoints) {
		super(x, y, id, handler, ss);
		
		Random r = new Random();
		this.setVelX(r.nextInt(3)+1);
		this.setVelY(r.nextInt(3)+1);
		
		this.maxHitPoints = maxHitpoints;
		this.hitPoints = maxHitpoints;
		
		charDisplay = ss.grabImage(1, 3, X, Y);
		createDropBundle();
		lengthOfHealthBar = 50;
	}
	
	
	
	/* Get and Setters */
	public void setTagger(GameObject tagger)
	{
		this.tagger = tagger;
	}
	public GameObject getTagger()
	{
		return tagger;
	}
	
	public void setDropTable(DropBundle enemyDropTable)
	{
		this.dropTable = enemyDropTable;
	}
	public DropBundle getDropTable()
	{
		return dropTable;
	}
	
	public void setMaxHitPoints(int newMaxHitPoints)
	{
		maxHitPoints = newMaxHitPoints;
	}
	public int getMaxHitPoints()
	{
		return maxHitPoints;
	}
	
	public void setHitPoints(int newHitPoints)
	{
		hitPoints = newHitPoints;
	}
	public int getHitPoints()
	{
		return hitPoints;
	}
	
	public int getLengthOfHealthBar()
	{
		return lengthOfHealthBar;
	}
	
	/* New Methods */
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
		
		dropTable = finalBundle;
	}
	
	public void onDeath()
	{
		//Dead = remove this guy
		handler.removeObject(this);
		
		if(tagger instanceof Player)
		{
			Player p = (Player) tagger;
			p.addExperience(25);
		}

		//Loot tables should decide what loot to create in the world
		//Each enemy should have a 'Loot Table'
		//Generate Loot to Drop

		dropTable.chooseItemToGenerate((int)x, (int)y, handler);
		
		//Loot created in the world should be managed via a separate gameObject array, but should probably still be gameObjects for if on the ground
		//Players should have an inventoryLoot variable
	}

	
	
	/* Overriden Methods */
	public int tickHelper(int index) {
		setX(x + velX);
		setY(y + velY);
		return index;
	}
	
	public void collision()
	{
		for(int i = 0; i < handler.getObjects().size(); i++)
		{
			GameObject tempObject = handler.getObjects().get(i);
			if(tempObject.getId() == ID.Bullet && !place_free((x + velX), (y+velY), getBounds(), tempObject.getBounds()))
			{
				if(hitPoints == maxHitPoints)
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
	
	public void render(Graphics g) {
		
		g.drawImage(charDisplay, (int)x, (int)y, null);
		if(hitPoints != maxHitPoints)
		{
			StaticCalculator.renderGraphicFloatingBar(Color.gray, Color.green, Color.white, 
					x+5, y-10, lengthOfHealthBar, 10, hitPoints/(double)maxHitPoints, g);
		}
		
	}

	public Rectangle getBounds()
	{
		return new Rectangle((int)x, (int)y, BouncingEnemy.X, 39);
	}

}
