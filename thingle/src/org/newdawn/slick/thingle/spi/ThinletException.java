package org.newdawn.slick.thingle.spi;

public class ThinletException extends Exception {

	public ThinletException(String msg) {
		super(msg);
	}
	
	public ThinletException(Throwable cause) {
		super(cause);
	}
	
	public ThinletException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
