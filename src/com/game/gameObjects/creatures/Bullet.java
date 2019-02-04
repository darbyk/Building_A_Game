package com.game.gameObjects.creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import com.game.controllers.Camera;
import com.game.controllers.Game;
import com.game.controllers.SpriteSheet;
import com.game.gameObjects.Trail;
import com.game.interfacesAndAbstracts.GameObject;
import com.game.interfacesAndAbstracts.ID;
import com.game.interfacesAndAbstracts.StaticCalculator;
import com.game.main.Handler;

public class Bullet extends GameObject{
	
	public static final int X = 16;
	public static final int Y = 16;
	GameObject firer;
	private Camera camera;
	private final int baseVelocity = 8;
	
	public Bullet(int x, int y, ID id, Handler handler, GameObject firer, Camera camera, SpriteSheet ss) {
		super(x, y, id, handler, ss);
		this.firer = firer;
		this.camera = camera;
		
		charDisplay = ss.grabImage(1, 1, X, Y);
		
		this.x = firer.getX();
		this.y = firer.getY();
		
		double angle = StaticCalculator.calculateAngleOfTwoPoints(this.x, this.y, x + camera.getX(), y+camera.getY());
		
		velX = StaticCalculator.calculateVelXByAngleAndBaseVelocity(baseVelocity, angle);
		velY = StaticCalculator.calculateVelYByAngleAndBaseVelocity(baseVelocity, angle);
	}

	public int tickHelper(int index) {
		x = Game.clamp(x+velX, -Bullet.X, (int) (Game.GAME_WIDTH));
		y = Game.clamp(y + velY, -Bullet.Y, (int) (Game.GAME_HEIGHT));
		
		if(x == Game.GAME_WIDTH || x == -Bullet.X)
		{
			handler.removeObject(this);
			index--;
		}
		else if(y == Game.GAME_HEIGHT || y == -Bullet.Y)
		{
			handler.removeObject(this);
			index--;
		}
		new Trail((int)x, (int)y, ID.Trail, handler, Bullet.X, Bullet.Y, 0.05f, Color.red, charDisplay);
		
		return index;
		
	}
	
	public void collision()
	{
		//Add some collision code
		for(int i = 0; i < handler.getObjects().size(); i++)
		{
			GameObject tempObject = handler.getObjects().get(i);
			if(tempObject.getId() == ID.Block)
			{
//				boolean test = getBounds().intersects(tempObject.getBounds());
				if(tempObject != this && !place_free((int) (x + velX), (y+velY), getBounds(), tempObject.getBounds()))
				{
					handler.removeObject(this);
				}
			}
		}
	}

	public void render(Graphics g) {
//		g.setColor(Color.green);
//		g.fillRect((int)x, (int)y, Bullet.X, Bullet.Y);
		g.drawImage(charDisplay, (int)x, (int)y, null);
		
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, Bullet.X, Bullet.Y);
	}

	
}
