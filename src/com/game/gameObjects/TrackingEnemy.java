package com.game.gameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.game.controllers.Animation;
import com.game.controllers.Game;
import com.game.controllers.SingleAnimation;
import com.game.interfacesAndAbstracts.GameObject;
import com.game.interfacesAndAbstracts.StaticCalculator;
import com.game.lootObjects.DropBundle;
import com.game.lootObjects.DropBundle.ITEM_LIST;
import com.game.lootObjects.DropItemInstruction;
import com.game.main.HUD;
import com.game.main.Handler;
import com.game.resources.ResourceLoader;
import com.game.resources.ResourceLoader.RESOURCE_TYPES;

public class TrackingEnemy extends GameObject{

	public static final int X = 64;
	public static final int Y = 64;
	
	private double baseVelocity;
	private final int maxHitPoints = 300;
	private int currentHitPoints = 300;
	private int hitPointsBarSize = 50;
	private boolean isAttacking = false;
	private boolean changeFromAttacking = false;
	
	private DropBundle enemyDropTable;
	
	private SingleAnimation greenImpAttack;
	
	public TrackingEnemy(int x, int y, ID id, Handler handler, ResourceLoader rl) {
		super(x, y, id, handler, rl.getSpriteSheetByType(RESOURCE_TYPES.WalkingGreenImp));
		
		Random r = new Random();
		baseVelocity = r.nextDouble() + 2;
		
		velX = 0;
		velY = 0;
		
		createDropBundle();
		
		charDisplay = ss.grabImage(1, 3, X, Y);
		
		BufferedImage[] greenImpAttack = {
				rl.getSpriteSheetByType(RESOURCE_TYPES.AttackingGreenImp).grabImage(1, 3, X, Y),
				rl.getSpriteSheetByType(RESOURCE_TYPES.AttackingGreenImp).grabImage(2, 3, X, Y),
				rl.getSpriteSheetByType(RESOURCE_TYPES.AttackingGreenImp).grabImage(3, 3, X, Y),
				rl.getSpriteSheetByType(RESOURCE_TYPES.AttackingGreenImp).grabImage(4, 3, X, Y)
		};
		this.greenImpAttack = new SingleAnimation(10, greenImpAttack);
	}

	public int tickHelper(int index) {
		x += velX;
		y += velY;
		
		
		
		return index;
	}
	
	public void collision()
	{
		if(isAttacking)
		{
			greenImpAttack.runAnimation();
			changeFromAttacking = !greenImpAttack.isRunningAnimation();
//			System.out.println("changeFromAttacking: " + changeFromAttacking);
		}
		
		for(int i = 0; i < handler.getObjects().size(); i++)
		{
			GameObject tempObject = handler.getObjects().get(i);
			if(tempObject.getId() == ID.Player)
			{
				if(!isAttacking && !place_free((x + velX), (y+velY), getBounds(), tempObject.getBounds()))
				{
					velX = 0;
					velY = 0;
					isAttacking = true;
				}
				else if(!isAttacking && tempObject.getBounds().intersects(getTrackingBounds()))
				{
					//Begin tracking player
					double angle = StaticCalculator.calculateAngleOfTwoPoints(tempObject.getX(), tempObject.getY(), this.getX(), this.getY());
					velX = -1 * StaticCalculator.calculateVelXByAngleAndBaseVelocity(baseVelocity, angle);
					velY = -1 * StaticCalculator.calculateVelYByAngleAndBaseVelocity(baseVelocity, angle);
					
				}
				else if(changeFromAttacking && tempObject.getBounds().intersects(getAttackingBounds()))
				{
					Player player = (Player) tempObject;
					player.health = (int) Game.clamp(player.health-25, 0, player.maxHealth);
//					System.out.println("Hit!");
				}
			}
			else if(tempObject.getId() == ID.Bullet && !place_free((x + velX), (y+velY), getBounds(), tempObject.getBounds()))
			{
				currentHitPoints -= 25;
				
				handler.removeObject(tempObject);
				if(currentHitPoints <= 0)
				{
					onDeath();
					i--;
				}
			}
		}
		
		for(int i = 0; i < handler.getObjects().size(); i++)
		{
			GameObject tempObject = handler.getObjects().get(i);
			if(tempObject.getId() == ID.Block)
			{
				if(!place_free((int) (x + velX), y, getBounds(), tempObject.getBounds()))
				{
					velX = 0;
				}
				if(!place_free(x, y+velY, getBounds(), tempObject.getBounds()))
				{
					velY = 0;
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

		//Loot tables should decide what loot to create in the world
		//Each enemy should have a 'Loot Table'
		//Generate Loot to Drop

		enemyDropTable.chooseItemToGenerate((int)x, (int)y, handler);
		
		//Loot created in the world should be managed via a separate gameObject array, but should probably still be gameObjects for if on the ground
		//Players should have an inventoryLoot variable
	}


	public void render(Graphics g) {
		
		if(!isAttacking)
		{
			g.drawImage(charDisplay, (int)x, (int)y, null);
		}
		else
		{
			greenImpAttack.drawAnimation(g, (int)x, (int)y);
			if(changeFromAttacking)
			{
				changeFromAttacking = false;
				isAttacking = false;
			}
		}
		
		if(currentHitPoints != 100)
		{
			g.setColor(Color.gray);
			g.fillRect((int)x +5, (int)y - 10, hitPointsBarSize, 10);
			g.setColor(Color.green);
			g.fillRect(5 + (int)x, -10 + (int)y, (int) (currentHitPoints/(double) (maxHitPoints) * hitPointsBarSize), 10);
			g.setColor(Color.white);
			g.drawRect(5 + (int) x, -10 + (int) y, hitPointsBarSize, 10);
		}
	}

	public Rectangle getBounds()
	{
		return new Rectangle((int)x, (int)y, BouncingEnemy.X, 39);
	}
	public Rectangle getTrackingBounds()
	{
		return new Rectangle((int)x - Game.WINDOW_WIDTH/2, (int)y - Game.WINDOW_HEIGHT/2, Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT);
	}
	public Rectangle getAttackingBounds()
	{
		return new Rectangle((int)x-20, (int)y-20, BouncingEnemy.X + 20, 39 + 20);
	}

}
