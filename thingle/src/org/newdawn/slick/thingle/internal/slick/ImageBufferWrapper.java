package org.newdawn.slick.thingle.internal.slick;

import org.newdawn.slick.ImageBuffer;
import org.newdawn.slick.thingle.spi.ThinletImage;
import org.newdawn.slick.thingle.spi.ThinletImageBuffer;

public class ImageBufferWrapper implements ThinletImageBuffer {
	private ImageBuffer buffer;
	
	public ImageBufferWrapper(ImageBuffer buffer) {
		this.buffer = buffer;
	}
	
	public ThinletImage getImage() {
		return new ImageWrapper(buffer.getImage());
	}

	public void setRGBA(int x, int y, int r, int g, int b, int a) {
		buffer.setRGBA(x, y, r, g, b, a);
	}
}
