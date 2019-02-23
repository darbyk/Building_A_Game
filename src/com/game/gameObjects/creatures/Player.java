package com.game.gameObjects.creatures;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.game.controllers.Animation;
import com.game.controllers.Camera;
import com.game.controllers.Game;
import com.game.controllers.SpriteSheet;
import com.game.gameObjects.lootObjects.DropItemInstruction;
import com.game.gameObjects.lootObjects.Loot;
import com.game.interfacesAndAbstracts.GameObject;
import com.game.interfacesAndAbstracts.ID;
import com.game.main.Handler;

public class Player extends GameObject{

	public static final int X = 32;
	public static final int Y = 36;
	
	private Animation playerSouthWalk;
	private Animation playerNorthWalk;
	private Animation playerEastWalk;
	private Animation playerWestWalk;
	
	
	public int manaRegenRatePerSecond = 5;
	public int healthRegenRatePerSecond = 1;
	public int manaTickTracker = 0;
	public int healthTickTracker = 0;
	
	public int maxHealth;
	public int health;
	public int maxMana;
	public int mana;
	public int experience;
	public int experiencePerLevel;
	
	private ArrayList<DropItemInstruction> playerLoot;
	
	public Player(int x, int y, ID id, Handler handler, SpriteSheet ss) {
		super(x, y, id, handler, ss);
		charDisplay = ss.grabImage(1, 3, X, Y);
		playerLoot = new ArrayList<DropItemInstruction>();
		dir = DIRECTION.SOUTH;
		
		maxHealth = 100;
		health = maxHealth;
		experience = 0;
		experiencePerLevel = 100;
		maxMana = 100;
		mana = maxMana;
		
		BufferedImage[] imagesSouthWalk = {
				ss.grabImage(1, 3, X, Y),
				ss.grabImage(2, 3, X, Y),
				ss.grabImage(3, 3, X, Y)
		};
		BufferedImage[] imagesNorthWalk = {
				ss.grabImage(1, 1, X, Y),
				ss.grabImage(2, 1, X, Y),
				ss.grabImage(3, 1, X, Y)
		};
		BufferedImage[] imagesEastWalk = {
				ss.grabImage(1, 2, X, Y),
				ss.grabImage(2, 2, X, Y),
				ss.grabImage(3, 2, X, Y)
		};
		BufferedImage[] imagesWestWalk = {
				ss.grabImage(1, 4, X, Y),
				ss.grabImage(2, 4, X, Y),
				ss.grabImage(3, 4, X, Y)
		};
		
		
		playerSouthWalk = new Animation(10, imagesSouthWalk);
		playerNorthWalk = new Animation(10, imagesNorthWalk);
		playerEastWalk = new Animation(10, imagesEastWalk);
		playerWestWalk = new Animation(10, imagesWestWalk);
	}

	public int tickHelper(int index) 
	{
		
		//Calculating next position?
		x = Game.clamp(x+velX, 0, (int) (Game.GAME_WIDTH - 1.1*Player.X));
		y = Game.clamp(y + velY, 0, (int) (Game.GAME_HEIGHT - 1.1*Player.Y));
		
		//Seems redundant to run all animations? Should we choose the one to run? 
		playerSouthWalk.runAnimation();
		playerNorthWalk.runAnimation();
		playerEastWalk.runAnimation();
		playerWestWalk.runAnimation();
		
		//Start regen'ing
		runRegeneration();		
		
		return index;
	}
	
	private void runRegeneration()
	{
		if(healthTickTracker >= 60)
		{
			healthTickTracker = 0;
			health = (int) Game.clamp(health + healthRegenRatePerSecond, 0, maxHealth);
		}
		else
		{
			healthTickTracker++;
		}
		
		if(manaTickTracker >= 60)
		{
			manaTickTracker = 0;
			mana = (int) Game.clamp(mana + manaRegenRatePerSecond, 0, maxMana);
		}
		else
		{
			manaTickTracker++;
		}
	}

	public void render(Graphics g) {
//		g.setColor(Color.white);
//		g.fillRect((int)x, (int)y, Player.X, Player.Y);
		if(velX != 0 || velY != 0)
		{
			if(DIRECTION.NORTH == dir)
				playerNorthWalk.drawAnimation(g, (int)x, (int)y);
			else if(DIRECTION.EAST == dir)
				playerEastWalk.drawAnimation(g, (int)x, (int)y);
			else if(DIRECTION.SOUTH == dir)
				playerSouthWalk.drawAnimation(g, (int)x, (int)y);
			else if(DIRECTION.WEST == dir)
				playerWestWalk.drawAnimation(g, (int)x, (int)y);
		}
		else
			g.drawImage(charDisplay, (int)x, (int)y, null);
		
	}
	
	public void addExperience(int experienceToAdd)
	{
		experience += experienceToAdd;
		System.out.println("Current player exp: " + experience + " exp per level: " + experiencePerLevel);
		if(experience >= experiencePerLevel)
		{
			experience -= experiencePerLevel;
			experiencePerLevel += 25;
		}
	}
	public void addLoot(DropItemInstruction item)
	{
		boolean hasMerged = false;
		for(int i = 0; i < playerLoot.size(); i++)
		{
			if(playerLoot.get(i).getItemName() == item.getItemName())
			{
				DropItemInstruction foundItem = playerLoot.get(i);
				foundItem.increaseStackedAmount(item.getStackedAmount());
				hasMerged = true;
				break;
			}
			
		}
		if(!hasMerged)
			playerLoot.add(item);
		
	}
	
	public void collision(){
		for (int i = 0; i < handler.getObjects().size(); i++) {
			GameObject tempObject = handler.getObjects().get(i);
			
			if (tempObject.getId() == ID.Block) {
				if (!place_free((x + velX), y, getBounds(), tempObject.getBounds())) {
					x = x - velX;
				}
				
				if (!place_free(x, (y + velY), getBounds(), tempObject.getBounds())) {
					y = y - velY;
				}
			}
			if(tempObject.getId() == ID.BouncingEnemy)
			{
				//if basic we're at a basic enemy
				if(!place_free((int) (x + velX), (int) (y + velY),  getBounds(), tempObject.getBounds()))
				{
					//collision code for if player intersects with basic enemy
					health = (int) Game.clamp(health-1, 0, maxHealth);
				}
			}
			if(tempObject.getId() == ID.Item)
			{
				if(!place_free((int) (x + velX), (int) (y + velY),  getBounds(), tempObject.getBounds()))
				{
					Loot l = (Loot)tempObject;
					DropItemInstruction item = l.convertObjectToInstruction();
					addLoot(item);
					printCurrentLoot();
					handler.removeObject(tempObject);
					i--;
				}
			}
		}
	}
	
	
	public void shootMissile(int mx, int my, Handler handler, Camera camera, SpriteSheet bss)
	{
		int manaCostOfMissle = 25;
		if(mana >= manaCostOfMissle)
		{
			new Bullet(mx, my, ID.Bullet, handler, this, camera, bss);
			mana = (int) Game.clamp(mana -25, 0, maxMana);
		}
		else
			System.out.println("You're out of mana!");
	}
	
	
	public Rectangle getBounds()
	{
		return new Rectangle((int)x, (int)y, Player.X, Player.Y);
	}
	
	public void printCurrentLoot()
	{
		for(int i = 0; i < playerLoot.size(); i++)
		{
			System.out.println(playerLoot.get(i).toString());
		}
		System.out.println("----------------------");
	}
	
}
