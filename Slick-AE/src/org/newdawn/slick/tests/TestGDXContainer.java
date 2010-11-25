package org.newdawn.slick.tests;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GDXGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.renderer.GDXRenderer;

public class TestGDXContainer extends BasicGame {
	private Image test;
	private AngelCodeFont font;
	
	public TestGDXContainer() {
		super("GDX Test");
	}

	public void init(GameContainer container) throws SlickException {
		test = new Image("testdata/logo.png");
		font = new AngelCodeFont("testdata/demo2.fnt","testdata/demo2_00.tga",false);
		System.out.println("LOAD IMAGE COMPLETE");
	}

	public void update(GameContainer container, int delta)
			throws SlickException {
	}

	public void render(GameContainer container, Graphics g)
			throws SlickException {
		test.draw(100,100);
		font.drawString(100,200,"HELLO");
	}

	public static void main(String[] argv) {
		try {
			Renderer.setRenderer(new GDXRenderer());
			GDXGameContainer container = new GDXGameContainer(new TestGDXContainer(), 800, 480);
			container.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
