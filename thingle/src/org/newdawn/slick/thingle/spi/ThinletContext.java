package org.newdawn.slick.thingle.spi;

import java.io.InputStream;
import java.net.URL;

import org.newdawn.slick.thingle.internal.ThinletInputListener;

public interface ThinletContext {

	public ThinletUtil createUtil();
	
	public ThinletInput createInput(ThinletInputListener listener);
	
	public ThinletColor createColor(int col);
	
	public ThinletColor createColor(int red, int green, int blue);
	
	public ThinletColor createColor(int red, int green, int blue, int alpha);
	
	public ThinletFont getDefaultFont();
	
	public void log(String message);
	
	public void log(String message, Throwable e);
	
	public void log(Throwable e);
	
	public InputStream getResourceAsStream(String ref);
	
	public URL getResource(String ref);
	
	public ThinletImage createImage(InputStream in, String name, boolean flipped) throws ThinletException;
	
	public ThinletImageBuffer createImageBuffer(int width, int height);
	
	public ThinletGraphics getGraphics();
	
	public int getWidth();
	
	public int getHeight();
}
