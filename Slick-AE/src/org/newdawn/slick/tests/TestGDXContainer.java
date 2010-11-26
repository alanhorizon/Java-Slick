package org.newdawn.slick.tests;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GDXGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.particles.effects.FireEmitter;
import org.newdawn.slick.renderer.GDXRenderer;

public class TestGDXContainer extends BasicGame {
	private Image test;
	private AngelCodeFont font;
	private ParticleSystem system;
	
	public TestGDXContainer() {
		super("GDX Test");
	}

	public void init(GameContainer container) throws SlickException {
		Image image = new Image("testdata/particle.tga", true);
		system = new ParticleSystem(image);
		
		system.addEmitter(new FireEmitter(400,300,45));
		system.addEmitter(new FireEmitter(200,300,60));
		system.addEmitter(new FireEmitter(600,300,30));
		
		test = new Image("testdata/logo.png");
		font = new AngelCodeFont("testdata/demo2.fnt","testdata/demo2_00.tga",false);
	}

	public void update(GameContainer container, int delta)
			throws SlickException {
		system.update(delta);
	}

	public void render(GameContainer container, Graphics g)
			throws SlickException {
		test.draw(100,100);
		font.drawString(100,200,"HELLO");
		system.render();
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
