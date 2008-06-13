package org.newdawn.slick.thingle.spi;

/**
 * Contract between thinlet and it's fonts
 * 
 * @author kevin
 */
public interface ThinletFont {
	/**
	 * Get the width of the given string in pixels if it was drawn
	 * with this font
	 * 
	 * @param str The string to measure
	 * @return The width in pixels
	 */
	public int getWidth(String str);
	
	/**
	 * Get the height of any line drawn with this font
	 * 
	 * @return The height of the line in pixels
	 */
	public int getLineHeight();
}
