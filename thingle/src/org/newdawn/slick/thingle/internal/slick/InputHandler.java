package org.newdawn.slick.thingle.internal.slick;

import org.newdawn.slick.Input;
import org.newdawn.slick.thingle.internal.Thinlet;
import org.newdawn.slick.util.InputAdapter;

/** 
 * The input handler responsible for translating from Slick input events
 * to the thinlet event model
 * 
 * @author kevin
 */
public class InputHandler extends InputAdapter {
	/** The thinlet instance events should be sent to */
	private Thinlet thinlet;
	/** The current modifiers state (alt, shift etc) */
	private Modifiers mods;
	/** The input we're listening to */
	private Input input;
	
	/**
	 * Create a new handler that maps between slick input and thinlet
	 * 
	 * @param thinlet The thinlet instance we're sending events to
	 */
	public InputHandler(Thinlet thinlet) {
		this.thinlet = thinlet;
	}

	/**
	 * @see org.newdawn.slick.util.InputAdapter#mouseClicked(int, int, int, int)
	 */
	public void mouseClicked(int button, int x, int y, int clickCount) {
		if (clickCount == 2) {
			mods.update();
			mouseMoved(x,y,x,y);
			thinlet.mousePressed(x, y, 2, mods);
		}
	}

	/**
	 * @see org.newdawn.slick.util.InputAdapter#mouseMoved(int, int, int, int)
	 */
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		mods.update();
		if (input.isMouseButtonDown(0)) {
			thinlet.mouseDragged(newx, newy, mods);
		} else {
			thinlet.mouseMoved(newx, newy, mods);
		}
	}

	/**
	 * @see org.newdawn.slick.util.InputAdapter#mousePressed(int, int, int)
	 */
	public void mousePressed(int button, int x, int y) {
		mods.update();
		mouseMoved(x,y,x,y);
		thinlet.mousePressed(x, y, 1, mods);
	}

	/**
	 * @see org.newdawn.slick.util.InputAdapter#mouseReleased(int, int, int)
	 */
	public void mouseReleased(int button, int x, int y) {
		mods.update();
		mouseMoved(x,y,x,y);
		thinlet.mouseReleased(x, y, mods);
	}

	/**
	 * @see org.newdawn.slick.util.InputAdapter#mouseWheelMoved(int)
	 */
	public void mouseWheelMoved(int change) {
	}

	/**
	 * @see org.newdawn.slick.util.InputAdapter#setInput(org.newdawn.slick.Input)
	 */
	public void setInput(Input input) {
		this.input = input;
		mods = new Modifiers(input);
	}
	
	/**
	 * @see org.newdawn.slick.util.InputAdapter#keyPressed(int, char)
	 */
	public void keyPressed(int key, char c) {
		mods.update();
		thinlet.keyPressed(c, key, mods, false);
	}

	/**
	 * @see org.newdawn.slick.util.InputAdapter#keyReleased(int, char)
	 */
	public void keyReleased(int key, char c) {
		super.keyReleased(key, c);
		thinlet.keyPressed(c, key, mods, true);
	}
	
}
