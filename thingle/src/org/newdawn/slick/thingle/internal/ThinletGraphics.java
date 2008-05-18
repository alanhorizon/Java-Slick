package org.newdawn.slick.thingle.internal;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Image;

public interface ThinletGraphics {

	public Font getFont();
	
	public void setFont(Font font);
	
	public void drawImage(Image image, int x, int y, int x2, int y2, int sx1, int sy1, int sx2, int sy2);
		
	public void drawImage(Image image, int x, int y);
	
	public void drawLine(int x, int y, int x2, int y2);
	
	public void drawOval(int x, int y, int width, int height);
	
	public void drawRect(int x, int y, int width, int height);
	
	public void fillOval(int x, int y, int width, int height);
	
	public void fillRect(int x, int y, int width, int height);
	
	public Rectangle getClip();
	
	public void setClip(int x, int y, int width, int height);
	
	public void setColor(Color color);
	
	public void drawString(String str, int x, int y);
	
	public void translate(int x, int y);
}
