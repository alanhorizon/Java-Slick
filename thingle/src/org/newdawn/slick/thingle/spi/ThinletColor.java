package org.newdawn.slick.thingle.spi;

public interface ThinletColor {

	public int getRed();
	
	public int getBlue();
	
	public int getGreen();
	
	public ThinletColor darker();
	
	public ThinletColor brighter();
}
