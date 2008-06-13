package org.newdawn.slick.thingle.internal.slick;

import org.newdawn.slick.Image;
import org.newdawn.slick.thingle.spi.ThinletImage;

public class ImageWrapper implements ThinletImage {
	private Image image;
	
	public ImageWrapper(Image image) {
		this.image = image;
	}

	public int getHeight() {
		return image.getHeight();
	}

	public int getWidth() {
		return image.getWidth();
	}
	
	public Image getSlickImage() {
		return image;
	}
}
