package org.newdawn.slick.thingle;

import org.newdawn.slick.thingle.internal.slick.SlickThinletFactory;
import org.newdawn.slick.thingle.spi.ThinletColor;
import org.newdawn.slick.thingle.spi.ThinletFactory;
import org.newdawn.slick.thingle.spi.ThinletUtil;

public class ThinletCore {
	private static ThinletFactory factory = new SlickThinletFactory();
	private static ThinletUtil util;
	
	public static void init(ThinletFactory f) {
		factory = f;
		util = null;
	}
	
	public static ThinletFactory getFactory() {
		return factory;
	}
	
	public static ThinletUtil getUtil() {
		if (util == null) {
			util = factory.createUtil();
		}
		
		return util;
	}
	
	public static ThinletColor createColor(int col) {
		return factory.createColor(col);
	}
	
	public static ThinletColor createColor(int red, int green, int blue) {
		return factory.createColor(red,green,blue);
	}
}
