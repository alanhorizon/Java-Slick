package org.newdawn.slick.thingle;

import org.newdawn.slick.Color;
import org.newdawn.slick.thingle.internal.Thinlet;

public class Theme {
	private Color background = new Color(0xe6e6e6);
	private Color text = new Color(0x000000);
	private Color textbackground = new Color(0xffffff);
	private Color border = new Color(0x909090);
	private Color disable = new Color(0xb0b0b0);
	private Color hover = new Color(0xededed);
	private Color press = new Color(0xb9b9b9);
	private Color focus = new Color(0x89899a);
	private Color select = new Color(0xc5c5dd);

	public Color getBackground() {
		return background;
	}
	
	public void setBackground(Color background) {
		this.background = background;
	}
	
	public Color getText() {
		return text;
	}
	
	public void setText(Color text) {
		this.text = text;
	}
	
	public Color getTextBackground() {
		return textbackground;
	}
	
	public void setTextBackground(Color textbackground) {
		this.textbackground = textbackground;
	}
	
	public Color getBorder() {
		return border;
	}
	
	public void setBorder(Color border) {
		this.border = border;
	}
	
	public Color getDisabled() {
		return disable;
	}
	
	public void setDisabled(Color disable) {
		this.disable = disable;
	}
	
	public Color getHover() {
		return hover;
	}
	
	public void setHover(Color hover) {
		this.hover = hover;
	}
	
	public Color getPressed() {
		return press;
	}
	
	public void setPressed(Color press) {
		this.press = press;
	}
	
	public Color getFocus() {
		return focus;
	}
	
	public void setFocus(Color focus) {
		this.focus = focus;
	}
	
	public Color getSelected() {
		return select;
	}
	
	public void setSelected(Color select) {
		this.select = select;
	}

	private int toInt(Color color) {
		return (color.getAlpha() << 24) + (color.getRed() << 16) + (color.getGreen() << 8) + color.getBlue();
	}
	
	void apply(Thinlet thinlet) {
		thinlet.setColors(toInt(background), toInt(text), toInt(textbackground),
				toInt(border), toInt(disable), toInt(hover), toInt(press), toInt(focus), toInt(select));
	}
}
