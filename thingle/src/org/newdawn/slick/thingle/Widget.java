package org.newdawn.slick.thingle;

import org.newdawn.slick.thingle.internal.Thinlet;

/**
 * A wrapper around the thinlet representation of components
 * 
 * @author kevin
 */
public class Widget {
	/** The thinlet instance being configured */
	private Thinlet thinlet;
	/** The component handle */
	private Object component;
	
	/**
	 * Create a new widget
	 * 
	 * @param thinlet The thinlet instance to be configured
	 * @param component The component handler
	 */
	Widget(Thinlet thinlet, Object component) {
		this.thinlet = thinlet;
		this.component = component;
	}
	
	/**
	 * Get the name of the widget
	 * 
	 * @return The name of the widget
	 */
	public String getName() {
		return thinlet.getString(component, "name");
	}
	
	/**
	 * Get the name of the type of component
	 * 
	 * @return The name of the type of component
	 */
	public String getWidgetClass() {
		return Thinlet.getClass(component);
	}
	
	/**
	 * Set the text displayed in this component. This is only applicable
	 * to components that display text
	 * 
	 * @param text The text to display
	 */
	public void setText(String text) {
		thinlet.setString(component, "text", text);
	}
}
