package com.game.gameObjects.lootObjects;

import java.util.ArrayList;
import java.util.Random;

import com.game.gameObjects.lootObjects.DropBundle.ITEM_LIST;
import com.game.interfacesAndAbstracts.Droppable;
import com.game.interfacesAndAbstracts.GameObject;
import com.game.interfacesAndAbstracts.ID;
import com.game.main.Handler;
import com.game.resources.ResourceLoader.RESOURCE_TYPES;

public class DropItemInstruction implements Droppable {

	public ITEM_LIST itemName;
	public double itemDropRate;
	public int minDropNumber;
	public int maxDropNumber;
	public int stackedAmount;
	
	public DropItemInstruction(ITEM_LIST itemName, double itemDropRate, int minDropNumber, int maxDropNumber)
	{
		this.itemName = itemName;
		this.itemDropRate = itemDropRate;
		this.minDropNumber = minDropNumber;
		this.maxDropNumber = maxDropNumber;
		stackedAmount = 1;
	}
	public DropItemInstruction(ITEM_LIST itemName, int stackedAmount)
	{
		this.itemName = itemName;
		this.stackedAmount = stackedAmount;
		this.itemDropRate = 1;
		this.minDropNumber = stackedAmount;
		this.maxDropNumber = stackedAmount;
	}
	
	
	public boolean shouldDrop()
	{
		Random r = new Random();
		int result = r.nextInt(100);
		if(result < itemDropRate*100)
			return true;
		return false;
	}

	public double dropRate() {
		return itemDropRate;
	}
	
	public void convertInstructionToObject(int x, int y, Handler handler)
	{
		Random r = new Random();
		int dropAmount = r.nextInt(maxDropNumber - minDropNumber + 1) + minDropNumber;
		if(itemName == ITEM_LIST.GOLD_COIN)
		{
			new GoldCoin(x, y, ID.Item, handler, handler.getResourceLoader().getSpriteSheetByType(RESOURCE_TYPES.GoldCoin), dropAmount);
		}
		else if(itemName == ITEM_LIST.SILVER_COIN)
		{
			new SilverCoin(x, y, ID.Item, handler, handler.getResourceLoader().getSpriteSheetByType(RESOURCE_TYPES.SilverCoin), dropAmount);
		}
		else if(itemName == ITEM_LIST.COPPER_COIN)
		{
			new CopperCoin(x, y, ID.Item, handler, handler.getResourceLoader().getSpriteSheetByType(RESOURCE_TYPES.CopperCoin), dropAmount );
		}
	}
	
	public DropItemInstruction convertObjectToInstruction(GameObject go)
	{
		ArrayList<DropItemInstruction> dis = new ArrayList<DropItemInstruction>();
		return null;
	}
	
	public String toString()
	{
		return "Item is: " + itemName + " at stackedAmount: " + stackedAmount;
	}
	
	public ITEM_LIST getItemName()
	{
		return itemName;
	}
	public int getStackedAmount()
	{
		return stackedAmount;
	}
	
	public void increaseStackedAmount(int amountToAdd)
	{
		this.stackedAmount = stackedAmount + amountToAdd;
		this.minDropNumber = minDropNumber + amountToAdd;
		this.maxDropNumber = maxDropNumber + amountToAdd;
	}
	
}
