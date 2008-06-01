package org.newdawn.slick.thingle.demos;

import org.newdawn.slick.thingle.internal.Thinlet;

/**
 * Login test logic
 * 
 * @author kevin
 */
public class Login {
	/** The dialog */
	public Object dialog;
	/** The thinlet instance being controlled */
	private Thinlet thinlet;
	
	/**
	 * Initialise 
	 * 
	 * @param thinlet The thinlet instance to control
	 */ 
	public void init(Thinlet thinlet) {
		this.thinlet = thinlet;
	}
	
	/**
	 * Login notification
	 * 
	 * @param dialog The dialog being shown
	 * @param username The username given
	 * @param password The password given
	 */
	public void login(Object dialog, String username, String password) {
		this.dialog = dialog;
		
		thinlet.remove(dialog);
		System.out.println("Attempting login with the following criteria: "+username+","+password);
	}
}
