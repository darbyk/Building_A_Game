package com.game.gameObjects.lootObjects;

import java.util.ArrayList;
import java.util.Random;

import com.game.interfacesAndAbstracts.Droppable;
import com.game.interfacesAndAbstracts.GameObject;
import com.game.main.Handler;

public class DropBundle implements Droppable {

	public ITEM_LIST itemName;
	public double itemDropRate;
	public int minDropNumber;
	public int maxDropNumber;
	
	ArrayList<Droppable> items = new ArrayList<Droppable>();
	
	public enum ITEM_LIST{
		BUNDLE,
		GOLD_COIN,
		SILVER_COIN,
		COPPER_COIN
	};
	
	public DropBundle(ITEM_LIST itemName, double itemDropRate, int minDropNumber, int maxDropNumber)
	{
		this.itemName = itemName;
		this.itemDropRate = itemDropRate;
		this.minDropNumber = minDropNumber;
		this.maxDropNumber = maxDropNumber;
		this.items.addAll(items);
	}
	
	public DropBundle() {
		
	}

	public boolean shouldDrop() {
		Random r = new Random();
		int result = r.nextInt(100);
		if(result < itemDropRate*100)
			return true;
		return false;
	}
	
	public void add(Droppable droppableItem)
	{
		items.add(droppableItem);
	}
	
	public void chooseItemToGenerate(int x, int y, Handler handler)
	{
		Random r = new Random();
		double probablityResult = r.nextDouble();
		double minState = 0.0;
		Droppable itemToCreate = null;
		for(int i = 0; i < items.size(); i++)
		{
			itemToCreate = items.get(i);
			if(minState + itemToCreate.dropRate() < probablityResult)
			{
				minState += items.get(i).dropRate();
			}
			else
			{
				itemToCreate.convertInstructionToObject(x, y, handler);
				return;
			}
			
		}
	}
	
	public void convertInstructionToObject(int x, int y, Handler handler)
	{
		for(int i = 0; i < items.size(); i++)
		{
			items.get(i).convertInstructionToObject(x, y, handler);
		}
	}

	public double dropRate() {
		return itemDropRate;
	}
	
	public String toString()
	{
		String bufferedString = "";
		for(int i = 0; i < items.size(); i++)
		{
			bufferedString += items.get(i).toString() + "\r\n";
		}
		return bufferedString;
	}

}
