package org.newdawn.slick.thingle.internal;


public class Rectangle {
	public int x;
	public int y;
	public int width;
	public int height;
	
	public Rectangle(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public boolean contains(int xp, int yp) {
		if (xp < x) {
			return false;
		}
		if (yp < y) {
			return false;
		}
		
		if (xp > x+width) {
			return false;
		}
		if (yp > y+height) {
			return false;
		}
		
		return true;
	}
	
	public boolean intersects(Rectangle other) {
		if ((x > (other.x + other.width)) || ((x + width) < other.x)) {
			return false;
		}
		if ((y > (other.y + other.height)) || ((y + height) < other.y)) {
			return false;
		}
		
        return true;	
	}
	
	public Rectangle intersection(Rectangle other) {
		if (!intersects(other)) {
			return new Rectangle(0,0,0,0);
		}
		
		int x1 = Math.max(x, other.x);
		int y1 = Math.max(y, other.y);
		int x2 = Math.min(x+width, other.x+other.width);
		int y2 = Math.min(y+height, other.y+other.height);
		
		return new Rectangle(x1,y1,(x2-x1),(y2-y1));
	}
}
