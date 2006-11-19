package org.newdawn.slick.gui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.InputListener;

/**
 * The utility class to handle all the input related gubbins
 * for basic GUI components
 *
 * @author kevin
 */
public abstract class BasicComponent implements InputListener {
	/** The input sending the events this component handles */
	protected Input input;
	/** True if the component is current acceptingInput */
	private boolean acceptingInput;
	/** The game container */
	protected GameContainer container;
	
	/**
	 * Create a new component
	 * 
	 * @param container The container displaying this component
	 */
	public BasicComponent(GameContainer container) {
		this.container = container;
		
		input = container.getInput();
		input.addPrimaryListener(this);
	}

	/**
	 * @see org.newdawn.slick.InputListener#isAcceptingInput()
	 */
	public boolean isAcceptingInput() {
		return acceptingInput;
	}
	
	/**
	 * Render this component to the screen
	 *
	 * @param container The container displaying this component
	 * @param g The graphics context used to render to the display
	 */
	public final void render(GameContainer container, Graphics g) {
		acceptingInput = true;
		renderImpl(container, g);
	}

	/**
	 * Render this component to the screen
	 *
	 * @param container The container displaying this component
	 * @param g The graphics context used to render to the display
	 */
	public abstract void renderImpl(GameContainer container, Graphics g);
	
	/**
	 * Indicate that this component has consumed the last reported event
	 */
	protected void consumeEvent() {
		input.consumeEvent();
	}
	
	/**
	 * @see org.newdawn.slick.InputListener#controllerButtonPressed(int, int)
	 */
	public void controllerButtonPressed(int controller, int button) {
	}

	/**
	 * @see org.newdawn.slick.InputListener#controllerButtonReleased(int, int)
	 */
	public void controllerButtonReleased(int controller, int button) {
	}

	/**
	 * @see org.newdawn.slick.InputListener#controllerDownPressed(int)
	 */
	public void controllerDownPressed(int controller) {
	}

	/**
	 * @see org.newdawn.slick.InputListener#controllerDownReleased(int)
	 */
	public void controllerDownReleased(int controller) {
	}

	/**
	 * @see org.newdawn.slick.InputListener#controllerLeftPressed(int)
	 */
	public void controllerLeftPressed(int controller) {
	}

	/**
	 * @see org.newdawn.slick.InputListener#controllerLeftReleased(int)
	 */
	public void controllerLeftReleased(int controller) {
	}

	/**
	 * @see org.newdawn.slick.InputListener#controllerRightPressed(int)
	 */
	public void controllerRightPressed(int controller) {
	}

	/**
	 * @see org.newdawn.slick.InputListener#controllerRightReleased(int)
	 */
	public void controllerRightReleased(int controller) {
	}

	/**
	 * @see org.newdawn.slick.InputListener#controllerUpPressed(int)
	 */
	public void controllerUpPressed(int controller) {
	}

	/**
	 * @see org.newdawn.slick.InputListener#controllerUpReleased(int)
	 */
	public void controllerUpReleased(int controller) {
	}

	/**
	 * @see org.newdawn.slick.InputListener#keyPressed(int, char)
	 */
	public void keyPressed(int key, char c) {
	}

	/**
	 * @see org.newdawn.slick.InputListener#keyReleased(int, char)
	 */
	public void keyReleased(int key, char c) {
	}

	/**
	 * @see org.newdawn.slick.InputListener#mouseMoved(int, int, int, int)
	 */
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
	}

	/**
	 * @see org.newdawn.slick.InputListener#mousePressed(int, int, int)
	 */
	public void mousePressed(int button, int x, int y) {
	}

	/**
	 * @see org.newdawn.slick.InputListener#mouseReleased(int, int, int)
	 */
	public void mouseReleased(int button, int x, int y) {
	}

	/**
	 * @see org.newdawn.slick.InputListener#setInput(org.newdawn.slick.Input)
	 */
	public void setInput(Input input) {
		this.input = input;
	}
	
	/**
	 * @see org.newdawn.slick.InputListener#inputEnded()
	 */
	public void inputEnded() {
		acceptingInput = false;
	}
	
	/**
	 * @see org.newdawn.slick.InputListener#mouseWheelMoved(int)
	 */
	public void mouseWheelMoved(int newValue) {
	}

}
