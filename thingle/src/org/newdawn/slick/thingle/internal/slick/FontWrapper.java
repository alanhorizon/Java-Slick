package org.newdawn.slick.thingle.internal.slick;

import org.newdawn.slick.Font;
import org.newdawn.slick.thingle.spi.ThinletFont;

public class FontWrapper implements ThinletFont {
	private Font font;
	
	FontWrapper(Font font) {
		this.font = font;
	}
	
	public int getLineHeight() {
		return font.getLineHeight();
	}

	public int getWidth(String str) {
		return font.getWidth(str);
	}

	public Font getSlickFont() {
		return font;
	}
}
