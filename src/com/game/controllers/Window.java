package com.game.controllers;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Window extends Canvas{

	private static final long serialVersionUID = -1478604005915452565L;
	
	public int width;
	public int height;
	public String title;
	
	JFrame frame;
	
	public Window(int width, int height, String title)
	{
		frame = new JFrame(title);
		
		frame.setPreferredSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
//		frame.add(game);
		
		frame.setVisible(true);
//		game.start();
		
	}
	
	public void setFrame(Game game){
		frame.add(game);
	}
	
}
