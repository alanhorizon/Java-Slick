package org.newdawn.commet.space;

public class Blob {
	private int x;
	private int y;
	private int timer;
	
	public Blob() {
		
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void update(int delta) {
		timer += delta;
	}
}
