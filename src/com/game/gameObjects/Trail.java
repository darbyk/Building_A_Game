package com.game.gameObjects;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.game.interfacesAndAbstracts.GameObject;
import com.game.main.Handler;

public class Trail extends GameObject {

	private float alpha = 1;
	private float removalValue;
	private Color color;
	
	private int width, height;
	
	public Trail(int x, int y, ID id, Handler handler, int width, int height, float removalValue, Color color, BufferedImage imageTrail) {
		super(x, y, id, handler);
		this.color = color;
		this.width = width;
		this.height = height;
		this.removalValue = removalValue;
		charDisplay = imageTrail;
	}

	public int tickHelper(int index) {
		if(alpha > removalValue)
			alpha -= removalValue*1;
		else
			handler.removeObject(this);
		
		return index;
	}
	
	public void collision()
	{
		//no collision code
	}

	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setComposite(makeTransparent(alpha));
		
//		g.setColor(color);
//		g.fillRect((int)x, (int)y, width, height);
		g.drawImage(charDisplay, (int)x, (int)y, null);
		
		g2d.setComposite(makeTransparent(1));
	}

	
	private AlphaComposite makeTransparent(float alpha)
	{
		int type = AlphaComposite.SRC_OVER;
		return (AlphaComposite.getInstance(type, alpha));
	}
	
	public Rectangle getBounds() {
		return null;
	}

}
