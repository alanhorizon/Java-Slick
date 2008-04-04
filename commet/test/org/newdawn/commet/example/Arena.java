package org.newdawn.commet.example;

import java.util.ArrayList;

public class Arena {
	private ArrayList<Tank> tanks = new ArrayList<Tank>();
	
	public Arena() {
	}
	
	public int getTankCount() {
		return tanks.size();
	}
	
	public Tank getTank(int index) {
		return tanks.get(index);
	}
	
	public void addTank(Tank tank) {
		tanks.add(tank);
	}
	
	public void removeTank(Tank tank) {
		tanks.remove(tank);
	}
}
