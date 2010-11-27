package org.newdawn.slick;

import org.lwjgl.input.Cursor;
import org.newdawn.slick.opengl.ImageData;
import org.newdawn.slick.opengl.InternalTextureLoader;
import org.newdawn.slick.util.Log;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

/**
 * A container that uses GDX to render everything
 * 
 * @author kevin
 */
public class GDXGameContainer extends GameContainer implements ApplicationListener {
	/** The time of the last update */
	private long lastUpdate;
	/** The application hosting the GDX listener */
	private Application app;
	
	/**
	 * Create a new game container
	 * 
	 * @param game The game that will be hosted 
	 * @param width The width of the game container
	 * @param height The height of the game container
	 * @throws SlickException 
	 */
	public GDXGameContainer(Game game,int width,int height) throws SlickException {
		super(game);

		this.game = game;
		this.width = width;
		this.height = height;
		lastUpdate = System.currentTimeMillis();
	}
	
	/**
	 * Set the android application being used when running on the handset
	 * 
	 * @param android The android application being used when running on the handset
	 */
	void setAndroidApplication(AndroidApplication android) {
		app = android;
	}

	/**
	 * Start the game container as a desktop application
	 */
	public void start() {
		lastUpdate = System.currentTimeMillis();
        app = new LwjglApplication(this, game.getTitle(), width, height, false);
	}

	/*
	 * (non-Javadoc)
	 * @see org.newdawn.slick.GameContainer#getScreenHeight()
	 */
	public int getScreenHeight() {
		return height;
	}

	/*
	 * (non-Javadoc)
	 * @see org.newdawn.slick.GameContainer#getScreenWidth()
	 */
	public int getScreenWidth() {
		return width;
	}

	/**
	 *  Not implemented for android
	 */
	public boolean hasFocus() {
		return true;
	}

	/**
	 *  Not implemented for android
	 */
	public boolean isMouseGrabbed() {
		return false;
	}

	/**
	 *  Not implemented for android
	 */
	public void setDefaultMouseCursor() {
	}

	/**
	 *  Not implemented for android
	 */
	public void setIcon(String ref) throws SlickException {
	}

	/**
	 *  Not implemented for android
	 */
	public void setIcons(String[] refs) throws SlickException {
	}

	/**
	 *  Not implemented for android
	 */
	public void setMouseCursor(String ref, int hotSpotX, int hotSpotY) {
	}

	/**
	 *  Not implemented for android
	 */
	public void setMouseCursor(ImageData data, int hotSpotX, int hotSpotY)
			throws SlickException {
	}

	/**
	 *  Not implemented for android
	 */
	public void setMouseCursor(Image image, int hotSpotX, int hotSpotY)
			throws SlickException {
	}

	/**
	 *  Not implemented for android
	 */
	public void setMouseCursor(Cursor cursor, int hotSpotX, int hotSpotY)
			throws SlickException {
	}

	/*
	 * (non-Javadoc)
	 * @see org.newdawn.slick.GameContainer#setMouseGrabbed(boolean)
	 */
	public void setMouseGrabbed(boolean grabbed) {
	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.ApplicationListener#create()
	 */
	public void create() {
		try {
			initSystem();
			Log.error("Creating application: "+app);
			if (app != null) {
				app.getInput().setInputProcessor(getInput());
			}
			enterOrtho();
			
			game.init(this);
		} catch (SlickException e) {
			Log.error("Failed to init", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.ApplicationListener#dispose()
	 */
	public void dispose() {
	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.ApplicationListener#render()
	 */
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

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.ApplicationListener#resize(int, int)
	 */
	public void resize(int width, int height) {
	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.ApplicationListener#resume()
	 */
	@Override
	public void resume() {
		try {
			initSystem();
			enterOrtho();
			
			InternalTextureLoader.get().reload();
		} catch (SlickException e) {
			Log.error("Failed to init", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.ApplicationListener#pause()
	 */
	@Override
	public void pause() {
	}

}
