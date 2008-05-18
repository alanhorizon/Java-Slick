package org.newdawn.slick.thingle.internal;

import org.newdawn.slick.Input;

public class Modifiers {
	public boolean isShiftDown;
	public boolean isPopupTrigger;
	public boolean isControlDown;
	private Input input;
	
	public Modifiers(Input input) {	
		this.input = input;
	}
	
	public void update() {
		isPopupTrigger = input.isMouseButtonDown(2);
		isShiftDown = input.isKeyDown(Input.KEY_LSHIFT) || input.isKeyDown(Input.KEY_RSHIFT);
		isControlDown = input.isKeyDown(Input.KEY_LCONTROL) || input.isKeyDown(Input.KEY_RCONTROL);
	}
}
