package com.game.gameObjects.lootObjects;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.game.controllers.SpriteSheet;
import com.game.gameObjects.lootObjects.DropBundle.ITEM_LIST;
import com.game.interfacesAndAbstracts.GameObject;
import com.game.interfacesAndAbstracts.ID;
import com.game.interfacesAndAbstracts.Lootable;
import com.game.main.Handler;

public class Loot extends GameObject implements Lootable{

	public final static int X = 16;
	public final static int Y = 16;
	
	private ITEM_LIST itemList;
	private int stackedAmount;
//	private int maxStackedAmount;
	
	/* Constructors */
	public Loot(int x, int y, ID id, Handler handler, SpriteSheet ss, int stackedAmount) {
		super(x, y, id, handler, ss);
		this.stackedAmount = stackedAmount;
		charDisplay = ss.grabImage(1, 1, X, Y);
		setItemList(null);
	}

	/* Implemented Methods */
	public DropItemInstruction convertObjectToInstruction() {
		DropItemInstruction loot = new DropItemInstruction(itemList, stackedAmount);
		return loot;
	}

	
	/* Overridden Methods */
	public void render(Graphics g) {
		g.drawImage(charDisplay, (int)x, (int)y, null);
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, Loot.X, Loot.Y);
	}

	public void collision() {
	}

	public int tickHelper(int index) {
		return index;
	}

	/* Getters and Setters */
	public int getStackedAmount() {
		return stackedAmount;
	}

	public void setStackedAmount(int stackedAmount) {
		this.stackedAmount = stackedAmount;
	}

	public ITEM_LIST getItemList() {
		return itemList;
	}

	public void setItemList(ITEM_LIST itemList) {
		this.itemList = itemList;
	}
}
