package org.newdawn.slick.thingle.internal.slick;

import org.newdawn.slick.ImageBuffer;
import org.newdawn.slick.thingle.spi.ThinletImage;
import org.newdawn.slick.thingle.spi.ThinletImageBuffer;

/**
 * A wrapped round Slick image buffers to support the graident creation
 * for Thinlet
 * 
 * @author kevin
 */
public class ImageBufferWrapper implements ThinletImageBuffer {
	/** The buffer wrapped */
	private ImageBuffer buffer;
	
	/**
	 * Create a new wrapper round a slick image buffer
	 * 
	 * @param buffer The buffer to be wrapped
	 */
	public ImageBufferWrapper(ImageBuffer buffer) {
		this.buffer = buffer;
	}
	
	/**
	 * @see org.newdawn.slick.thingle.spi.ThinletImageBuffer#getImage()
	 */
	public ThinletImage getImage() {
		return new ImageWrapper(buffer.getImage());
	}

	/**
	 * @see org.newdawn.slick.thingle.spi.ThinletImageBuffer#setRGBA(int, int, int, int, int, int)
	 */
	public void setRGBA(int x, int y, int r, int g, int b, int a) {
		buffer.setRGBA(x, y, r, g, b, a);
	}
}
