package org.newdawn.slick.thingle;

import java.io.IOException;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.SlickCallable;
import org.newdawn.slick.thingle.internal.Thinlet;
import org.newdawn.slick.thingle.internal.slick.InputHandler;
import org.newdawn.slick.thingle.internal.slick.SlickBinding;
import org.newdawn.slick.util.Log;
import org.newdawn.slick.util.ResourceLoader;

/**
 * A single UI page. This page provides a Slick oriented wrapper around the 
 * thinlet API.
 * 
 * @author kevin
 */
public class Page extends ActionHandler {
	/** The game container the page is rendering in */
	private GameContainer container;
	/** The thinlet instance in use */
	private Thinlet thinlet;
	/** The input handler translating slick events into thinlet */
	private InputHandler inputHandler;
	/** The colour theme applied */
	private Theme theme;
	
	/**
	 * Create new a new UI page
	 * 
	 * @param container The container holding this page
	 * @throws SlickException Indicates a failure to create thinlet
	 */
	public Page(GameContainer container) throws SlickException {
		this.container = container;
		
		container.getInput().setDoubleClickInterval(250);
		
		thinlet = new Thinlet();
		thinlet.setKeyFocus(true);
		theme = new Theme();
		setColors();
		inputHandler = new InputHandler(thinlet);
		inputHandler.setInput(container.getInput());
	}

	/**
	 * Create a new UI page
	 * 
	 * @param ref A reference to a thinlet xml file to describe the UI
	 * @param container The container holding this page
	 * @throws SlickException Indicates a failure to create thinlet
	 */
	public Page(GameContainer container, String ref) throws SlickException {
		this(container);
		
		addComponents(ref);
	}

	/**
	 * Create a new UI page
	 * 
	 * @param ref A reference to a thinlet xml file to describe the UI
	 * @param container The container holding this page
	 * @param handler The object to respond to events from the GUI specified in the ref XML
	 * @throws SlickException Indicates a failure to create thinlet
	 */
	public Page(GameContainer container, String ref, ActionHandler handler) throws SlickException {
		this(container);
		
		addComponents(ref, handler);
	}
	
	/**
	 * Indicate if the background desktop should be drawn 
	 * 
	 * @param drawDesktop True if the background should be drawn
	 */
	public void setDrawDesktop(boolean drawDesktop) {
		thinlet.setDrawDesktop(drawDesktop);
	}
	
	/**
	 * Get the thinlet in use
	 * 
	 * @return The thinlet instance in use
	 */
	public Thinlet getThinlet() {
		return thinlet;
	}
	
	/**
	 * Set the colour theme to apply
	 * 
	 * @param theme The colour theme to apply
	 */
	public void setTheme(Theme theme) {
		theme.apply(thinlet);
	}
	
	/**
	 * Utility to apply the colour scheme
	 */
	private void setColors() {
		theme.apply(thinlet);
	}
	
	/**
	 * Add the components specified in the referenced XML file. The action
	 * events are sent to this page.
	 * 
	 * @param ref The reference to the XML file
  	 * @throws SlickException Indicates a failure to load the XML
	 */
	public void addComponents(String ref) throws SlickException {
		addComponents(ref, this);
	}

	/**
	 * Add the components specified in the referenced XML file. The action
	 * events are sent to the action handler specified.
	 * 
	 * @param actionHandler The handler to send events to
	 * @param ref The reference to the XML file
  	 * @throws SlickException Indicates a failure to load the XML
	 */
	public void addComponents(String ref, ActionHandler actionHandler) throws SlickException {
		try {
			actionHandler.setThinlet(thinlet);
			thinlet.add(thinlet.parse(ResourceLoader.getResourceAsStream(ref), actionHandler));
		} catch (IOException e) {
			Log.error(e);
			throw new SlickException("Failed to load: "+ref, e);
		}
		
		layout();
	}
	
	/**
	 * Render the page to the given graphics context
	 * 
	 * @param g The graphics context to render to
	 */
	public void render(Graphics g) {
		Font font = g.getFont();
		SlickCallable.enterSafeBlock();
		thinlet.paint(new SlickBinding(g), container.getWidth(), container.getHeight());	
		SlickCallable.leaveSafeBlock();
		g.setFont(font);
	}
	
	/**
	 * Layout the GUI
	 */
	public void layout() {
		thinlet.layout(container.getWidth(), container.getHeight());
	}
	
	/**
	 * Enable input to this GUI page
	 */
	public void enable() {
		container.getInput().addListener(inputHandler);
	}

	/**
	 * Disabl input to this GUI page
	 */
	public void disable() {
		container.getInput().removeListener(inputHandler);
		container.getInput().clearKeyPressedRecord();
	}
}
