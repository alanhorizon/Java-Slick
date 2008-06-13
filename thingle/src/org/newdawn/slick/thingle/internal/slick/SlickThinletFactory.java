package org.newdawn.slick.thingle.internal.slick;

import java.io.InputStream;
import java.net.URL;

import org.lwjgl.Sys;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.ImageBuffer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.thingle.internal.ThinletInputListener;
import org.newdawn.slick.thingle.spi.ThinletColor;
import org.newdawn.slick.thingle.spi.ThinletException;
import org.newdawn.slick.thingle.spi.ThinletContext;
import org.newdawn.slick.thingle.spi.ThinletFont;
import org.newdawn.slick.thingle.spi.ThinletGraphics;
import org.newdawn.slick.thingle.spi.ThinletImage;
import org.newdawn.slick.thingle.spi.ThinletImageBuffer;
import org.newdawn.slick.thingle.spi.ThinletInput;
import org.newdawn.slick.thingle.spi.ThinletUtil;
import org.newdawn.slick.util.Log;
import org.newdawn.slick.util.ResourceLoader;

/**
 * A SPI implementation for Slick
 * 
 * @author kevin
 */
public class SlickThinletFactory implements ThinletContext, ThinletUtil {
	/** The default font in Slick */
	private TrueTypeFont font = new TrueTypeFont(new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 12), false);
	/** The default font for Thinlet */
	private FontWrapper defaultFont = new FontWrapper(font);
	/** The game container running this implementation */
	private GameContainer container;
	
	/**
	 * Create a new context
	 * 
	 * @param container The container the context will be rendered to
	 */
	public SlickThinletFactory(GameContainer container) {
		this.container = container;
		container.getInput().setDoubleClickInterval(250);
	}
	
	/**
	 * @see org.newdawn.slick.thingle.spi.ThinletContext#createUtil()
	 */
	public ThinletUtil createUtil() {
		return this;
	}

	/**
	 * @see org.newdawn.slick.thingle.spi.ThinletUtil#getClipboard()
	 */
	public String getClipboard() {
		return (String) Sys.getClipboard();
	}

	/**
	 * @see org.newdawn.slick.thingle.spi.ThinletContext#createColor(int)
	 */
	public ThinletColor createColor(int col) {
		return new ColorWrapper(col);
	}

	/**
	 * @see org.newdawn.slick.thingle.spi.ThinletContext#createColor(int, int, int)
	 */
	public ThinletColor createColor(int red, int green, int blue) {
		return new ColorWrapper(red, green, blue);
	}

	/**
	 * @see org.newdawn.slick.thingle.spi.ThinletContext#log(java.lang.String)
	 */
	public void log(String message) {
		Log.warn(message);
	}

	/**
	 * @see org.newdawn.slick.thingle.spi.ThinletContext#log(java.lang.String, java.lang.Throwable)
	 */
	public void log(String message, Throwable e) {
		Log.error(message, e);
	}

	/**
	 * @see org.newdawn.slick.thingle.spi.ThinletContext#log(java.lang.Throwable)
	 */
	public void log(Throwable e) {
		Log.error(e);
	}

	/**
	 * @see org.newdawn.slick.thingle.spi.ThinletContext#getDefaultFont()
	 */
	public ThinletFont getDefaultFont() {
		return defaultFont;
	}

	/**
	 * Create a thinlet font from a Slick Font
	 * 
	 * @param font The font to wrap
	 * @return The Thinlet font 
	 */
	public ThinletFont createThinletFont(Font font) {
		return new FontWrapper(font);
	}

	/**
	 * @see org.newdawn.slick.thingle.spi.ThinletContext#getResource(java.lang.String)
	 */
	public URL getResource(String ref) {
		return ResourceLoader.getResource(ref);
	}

	/**
	 * @see org.newdawn.slick.thingle.spi.ThinletContext#getResourceAsStream(java.lang.String)
	 */
	public InputStream getResourceAsStream(String ref) {
		return ResourceLoader.getResourceAsStream(ref);
	}

	/**
	 * @see org.newdawn.slick.thingle.spi.ThinletContext#createImage(java.io.InputStream, java.lang.String, boolean)
	 */
	public ThinletImage createImage(InputStream in, String name, boolean flipped)
			throws ThinletException {
		try {
			return new ImageWrapper(new Image(in, name, flipped));
		} catch (SlickException e) {
			throw new ThinletException(e);
		}
	}

	/**
	 * @see org.newdawn.slick.thingle.spi.ThinletContext#createImageBuffer(int, int)
	 */
	public ThinletImageBuffer createImageBuffer(int width, int height) {
		return new ImageBufferWrapper(new ImageBuffer(width, height));
	}

	/**
	 * @see org.newdawn.slick.thingle.spi.ThinletContext#createInput(org.newdawn.slick.thingle.internal.ThinletInputListener)
	 */
	public ThinletInput createInput(ThinletInputListener listener) {
		InputHandler handler = new InputHandler(listener);
		handler.setInput(container.getInput());
		
		return handler;
	}

	/**
	 * @see org.newdawn.slick.thingle.spi.ThinletContext#getGraphics()
	 */
	public ThinletGraphics getGraphics() {
		return new SlickGraphics(container.getGraphics());
	}

	/**
	 * @see org.newdawn.slick.thingle.spi.ThinletContext#getHeight()
	 */
	public int getHeight() {
		return container.getHeight();
	}

	/**
	 * @see org.newdawn.slick.thingle.spi.ThinletContext#getWidth()
	 */
	public int getWidth() {
		return container.getWidth();
	}

	/**
	 * @see org.newdawn.slick.thingle.spi.ThinletContext#createColor(int, int, int, int)
	 */
	public ThinletColor createColor(int red, int green, int blue, int alpha) {
		return new ColorWrapper(red, green, blue, alpha);
	}
}
