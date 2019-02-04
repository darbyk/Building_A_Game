package com.game.gameObjects.creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.game.controllers.Animation;
import com.game.controllers.Game;
import com.game.controllers.SingleAnimation;
import com.game.gameObjects.lootObjects.DropBundle;
import com.game.gameObjects.lootObjects.DropItemInstruction;
import com.game.gameObjects.lootObjects.DropBundle.ITEM_LIST;
import com.game.interfacesAndAbstracts.GameObject;
import com.game.interfacesAndAbstracts.ID;
import com.game.interfacesAndAbstracts.StaticCalculator;
import com.game.main.HUD;
import com.game.main.Handler;
import com.game.resources.ResourceLoader;
import com.game.resources.ResourceLoader.RESOURCE_TYPES;

public class TrackingEnemy extends Enemy{

	public static final int X = 64;
	public static final int Y = 64;
	
	private double baseVelocity;
	private boolean isAttacking = false;
	private boolean changeFromAttacking = false;
	
	private SingleAnimation greenImpAttack;
	
	public TrackingEnemy(int x, int y, ID id, Handler handler, ResourceLoader rl, int maxHitPoints) {
		super(x, y, id, handler, rl.getSpriteSheetByType(RESOURCE_TYPES.WalkingGreenImp), maxHitPoints);
		
		Random r = new Random();
		baseVelocity = r.nextDouble() + 2;
		
		setVelX(0);
		setVelY(0);
		
		createDropBundle();
		
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
				}
			}
			else if(tempObject.getId() == ID.Bullet && !place_free((x + velX), (y+velY), getBounds(), tempObject.getBounds()))
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
		DropItemInstruction dii1 = new DropItemInstruction(ITEM_LIST.GOLD_COIN, .05, 1, 1);
		
		DropItemInstruction dii2 = new DropItemInstruction(ITEM_LIST.SILVER_COIN, .5, 2, 2);
		DropItemInstruction dii3 = new DropItemInstruction(ITEM_LIST.COPPER_COIN, .5, 10, 14);
		DropBundle b1 = new DropBundle(ITEM_LIST.BUNDLE, .95, 1, 1);
		DropBundle finalBundle = new DropBundle();
		
		b1.add(dii2);
		b1.add(dii3);
		
		finalBundle.add(dii1);
		finalBundle.add(b1);
		
		this.setDropTable(finalBundle);;
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
		
		if(getHitPoints() != 100)
		{
			StaticCalculator.renderGraphicFloatingBar(Color.gray, Color.green, Color.white, 
					x+5, y-10, getLengthOfHealthBar(), 10, getHitPoints()/(double)getMaxHitPoints(), g);
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
