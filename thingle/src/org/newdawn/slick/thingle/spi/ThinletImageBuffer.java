package org.newdawn.slick.thingle.spi;

public interface ThinletImageBuffer {

	public void setRGBA(int x, int y, int r, int g, int b, int a);
	
	public ThinletImage getImage();
}
