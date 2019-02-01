package com.game.main;

import java.awt.Graphics;
import java.util.ArrayList;

import com.game.interfacesAndAbstracts.GameObject;
import com.game.resources.ResourceLoader;

public class Handler {
	ArrayList<GameObject> objects = new ArrayList<GameObject>();
	protected ResourceLoader resourceLoader;
	
	public Handler(ResourceLoader resourceLoader)
	{
		this.resourceLoader = resourceLoader;
	}
	
	public ResourceLoader getResourceLoader()
	{
		return resourceLoader;
	}
	
	public void tick(){
		for(int i = 0; i < objects.size(); i++)
		{
			i = objects.get(i).tick(i);
		}
	}
	
	public void render(Graphics g){
		for(int i = 0; i < objects.size(); i++)
		{
			objects.get(i).render(g);
		}
	}
	
	public void addObject(GameObject object)
	{
		this.objects.add(object);
	}
	
	public boolean removeObject(GameObject object)
	{
		return this.objects.remove(object);
	}
	public ArrayList<GameObject> getObjects()
	{
		return objects;
	}
}
