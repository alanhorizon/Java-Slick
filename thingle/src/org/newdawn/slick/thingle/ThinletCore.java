package org.newdawn.slick.thingle;

import org.newdawn.slick.thingle.spi.ThinletColor;
import org.newdawn.slick.thingle.spi.ThinletContext;
import org.newdawn.slick.thingle.spi.ThinletGraphics;
import org.newdawn.slick.thingle.spi.ThinletInput;
import org.newdawn.slick.thingle.spi.ThinletUtil;

public class ThinletCore {
	private static ThinletContext context;
	private static ThinletUtil util;
	private static ThinletInput input;
	
	public static void init(ThinletContext f) {
		context = f;
		util = context.createUtil();
	}
	
	public static ThinletContext getFactory() {
		return context;
	}
	
	public static int getWidth() {
		return context.getWidth();
	}
	
	public static int getHeight() {
		return context.getHeight();
	}
	
	public static ThinletGraphics getGraphics() {
		return context.getGraphics();
	}
	
	public static ThinletUtil getUtil() {
		return util;
	}
	
	public static ThinletColor createColor(int col) {
		return context.createColor(col);
	}
	
	public static ThinletColor createColor(int red, int green, int blue) {
		return context.createColor(red,green,blue);
	}
	
	public static ThinletColor createColor(float red, float green, float blue) {
		return context.createColor((int) (red*255),(int) (green*255),(int) (blue*255));
	}
	
	public static ThinletColor createColor(int red, int green, int blue, int alpha) {
		return context.createColor(red,green,blue,alpha);
	}
	
	public static ThinletColor createColor(float red, float green, float blue, float alpha) {
		return context.createColor((int) (red*255),(int) (green*255),(int) (blue*255), (int) (alpha*255));
	}
}
