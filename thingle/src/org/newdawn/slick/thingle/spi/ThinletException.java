package org.newdawn.slick.thingle.spi;

/**
 * A wrapper exception to keep implementation seperated from 
 * framework.
 * 
 * @author kevin
 */
public class ThinletException extends Exception {

	/**
	 * Create a new exception
	 * 
	 * @param msg The detail message
	 */
	public ThinletException(String msg) {
		super(msg);
	}

	/**
	 * Create a new exception
	 * 
	 * @param cause The exception causing this exception to be thrown
	 */
	public ThinletException(Throwable cause) {
		super(cause);
	}

	/**
	 * Create a new exception
	 * 
	 * @param msg The detail message
	 * @param cause The exception causing this exception to be thrown
	 */
	public ThinletException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
