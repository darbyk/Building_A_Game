package com.game.interfacesAndAbstracts;

import com.game.gameObjects.lootObjects.DropItemInstruction;

public interface Lootable {
	
	public DropItemInstruction convertObjectToInstruction();
}
