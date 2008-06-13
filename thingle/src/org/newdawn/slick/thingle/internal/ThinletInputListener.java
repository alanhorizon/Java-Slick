package org.newdawn.slick.thingle.internal;

import org.newdawn.slick.thingle.spi.ThinletInput;

public interface ThinletInputListener {
	public void mouseDragged(int x, int y, ThinletInput mods);
	
	public void mouseMoved(int x, int y, ThinletInput mods);
	
	public void mouseReleased(int x, int y, ThinletInput mods);
	
	public void mouseWheelMoved(int rotation, ThinletInput mods);
		
	public void mousePressed(int x, int y, int clickCount, ThinletInput mods);
		
	public boolean keyPressed(char keychar, int keycode, ThinletInput mods, boolean typed);
}
