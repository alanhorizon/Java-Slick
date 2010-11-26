package org.newdawn.slick;

import org.lwjgl.input.Cursor;
import org.newdawn.slick.opengl.ImageData;
import org.newdawn.slick.util.Log;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class GDXGameContainer extends GameContainer implements ApplicationListener {
	private LwjglApplication app;
	private long lastUpdate;
	
	public GDXGameContainer(Game game,int width,int height) throws SlickException {
		super(game);

		this.game = game;
		this.width = width;
		this.height = height;
		lastUpdate = System.currentTimeMillis();
	}
	
	public void start() {
		lastUpdate = System.currentTimeMillis();
        app = new LwjglApplication(this, game.getTitle(), width, height, false);
	}

	public int getScreenHeight() {
		return height;
	}

	public int getScreenWidth() {
		return width;
	}

	public boolean hasFocus() {
		return true;
	}

	public boolean isMouseGrabbed() {
		return false;
	}

	public void setDefaultMouseCursor() {
	}

	public void setIcon(String ref) throws SlickException {
	}

	public void setIcons(String[] refs) throws SlickException {
	}

	public void setMouseCursor(String ref, int hotSpotX, int hotSpotY)
			throws SlickException {
	}

	public void setMouseCursor(ImageData data, int hotSpotX, int hotSpotY)
			throws SlickException {
	}

	public void setMouseCursor(Image image, int hotSpotX, int hotSpotY)
			throws SlickException {
	}

	public void setMouseCursor(Cursor cursor, int hotSpotX, int hotSpotY)
			throws SlickException {
	}

	public void setMouseGrabbed(boolean grabbed) {
	}

	public void create() {
		try {
			initSystem();
			enterOrtho();
			
			game.init(this);
		} catch (SlickException e) {
			Log.error("Failed to init", e);
		}
	}

	public void dispose() {
	}

	public void render() {
		int delta = (int) (System.currentTimeMillis() - lastUpdate);
		lastUpdate = System.currentTimeMillis();
		
		try {
			updateAndRender(delta);
			updateFPS();
		} catch (SlickException e) {
			Log.error("Failed to render/update", e);
			throw new RuntimeException(e);
		}
	}

	public void resize(int width, int height) {
	}

}
