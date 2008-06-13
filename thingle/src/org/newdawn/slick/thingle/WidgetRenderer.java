package org.newdawn.slick.thingle;

import org.newdawn.slick.thingle.internal.Rectangle;
import org.newdawn.slick.thingle.internal.Thinlet;
import org.newdawn.slick.thingle.spi.ThinletGraphics;

/**
 * A plugin to allow custom rendering to components, currently only panels
 * 
 * @author kevin
 */
public interface WidgetRenderer {

	/**
	 * Render the contents of the widget
	 * 
	 * @param thinlet The thinlet instance we're rendering
	 * @param g The graphics context to render to 
	 * @param componentHandle The handle to the component being rendered
	 * @param bounds The bounds of the component
	 */
	public void paint(Thinlet thinlet, ThinletGraphics g, Object componentHandle, Rectangle bounds);
}
