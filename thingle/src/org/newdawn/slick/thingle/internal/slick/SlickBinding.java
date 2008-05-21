package org.newdawn.slick.thingle.internal.slick;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.thingle.internal.Rectangle;
import org.newdawn.slick.thingle.internal.ThinletGraphics;

/**
 * A binding that uses slick to render what thinlet requires
 * 
 * @author kevin
 */
public class SlickBinding implements ThinletGraphics {
	/** The graphics context to render to */
	private Graphics g;
	
	/**
	 * Create a new slick binding
	 * 
	 * @param g The graphics context to render to
	 */
	public SlickBinding(Graphics g) {
		this.g = g;
	}
	
	/**
	 * @see org.newdawn.slick.thingle.internal.ThinletGraphics#getFont()
	 */
	public Font getFont() {
		return g.getFont();
	}
	
	/**
	 * @see org.newdawn.slick.thingle.internal.ThinletGraphics#drawImage(org.newdawn.slick.Image, int, int)
	 */
	public void drawImage(Image image, int x, int y) {
		g.drawImage(image, x, y);
	}
	
	/**
	 * @see org.newdawn.slick.thingle.internal.ThinletGraphics#drawLine(int, int, int, int)
	 */
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

	/**
	 * @see org.newdawn.slick.thingle.internal.ThinletGraphics#drawImage(org.newdawn.slick.Image, int, int, int, int, int, int, int, int)
	 */
	public void drawImage(Image image, int x, int y, int x2, int y2, int sx1, int sy1, int sx2, int sy2){
		g.drawImage(image, x, y, x2, y2, sx1, sy1, sx2, sy2);
	}

	/**
	 * @see org.newdawn.slick.thingle.internal.ThinletGraphics#drawOval(int, int, int, int)
	 */
	public void drawOval(int x, int y, int width, int height) {
		g.drawOval(x, y, width, height);
	}

	/**
	 * @see org.newdawn.slick.thingle.internal.ThinletGraphics#drawRect(int, int, int, int)
	 */
	public void drawRect(int x, int y, int width, int height) {
		g.drawRect(x,y,width,height);
	}

	/**
	 * @see org.newdawn.slick.thingle.internal.ThinletGraphics#drawString(java.lang.String, int, int)
	 */
	public void drawString(String str, int x, int y) {
		g.drawString(str, x, y);
	}

	/**
	 * @see org.newdawn.slick.thingle.internal.ThinletGraphics#fillOval(int, int, int, int)
	 */
	public void fillOval(int x, int y, int width, int height) {
		g.fillOval(x, y, width, height);
	}

	/**
	 * @see org.newdawn.slick.thingle.internal.ThinletGraphics#fillRect(int, int, int, int)
	 */
	public void fillRect(int x, int y, int width, int height) {
		g.fillRect(x,y,width,height);
	}

	/**
	 * @see org.newdawn.slick.thingle.internal.ThinletGraphics#getClip()
	 */
	public Rectangle getClip() {
		org.newdawn.slick.geom.Rectangle rect = g.getWorldClip();
		return new Rectangle((int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight());
	}

	/**
	 * @see org.newdawn.slick.thingle.internal.ThinletGraphics#setClip(int, int, int, int)
	 */
	public void setClip(int x, int y, int width, int height) {
		g.setWorldClip(x, y, width, height);
	}

	/**
	 * @see org.newdawn.slick.thingle.internal.ThinletGraphics#setColor(org.newdawn.slick.Color)
	 */
	public void setColor(Color color) {
		g.setColor(color);
	}

	/**
	 * @see org.newdawn.slick.thingle.internal.ThinletGraphics#setFont(org.newdawn.slick.Font)
	 */
	public void setFont(Font font) {
		g.setFont(font);
	}

	/**
	 * @see org.newdawn.slick.thingle.internal.ThinletGraphics#translate(int, int)
	 */
	public void translate(int x, int y) {
		g.translate(x, y);
	}
}
