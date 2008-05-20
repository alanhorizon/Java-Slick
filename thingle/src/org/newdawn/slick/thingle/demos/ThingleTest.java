package org.newdawn.slick.thingle.demos;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.thingle.Page;
import org.newdawn.slick.thingle.Theme;

public class ThingleTest extends BasicGame {
	private Page page;
	
	public ThingleTest() {
		super("Thingle");
	}
	
	public void init(GameContainer container) throws SlickException {
		container.setShowFPS(false);
		container.setVSync(true);
		
		page = new Page(container, "res/demo.xml", new Demo());
		Theme theme = new Theme();
		theme.setBackground(new Color(0.6f,0.6f,1f,1f));
		theme.setBorder(new Color(0,0,0.5f));
		theme.setFocus(new Color(0,0,0));
		page.setTheme(theme);
		
		page.enable();
	}
	
	public void update(GameContainer container, int delta)
			throws SlickException {
	}

	public void render(GameContainer container, Graphics g)
			throws SlickException {
		page.render(g);
	}

	public static void main(String[] argv) {
		try {
			AppGameContainer container = new AppGameContainer(new ThingleTest(), 600, 600, false);
			container.start();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
