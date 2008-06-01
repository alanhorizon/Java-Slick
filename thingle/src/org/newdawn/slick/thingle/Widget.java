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
	
	/**
	 * Set the icon to be displayed on the component
	 * 
	 * @param iconRef The icon to be displayed on the component
	 */
	public void setIcon(String iconRef) {
		thinlet.setIcon(component, "icon", thinlet.getIcon(iconRef));
	}
	
	/**
	 * Add an item to a list component.
	 * 
	 * @param text The text to display on the component
	 * @return The item component added
	 */
	public Widget addItem(String text) {
		Object handle = Thinlet.create("item");
		Widget item = new Widget(thinlet, handle);
		item.setText(text);

		thinlet.add(component, handle);
		return item;
	}
	
	/**
	 * Remove an item from a component
	 * 
	 * @param item The item to remove
	 */
	public void removeItem(Widget item) {
		thinlet.remove(item);
	}
}
