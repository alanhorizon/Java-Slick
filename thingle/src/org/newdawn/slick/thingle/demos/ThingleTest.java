package org.newdawn.slick.thingle.demos;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.thingle.Page;
import org.newdawn.slick.thingle.Theme;
import org.newdawn.slick.thingle.ThinletCore;
import org.newdawn.slick.thingle.internal.slick.SlickThinletFactory;
import org.newdawn.slick.thingle.spi.ThinletException;

/**
 * The big test demo that has multiple tabs, sub-dialogs etc
 * 
 * @author kevin
 */
public class ThingleTest extends BasicGame {
	/** The UI page being displayed */
	private Page page;
	/** The image to display in the background */
	private Image image;
	
	/**
	 * Create a new test
	 */
	public ThingleTest() {
		super("Thingle Demo");
	}
	
	/**
	 * @see org.newdawn.slick.BasicGame#init(org.newdawn.slick.GameContainer)
	 */
	public void init(GameContainer container) throws SlickException {
		ThinletCore.init(new SlickThinletFactory(container));
		
		container.setShowFPS(false);
		//container.setVSync(true);
		//container.setTargetFrameRate(100);
		
		image = new Image("res/logo.png");
		container.getGraphics().setBackground(Color.white);
		
		try {
			page = new Page("res/demo.xml", new Demo());
		} catch (ThinletException e) {
			e.printStackTrace();
		}
		Theme theme = new Theme();
		theme.setBackground(ThinletCore.createColor(0.6f,0.6f,1f,1f));
		theme.setBorder(ThinletCore.createColor(0,0,0.5f));
		theme.setFocus(ThinletCore.createColor(0,0,0));
		page.setTheme(theme);
		page.setDrawDesktop(true);
		
		page.enable();
	}
	
	public void update(GameContainer container, int delta)
			throws SlickException {
	}

	public void render(GameContainer container, Graphics g)
			throws SlickException {
		image.draw(100,200);
		page.render();
		
		g.setColor(Color.black);
		g.drawString("FPS: "+container.getFPS(), 530, 2);
	}

	/**
	 * Entry point to the demo
	 * 
	 * @param argv The argments passed to the execution
	 */
	public static void main(String[] argv) {
		try {
			AppGameContainer container = new AppGameContainer(new ThingleTest(), 600, 600, false);
			container.start();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
