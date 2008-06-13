package org.newdawn.slick.thingle.spi;

import java.io.InputStream;
import java.net.URL;

public interface ThinletFactory {

	public ThinletUtil createUtil();
	
	public ThinletColor createColor(int col);
	
	public ThinletColor createColor(int red, int green, int blue);
	
	public ThinletFont getDefaultFont();
	
	public void log(String message);
	
	public void log(String message, Throwable e);
	
	public void log(Throwable e);
	
	public InputStream getResourceAsStream(String ref);
	
	public URL getResource(String ref);
	
	public ThinletImage createImage(InputStream in, String name, boolean flipped) throws ThinletException;
	
	public ThinletImageBuffer createImageBuffer(int width, int height);
}
