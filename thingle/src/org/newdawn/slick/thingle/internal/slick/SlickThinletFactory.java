package org.newdawn.slick.thingle.internal.slick;

import java.io.InputStream;
import java.net.URL;

import org.lwjgl.Sys;
import org.newdawn.slick.Font;
import org.newdawn.slick.Image;
import org.newdawn.slick.ImageBuffer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.thingle.spi.ThinletColor;
import org.newdawn.slick.thingle.spi.ThinletException;
import org.newdawn.slick.thingle.spi.ThinletFactory;
import org.newdawn.slick.thingle.spi.ThinletFont;
import org.newdawn.slick.thingle.spi.ThinletImage;
import org.newdawn.slick.thingle.spi.ThinletImageBuffer;
import org.newdawn.slick.thingle.spi.ThinletUtil;
import org.newdawn.slick.util.Log;
import org.newdawn.slick.util.ResourceLoader;

public class SlickThinletFactory implements ThinletFactory, ThinletUtil {
	private TrueTypeFont font = new TrueTypeFont(new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 12), false);
	private FontWrapper defaultFont = new FontWrapper(font);
	
	public ThinletUtil createUtil() {
		return this;
	}

	public String getClipboard() {
		return (String) Sys.getClipboard();
	}

	public ThinletColor createColor(int col) {
		return new ColorWrapper(col);
	}

	public ThinletColor createColor(int red, int green, int blue) {
		return new ColorWrapper(red, green, blue);
	}

	public void log(String message) {
		Log.warn(message);
	}

	public void log(String message, Throwable e) {
		Log.error(message, e);
	}

	public void log(Throwable e) {
		Log.error(e);
	}

	public ThinletFont getDefaultFont() {
		return defaultFont;
	}

	public ThinletFont createThinletFont(Font font) {
		return new FontWrapper(font);
	}

	public URL getResource(String ref) {
		return ResourceLoader.getResource(ref);
	}

	public InputStream getResourceAsStream(String ref) {
		return ResourceLoader.getResourceAsStream(ref);
	}

	public ThinletImage createImage(InputStream in, String name, boolean flipped)
			throws ThinletException {
		try {
			return new ImageWrapper(new Image(in, name, flipped));
		} catch (SlickException e) {
			throw new ThinletException(e);
		}
	}

	public ThinletImageBuffer createImageBuffer(int width, int height) {
		return new ImageBufferWrapper(new ImageBuffer(width, height));
	}
}
