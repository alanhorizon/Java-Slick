package org.newdawn.slick.thingle.internal.slick;

import org.newdawn.slick.Font;
import org.newdawn.slick.thingle.spi.ThinletFont;

/**
 * A wrapper to make a Slick font look like a Thinlet font
 * 
 * @author kevin
 */
public class FontWrapper implements ThinletFont {
	/** The font wrapped */
	private Font font;
	
	/**
	 * Create a new font wrapped
	 * 
	 * @param font The font to be wrapped
	 */
	FontWrapper(Font font) {
		this.font = font;
	}
	
	/**
	 * @see org.newdawn.slick.thingle.spi.ThinletFont#getLineHeight()
	 */
	public int getLineHeight() {
		return font.getLineHeight();
	}

	/**
	 * @see org.newdawn.slick.thingle.spi.ThinletFont#getWidth(java.lang.String)
	 */
	public int getWidth(String str) {
		return font.getWidth(str);
	}

	/**
	 * Get the wrapped font
	 * 
	 * @return The slick font that is wrapped up
	 */
	public Font getSlickFont() {
		return font;
	}
}
