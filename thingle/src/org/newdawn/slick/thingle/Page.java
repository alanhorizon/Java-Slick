package org.newdawn.slick.thingle;

import java.io.IOException;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.thingle.internal.InputHandler;
import org.newdawn.slick.thingle.internal.Thinlet;
import org.newdawn.slick.thingle.internal.slick.SlickBinding;
import org.newdawn.slick.util.Log;
import org.newdawn.slick.util.ResourceLoader;

public class Page extends ActionHandler {
	private GameContainer container;
	private Thinlet thinlet;
	private InputHandler inputHandler;
	private Theme theme;
	
	public Page(GameContainer container) throws SlickException {
		this.container = container;
		
		container.getGraphics().setBackground(Color.white);
		container.getInput().setDoubleClickInterval(50);
		
		thinlet = new Thinlet();
		theme = new Theme();
		setColors();
		inputHandler = new InputHandler(thinlet);
		inputHandler.setInput(container.getInput());
	}

	public Page(GameContainer container, String ref) throws SlickException {
		this(container);
		
		addComponents(ref);
	}

	public Page(GameContainer container, String ref, ActionHandler handler) throws SlickException {
		this(container);
		
		addComponents(ref, handler);
	}
	
	public Thinlet getThinlet() {
		return thinlet;
	}
	
	public void setTheme(Theme theme) {
		theme.apply(thinlet);
	}
	
	private void setColors() {
		theme.apply(thinlet);
	}
	
	public void addComponents(String ref) throws SlickException {
		addComponents(ref, this);
	}
	
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
	
	public void render(Graphics g) {
		thinlet.paint(new SlickBinding(g), container.getWidth(), container.getHeight());	
	}
	
	public void layout() {
		thinlet.layout(container.getWidth(), container.getHeight());
	}
	
	public void enable() {
		container.getInput().addListener(inputHandler);
	}
	
	public void disable() {
		container.getInput().removeListener(inputHandler);
	}
}
