package org.newdawn.slick.thingle.demos;

import org.newdawn.slick.thingle.ActionHandler;

/**
 * Login test logic
 * 
 * @author kevin
 */
public class Login extends ActionHandler {
	/** The dialog */
	public Object dialog;
	
	/**
	 * Login notification
	 * 
	 * @param dialog The dialog being shown
	 * @param username The username given
	 * @param password The password given
	 */
	public void login(Object dialog, String username, String password) {
		this.dialog = dialog;
		
		remove(dialog);
		System.out.println("Attempting login with the following criteria: "+username+","+password);
	}
}
