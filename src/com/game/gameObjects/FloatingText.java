package com.game.gameObjects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.game.interfacesAndAbstracts.GameObject;
import com.game.interfacesAndAbstracts.ID;
import com.game.main.Handler;

public class FloatingText extends GameObject {

	String textToShow;
	int ticksToSustain = 60;
	
	public FloatingText(int x, int y, ID id, Handler handler, String textToShow) {
		super(x, y, id, handler);
		this.textToShow = textToShow;
	}

	public void render(Graphics g) {
		Font fnt = new Font("arial", 1, 24);
//		Font fnt2 = new Font("arial", 1, 30);
		
		g.setFont(fnt);
		g.setColor(Color.white);
		g.drawString(textToShow, (int)x, (int)y);
	}

	public Rectangle getBounds() {
		return null;
	}

	public void collision() {
		
	}

	public int tickHelper(int index) {
		ticksToSustain--;
		if(ticksToSustain < 0)
		{
			handler.removeObject(this);
			index--;
		}
		return index;
	}

	
}
