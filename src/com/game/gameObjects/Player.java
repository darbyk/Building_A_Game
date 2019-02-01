package com.game.gameObjects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.game.controllers.Animation;
import com.game.controllers.Game;
import com.game.controllers.SpriteSheet;
import com.game.interfacesAndAbstracts.GameObject;
import com.game.main.HUD;
import com.game.main.Handler;

public class Player extends GameObject{

	public static final int X = 32;
	public static final int Y = 36;
	
	protected ArrayList<GameObject> playerLoot;
	private Animation playerSouthWalk;
	private Animation playerNorthWalk;
	private Animation playerEastWalk;
	private Animation playerWestWalk;
	
	public int maxHealth = 100;
	public int health = maxHealth;
	
	public int experience = 0;
	
	public Player(int x, int y, ID id, Handler handler, SpriteSheet ss) {
		super(x, y, id, handler, ss);
		charDisplay = ss.grabImage(1, 3, X, Y);
		playerLoot = new ArrayList<GameObject>();
		dir = DIRECTION.SOUTH;
		
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
		
//		collision();
		x = Game.clamp(x+velX, 0, (int) (Game.GAME_WIDTH - 1.1*Player.X));
		y = Game.clamp(y + velY, 0, (int) (Game.GAME_HEIGHT - 1.1*Player.Y));
		
		playerSouthWalk.runAnimation();
		playerNorthWalk.runAnimation();
		playerEastWalk.runAnimation();
		playerWestWalk.runAnimation();
		
		return index;
		
		
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
		
		//testing
//		Graphics2D g2d = (Graphics2D) g;
//		g.setColor(Color.red);
//		g2d.draw(getBounds());
		
		
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
			if(tempObject.getId() == ID.BasicEnemy)
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
//					new FloatingText((int) x, (int) y, ID.FloatingText, handler, "Picked up item 1 gold coin");
					handler.removeObject(tempObject);
					System.out.println("Text");
				}
			}
		}
	}
	
	
//	private void collision()
//	{
//		for(int i = 0; i < handler.getObjects().size(); i++)
//		{
//			GameObject tempObject = handler.getObjects().get(i);
//			if(tempObject.getId() == ID.BasicEnemy)
//			{
//				//if basic we're at a basic enemy
//				if(getBounds().intersects(tempObject.getBounds()))
//				{
//					//collision code for if player intersects with basic enemy
//					HUD.HEALTH = Game.clamp(HUD.HEALTH-1, 0, HUD.MAX_HEALTH);
//				}
//			}
//			else if(tempObject.getId() == ID.Block){
//				if(getBounds().intersects(tempObject.getBounds()))
//				{
//					//collided with block
////					System.out.println("Block collision");
//					x -= velX;
//					y -= velY;
//				}
//			}
//		}
//	}
	
	public Rectangle getBounds()
	{
		return new Rectangle((int)x, (int)y, Player.X, Player.Y);
	}
	
}
