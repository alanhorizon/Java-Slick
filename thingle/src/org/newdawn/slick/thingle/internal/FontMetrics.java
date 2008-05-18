package org.newdawn.slick.thingle.internal;

import org.newdawn.slick.Font;

public class FontMetrics {
	private Font font;
	
	public FontMetrics(Font font) {
		this.font = font;
	}
	
	public int charWidth(char c) {
		return font.getWidth(""+c);
	}
	
	public int getHeight() {
		return font.getLineHeight();
	}
	
	public int charsWidth(char[] data, int start, int length) {
		return font.getWidth(new String(data, start, length));
	}
	
	public int stringWidth(String str) {
		return font.getWidth(str);
	}
	
	public int getAscent() {
		return font.getLineHeight();
	}
	
	public int getLeading() {
		return 0;
	}
	
	public int getDescent() {
		return 1;
	}
}
