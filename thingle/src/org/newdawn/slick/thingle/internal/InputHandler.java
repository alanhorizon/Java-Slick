package org.newdawn.slick.thingle.internal;

import org.newdawn.slick.Input;
import org.newdawn.slick.util.InputAdapter;

public class InputHandler extends InputAdapter {
	private Thinlet thinlet;
	private Modifiers mods;
	private Input input;
	
	public InputHandler(Thinlet thinlet) {
		this.thinlet = thinlet;
	}

	/**
	 * @see org.newdawn.slick.util.InputAdapter#mouseClicked(int, int, int, int)
	 */
	public void mouseClicked(int button, int x, int y, int clickCount) {
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
		thinlet.mousePressed(x, y, 1, mods);
	}

	/**
	 * @see org.newdawn.slick.util.InputAdapter#mouseReleased(int, int, int)
	 */
	public void mouseReleased(int button, int x, int y) {
		mods.update();
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
	
}
