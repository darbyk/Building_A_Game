package com.game.interfacesAndAbstracts;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.game.controllers.SpriteSheet;
import com.game.main.Handler;

public abstract class GameObject {

	protected float x, y;
	protected float velX, velY;
	protected ID id;
	protected Handler handler;
	protected DIRECTION dir;
	public int curId;
	
	protected BufferedImage charDisplay;
	
	protected SpriteSheet ss;
	
	public enum DIRECTION{
		NORTH,
		EAST,
		SOUTH,
		WEST
	};

	/* Constructors */
	public GameObject(int x, int y, ID id, Handler handler)
	{
		this.x = x;
		this.y = y;
		this.id = id;
		this.handler = handler;
		handler.addObject(this);
	}
	
	public GameObject(int x, int y, ID id, Handler handler, DIRECTION dir, SpriteSheet ss)
	{
		this(x, y, id, handler);
		this.dir = dir;
	}
	
	public GameObject(int x, int y, ID id, Handler handler, SpriteSheet ss)
	{
		this(x, y, id, handler);
		this.ss = ss;
	}
	
	//abstract methods
	public abstract void render(Graphics g);
	public abstract Rectangle getBounds();
	public abstract void collision();
	public abstract int tickHelper(int index);
	
	public boolean place_free(float x, float y, Rectangle myRect, Rectangle otherRect) {
		myRect.x = (int)x;
		myRect.y = (int)y;
		if (myRect.intersects(otherRect)) {
			return false;
		}
		return true;
	}
	
	public int tick(int index)
	{
		collision();
		index = tickHelper(index);
		
		return index;
	}
	
	//getters
	public float getX(){
		return x;
	}
	public float getY(){
		return y;
	}
	public ID getId(){
		return id;
	}
	public float getVelX(){
		return velX;
	}
	public float getVelY(){
		return velY;
	}
	public DIRECTION getDirection()
	{
		return dir;
	}
	
	//setters
	public void setX(float x){
		this.x = x;
	}
	public void setY(float y){
		this.y = y;
	}
	public void setId(ID id){
		this.id = id;
	}
	public void setVelX(float velX){
		this.velX = velX;
	}
	public void setVelY(float velY){
		this.velY = velY;
	}
	public void setDirection(DIRECTION dir)
	{
		this.dir = dir;
	}
	
}
