package org.newdawn.slick.thingle.demos;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.thingle.WidgetRenderer;
import org.newdawn.slick.thingle.internal.Rectangle;
import org.newdawn.slick.thingle.internal.Thinlet;
import org.newdawn.slick.thingle.internal.slick.SlickGraphics;
import org.newdawn.slick.thingle.spi.ThingleGraphics;

/**
 * A widget renderer to test custom panel rendering
 * 
 * @author kevin
 */
public class TestWidgetRenderer implements WidgetRenderer {
	
	/**
	 * @see org.newdawn.slick.thingle.WidgetRenderer#paint(org.newdawn.slick.thingle.internal.Thinlet, org.newdawn.slick.thingle.spi.ThingleGraphics, java.lang.Object, org.newdawn.slick.thingle.internal.Rectangle)
	 */
	public void paint(Thinlet thinlet, ThingleGraphics tg,
			Object componentHandle, Rectangle bounds) {
		float ang = (System.currentTimeMillis() / 10) % 360;
		
		Graphics g = ((SlickGraphics) tg).getGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, bounds.width, bounds.height);
		
		g.translate(bounds.width / 2, bounds.height / 2);
		g.rotate(0, 0, ang);
		g.setColor(Color.yellow);
		g.drawString("Anim Test", 0, 0);
		g.rotate(0, 0, -ang);
		g.translate(-bounds.width / 2, -bounds.height / 2);
	}

}
