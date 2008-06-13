package org.newdawn.slick.thingle;

import org.newdawn.slick.thingle.spi.ThingleColor;
import org.newdawn.slick.thingle.spi.ThingleContext;
import org.newdawn.slick.thingle.spi.ThingleFont;
import org.newdawn.slick.thingle.spi.ThingleGraphics;
import org.newdawn.slick.thingle.spi.ThingleUtil;

/**
 * A static core utility for accessing the different parts of the
 * Thingle SPI.
 * 
 * @author kevin
 */
public class Thingle {
	/** The context providing the different parts of the SPI */
	private static ThingleContext context;
	/** The utilities class */
	private static ThingleUtil util;
	
	/**
	 * Initialise the Thingle system providing the SPI implementation
	 * 
	 * @param f The context to use for thingle instances
	 */
	public static void init(ThingleContext f) {
		context = f;
		util = context.createUtil();
	}
	
	/**
	 * Get the context used to provide SPI elements
	 * 
	 * @return The context
	 */
	public static ThingleContext getContext() {
		return context;
	}
	
	/**
	 * Get the width of the context being rendered to
	 * 
	 * @return The width of the context being rendered to
	 */
	public static int getWidth() {
		return context.getWidth();
	}
	
	/**
	 * Get the height of the context being rendered to
	 * 
	 * @return The height of the context being rendered to
	 */
	public static int getHeight() {
		return context.getHeight();
	}
	
	/**
	 * Get the graphics context to render to
	 * 
	 * @return The graphics context to render to
	 */
	public static ThingleGraphics getGraphics() {
		return context.getGraphics();
	}
	
	/**
	 * Get the utility class for the context
	 * 
	 * @return The utility class for the context
	 */
	public static ThingleUtil getUtil() {
		return util;
	}
	
	/**
	 * Create a color
	 * 
	 * @param col The 0xAARRGGBB encoding of the color
	 * @return A colour object representing the value given
	 */
	public static ThingleColor createColor(int col) {
		return context.createColor(col);
	}
	
	/**
	 * Create a colour
	 * 
	 * @param red The red component of the colour
	 * @param green The green component of the colour
	 * @param blue The blue component of the colour
	 * @return The newly created colour
	 */
	public static ThingleColor createColor(int red, int green, int blue) {
		return context.createColor(red,green,blue);
	}

	/**
	 * Create a colour
	 * 
	 * @param red The red component of the colour
	 * @param green The green component of the colour
	 * @param blue The blue component of the colour
	 * @return The newly created colour
	 */
	public static ThingleColor createColor(float red, float green, float blue) {
		return context.createColor((int) (red*255),(int) (green*255),(int) (blue*255));
	}

	/**
	 * Create a colour
	 * 
	 * @param red The red component of the colour
	 * @param green The green component of the colour
	 * @param blue The blue component of the colour
	 * @param alpha The alpha component of the colour
	 * @return The newly created colour
	 */
	public static ThingleColor createColor(int red, int green, int blue, int alpha) {
		return context.createColor(red,green,blue,alpha);
	}

	/**
	 * Create a colour
	 * 
	 * @param red The red component of the colour
	 * @param green The green component of the colour
	 * @param blue The blue component of the colour
	 * @param alpha The alpha component of the colour
	 * @return The newly created colour
	 */
	public static ThingleColor createColor(float red, float green, float blue, float alpha) {
		return context.createColor((int) (red*255),(int) (green*255),(int) (blue*255), (int) (alpha*255));
	}
	
	/**
	 * Create a font with the given specifications
	 * 
	 * @param face The name of the type face of the font
	 * @param style The style of font as defined by java.awt.Font
	 * @param size The size of the font
	 * @return A thinlet font created from the given information
	 */
	public static ThingleFont createFont(String face, int style, int size) {
		return context.createFont(face, style, size);
	}
}
