package org.newdawn.slick.thingle.internal.slick;

import org.newdawn.slick.Color;
import org.newdawn.slick.thingle.spi.ThinletColor;

public class ColorWrapper implements ThinletColor {
	private Color color;
	
	public ColorWrapper(int r, int g, int b) {
		color = new Color(r, g, b);
	}
	
	public ColorWrapper(int value) {
		color = new Color(value);
	}

	private ColorWrapper(Color col) {
		this.color = col;
	}
	
	public ThinletColor brighter() {
		return new ColorWrapper(color.brighter());
	}

	public ThinletColor darker() {
		return new ColorWrapper(color.darker());
	}

	public int getBlue() {
		return color.getBlue();
	}

	public int getGreen() {
		return color.getGreen();
	}

	public int getRed() {
		return color.getRed();
	}

	public Color getSlickColor() {
		return color;
	}
}
