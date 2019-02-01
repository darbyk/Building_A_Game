package com.game.interfacesAndAbstracts;

import com.game.main.Handler;

public interface Droppable {
	
	public boolean shouldDrop();
	public double dropRate();
	public void convertInstructionToObject(int x, int y, Handler handler);
}
