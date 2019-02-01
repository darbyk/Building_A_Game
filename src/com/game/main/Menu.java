package com.game.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import com.game.controllers.Game;
import com.game.controllers.Game.STATE;
import com.game.controllers.SpriteSheet;
import com.game.gameObjects.Player;

public class Menu extends MouseAdapter{
	
	Game game;
	Handler handler;
	SpriteSheet ss;
	public Player playerObject;
	
	public Menu(Game game, Handler handler, SpriteSheet ss)
	{
		this.game = game;
		this.handler = handler;
	}
	
	public void mousePressed(MouseEvent e)
	{
		int mx = e.getX();
		int my = e.getY();
		
		Random r = new Random();
		
		if(STATE.Menu == game.getGameState())
		{
			if(mouseOver(mx,my,210,150,200,64))
			{
				game.gameState = STATE.Game;
//				playerObject = game.loadLevel(game.getLevel());
			}
			else if(mouseOver(mx,my,210,250,200,64))
			{
				game.gameState = STATE.Help;
			}
			else if(mouseOver(mx,my,210,350,200,64))
			{
				game.gameState = STATE.Quit;
			}
			
		}
		else if (game.getGameState() == STATE.Help)
		{
			if(mouseOver(mx,my,210,250,200,64))
			{
				game.gameState = STATE.Menu;
			}
		}
		else if(game.getGameState() == STATE.Quit)
		{
			
		}
	}
	
	private boolean mouseOver(int mx, int my, int x, int y, int width, int height)
	{
		if(mx > x && mx < x + width)
		{
			if(my > y && my < y + height)
			{
				return true;
			}
			else
				return false;
		}
		else return false;
	}
	
	public void mouseReleased(MouseEvent e)
	{
		
	}
	
	public void tick()
	{
		
	}
	
	public void render(Graphics g)
	{
		Font fnt = new Font("arial", 1, 50);
		Font fnt2 = new Font("arial", 1, 30);
		
		g.setFont(fnt);
		g.setColor(Color.white);
		g.drawString("Menu", 240, 70);
		STATE curGameState = game.getGameState();
		if(STATE.Menu == curGameState)
		{
			
			g.setFont(fnt2);
			g.drawRect(210, 150, 200, 64);
			g.drawString("Play", 270, 190);
			
			g.drawRect(210, 250, 200, 64);
			g.drawString("Help", 270, 290);
			
			g.drawRect(210, 350, 200, 64);
			g.drawString("Quit", 270, 390);
		}
		if(STATE.Help == curGameState)
		{
			g.setColor(Color.white);
			g.setFont(fnt2);
			g.drawString("This game is an example", 140
					, 150);
			
			g.drawRect(210, 250, 200, 64);
			g.drawString("Back", 270, 290);
		}
	}
}
