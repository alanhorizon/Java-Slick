package org.newdawn.slick.thingle;

import java.io.IOException;
import java.util.HashMap;

import org.newdawn.slick.thingle.internal.Skinlet;
import org.newdawn.slick.thingle.internal.Thinlet;
import org.newdawn.slick.thingle.spi.ThingleException;

/**
 * A single UI page. This page provides a Slick oriented wrapper around the 
 * thinlet API.
 * 
 * @author kevin
 */
public class Page {
	/** The thinlet instance in use */
	private Skinlet thinlet;
	/** The colour theme applied */
	private Theme theme;
	
	/** The cache of widgets from this page */
	private HashMap widgets = new HashMap();
	
	/**
	 * Create new a new UI page
	 * 
	 * @throws ThingleException Indicates a failure to create thinlet
	 */
	public Page() throws ThingleException {
		thinlet = new Skinlet();
		thinlet.setKeyFocus(true);
		theme = new Theme();
		setColors();
	}

	/**
	 * Create a new UI page
	 * 
	 * @param ref A reference to a thinlet xml file to describe the UI
	 * @throws ThingleException Indicates a failure to create thinlet
	 */
	public Page(String ref) throws ThingleException {
		this();
		
		addComponents(ref);
	}

	/**
	 * Create a new UI page
	 * 
	 * @param ref A reference to a thinlet xml file to describe the UI
	 * @param handler The object to respond to events from the GUI specified in the ref XML
	 * @throws ThingleException Indicates a failure to create thinlet
	 */
	public Page(String ref, Object handler) throws ThingleException {
		this();
		
		addComponents(ref, handler);
	}
	
	/**
	 * Load a skin from a configuration file assumed to be on the class path
	 * 
	 * @param name The name of the configuration file (without it's extension)
	 * @throws ThingleException Indicates a failure to load the given skin
	 */
	public void loadSkin(String name) throws ThingleException {
		thinlet.loadSkin("skins", name);
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
  	 * @throws ThingleException Indicates a failure to load the XML
	 */
	public void addComponents(String ref) throws ThingleException {
		addComponents(ref, this);
	}

	/**
	 * Add the components specified in the referenced XML file. The action
	 * events are sent to the action handler specified.
	 * 
	 * @param actionHandler The handler to send events to
	 * @param ref The reference to the XML file
  	 * @throws ThingleException Indicates a failure to load the XML
	 */
	public void addComponents(String ref, Object actionHandler) throws ThingleException {
		try {
			thinlet.add(thinlet.parse(Thingle.getContext().getResourceAsStream(ref), actionHandler));
		} catch (IOException e) {
			Thingle.getContext().log(e);
			throw new ThingleException("Failed to load: "+ref, e);
		}
		
		layout();
	}
	
	/**
	 * Render the page to the given graphics context
	 * 
	 * @param g The graphics context to render to
	 */
	public void render() {
		Thingle.doPreRender();
		thinlet.paint(Thingle.getGraphics(), Thingle.getWidth(), Thingle.getHeight());	
		Thingle.doPostRender();
	}
	
	/**
	 * Layout the GUI
	 */
	public void layout() {
		thinlet.layout(Thingle.getWidth(), Thingle.getHeight());
	}
	
	/**
	 * Update the input handling for the page
	 * 
	 * @param delta The amount of time in milliseconds thats passed since last update
	 */
	public void update(int delta) {
		thinlet.getInput().update(delta);
	}
	
	/**
	 * Enable input to this GUI page
	 */
	public void enable() {
		thinlet.getInput().enable();
	}

	/**
	 * Disabl input to this GUI page
	 */
	public void disable() {
		thinlet.getInput().disable();
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
