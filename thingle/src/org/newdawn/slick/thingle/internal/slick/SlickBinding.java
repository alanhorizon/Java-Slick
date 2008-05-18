package org.newdawn.slick.thingle.internal.slick;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.thingle.internal.Rectangle;
import org.newdawn.slick.thingle.internal.ThinletGraphics;

public class SlickBinding implements ThinletGraphics {
	private Graphics g;
	
	public SlickBinding(Graphics g) {
		this.g = g;
	}
	
	public Font getFont() {
		return g.getFont();
	}
	
	public void drawImage(Image image, int x, int y) {
		g.drawImage(image, x, y);
	}
	
	public void drawLine(int x, int y, int ax, int ay) {
		if ((x == ax) || (y == ay)) {
			int x1 = Math.min(x,ax);
			int x2 = Math.max(x,ax)+1;
			int y1 = Math.min(y,ay);
			int y2 = Math.max(y,ay)+1;
			
			g.fillRect(x1,y1,(x2-x1),(y2-y1));
			return;
		}
		
		g.drawLine(x,y,ax,ay);
	}

	public void drawImage(Image image, int x, int y, int x2, int y2, int sx1, int sy1, int sx2, int sy2){
		g.drawImage(image, x, y, x2, y2, sx1, sy1, sx2, sy2);
	}

	public void drawOval(int x, int y, int width, int height) {
		g.drawOval(x, y, width, height);
	}

	public void drawRect(int x, int y, int width, int height) {
		g.drawRect(x,y,width,height);
	}

	public void drawString(String str, int x, int y) {
		g.drawString(str, x, y);
	}

	public void fillOval(int x, int y, int width, int height) {
		g.fillOval(x, y, width, height);
	}

	public void fillRect(int x, int y, int width, int height) {
		g.fillRect(x,y,width,height);
	}

	public Rectangle getClip() {
		org.newdawn.slick.geom.Rectangle rect = g.getWorldClip();
		return new Rectangle((int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight());
	}

	public void setClip(int x, int y, int width, int height) {
		g.setWorldClip(x, y, width, height);
	}

	public void setColor(Color color) {
		g.setColor(color);
	}

	public void setFont(Font font) {
		g.setFont(font);
	}

	public void translate(int x, int y) {
		g.translate(x, y);
	}
	
}
