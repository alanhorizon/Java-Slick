package org.newdawn.slick.thingle;

import org.newdawn.slick.Color;
import org.newdawn.slick.thingle.internal.Thinlet;

/**
 * A colour theme to apply across the GUI
 * 
 * @author kevin
 */
public class Theme {
	/** The background colour - also used as the base of the gradients */
	private Color background = new Color(0xe6e6e6);
	/** The text colour */
	private Color text = new Color(0x000000);
	/** The background of text area's colour */
	private Color textbackground = new Color(0xffffff);
	/** The border of components colour */
	private Color border = new Color(0x909090);
	/** The disabled colour */
	private Color disable = new Color(0xb0b0b0);
	/** The colour used when the mouse is hovering over a component */
	private Color hover = new Color(0xededed);
	/** The colour used when a button is pressed */
	private Color press = new Color(0xb9b9b9);
	/** The colour of the focus outline */
	private Color focus = new Color(0x89899a);
	/** The colour of the selection over text */
	private Color select = new Color(0xc5c5dd);

	/**
	 * Get the background colour
	 * 
	 * @return The background colour
	 */
	public Color getBackground() {
		return background;
	}
	
	/**
	 * Set the background colour
	 * 
	 * @param background The background colour
	 */
	public void setBackground(Color background) {
		this.background = background;
	}

	/**
	 * Get the text colour
	 * 
	 * @return The text colour
	 */
	public Color getText() {
		return text;
	}

	/**
	 * Set the txt colour
	 * 
	 * @param text The text colour
	 */
	public void setText(Color text) {
		this.text = text;
	}

	/**
	 * Get the text background colour
	 * 
	 * @return The text background colour
	 */
	public Color getTextBackground() {
		return textbackground;
	}

	/**
	 * Set the text background colour
	 * 
	 * @param textbackground The text background colour
	 */
	public void setTextBackground(Color textbackground) {
		this.textbackground = textbackground;
	}

	/**
	 * Get the border colour
	 * 
	 * @return The border colour
	 */
	public Color getBorder() {
		return border;
	}

	/**
	 * Set the border colour
	 * 
	 * @param border The border colour
	 */
	public void setBorder(Color border) {
		this.border = border;
	}

	/**
	 * Get the disabled colour
	 * 
	 * @return The disabled colour
	 */
	public Color getDisabled() {
		return disable;
	}

	/**
	 * Set the disabled colour
	 * 
	 * @param disable The disabled colour
	 */
	public void setDisabled(Color disable) {
		this.disable = disable;
	}

	/**
	 * Get the hover colour
	 * 
	 * @return The hover colour
	 */
	public Color getHover() {
		return hover;
	}

	/**
	 * Set the hover colour
	 * 
	 * @param hover The hover colour
	 */
	public void setHover(Color hover) {
		this.hover = hover;
	}

	/**
	 * Get the pressed colour
	 * 
	 * @return The pressed colour
	 */
	public Color getPressed() {
		return press;
	}

	/**
	 * Set the pressed colour
	 * 
	 * @param press The pressed colour
	 */
	public void setPressed(Color press) {
		this.press = press;
	}

	/**
	 * Get the focus colour
	 * 
	 * @return The focus colour
	 */
	public Color getFocus() {
		return focus;
	}

	/**
	 * Set the focused colour
	 * 
	 * @param focus The focused colour
	 */
	public void setFocus(Color focus) {
		this.focus = focus;
	}

	/**
	 * Get the selected colour
	 * 
	 * @return The selected colour
	 */
	public Color getSelected() {
		return select;
	}

	/**
	 * Set the selected colour
	 * 
	 * @param select The selected colour
	 */
	public void setSelected(Color select) {
		this.select = select;
	}

	/**
	 * Utility to convert the colour to an int form
	 * 
	 * @param color The colour to convert
	 * @return The int form of the colour
	 */
	private int toInt(Color color) {
		return (color.getAlpha() << 24) + (color.getRed() << 16) + (color.getGreen() << 8) + color.getBlue();
	}
	
	/**
	 * Apply a theme to a thinlet instance
	 * 
	 * @param thinlet The thinlet instance to apply the theme to
	 */
	void apply(Thinlet thinlet) {
		thinlet.setColors(toInt(background), toInt(text), toInt(textbackground),
				toInt(border), toInt(disable), toInt(hover), toInt(press), toInt(focus), toInt(select));
	}
}
