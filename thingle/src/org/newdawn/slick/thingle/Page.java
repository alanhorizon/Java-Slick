package org.newdawn.slick.thingle;

import java.io.IOException;
import java.util.HashMap;

import org.newdawn.slick.Color;
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
public class Page {
	/** The game container the page is rendering in */
	private GameContainer container;
	/** The thinlet instance in use */
	private Thinlet thinlet;
	/** The input handler translating slick events into thinlet */
	private InputHandler inputHandler;
	/** The colour theme applied */
	private Theme theme;
	
	/** The cache of widgets from this page */
	private HashMap widgets = new HashMap();
	
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
	public Page(GameContainer container, String ref, Object handler) throws SlickException {
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
	public void addComponents(String ref, Object actionHandler) throws SlickException {
		try {
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
		Color col = g.getColor();
		SlickCallable.enterSafeBlock();
		thinlet.paint(new SlickBinding(g), container.getWidth(), container.getHeight());	
		SlickCallable.leaveSafeBlock();
		g.setFont(font);
		g.setColor(col);
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
	
	/**
	 * Get a widget in the page
	 * 
	 * @param name The name of the widget to retrieve
 	 * @return The widget or null if no widget by that name could be found
	 */
	public Widget getWidget(String name) {
		Widget ret = (Widget) widgets.get(name);
		if (ret == null) {
			Object desktop = thinlet.getDesktop();
			Object component = thinlet.find(desktop, name);
		
			if (component == null) {
				return null;
			}
			
			ret = new Widget(thinlet, component);
		}
		
		return ret;
	}
}
