package com.game.lootObjects;

import java.util.Random;

import com.game.gameObjects.ID;
import com.game.interfacesAndAbstracts.Droppable;
import com.game.lootObjects.DropBundle.ITEM_LIST;
import com.game.main.Handler;
import com.game.resources.ResourceLoader.RESOURCE_TYPES;

public class DropItemInstruction implements Droppable {

	public ITEM_LIST itemName;
	public double itemDropRate;
	public int minDropNumber;
	public int maxDropNumber;
	
	public DropItemInstruction(ITEM_LIST itemName, double itemDropRate, int minDropNumber, int maxDropNumber)
	{
		this.itemName = itemName;
		this.itemDropRate = itemDropRate;
		this.minDropNumber = minDropNumber;
		this.maxDropNumber = maxDropNumber;
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
		if(itemName == ITEM_LIST.GOLD_COIN)
		{
			new GoldCoin(x, y, ID.Item, handler, handler.getResourceLoader().getSpriteSheetByType(RESOURCE_TYPES.GoldCoin));
		}
		else if(itemName == ITEM_LIST.SILVER_COIN)
		{
			new SilverCoin(x, y, ID.Item, handler, handler.getResourceLoader().getSpriteSheetByType(RESOURCE_TYPES.SilverCoin));
		}
		else if(itemName == ITEM_LIST.COPPER_COIN)
		{
			new CopperCoin(x, y, ID.Item, handler, handler.getResourceLoader().getSpriteSheetByType(RESOURCE_TYPES.CopperCoin));
		}
	}
}
