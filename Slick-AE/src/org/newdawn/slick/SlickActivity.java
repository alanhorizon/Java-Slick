package org.newdawn.slick;

import org.newdawn.slick.opengl.InternalTextureLoader;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.renderer.GDXRenderer;
import org.newdawn.slick.util.ResourceLoader;

import android.util.Log;

import com.badlogic.gdx.backends.android.AndroidApplication;

/**
 * An activity extended to host a Slick Game in an android application
 * 
 * @author kevin
 */
public class SlickActivity extends AndroidApplication {
	private GDXGameContainer container;
	
	/**
	 * Start the game. Should be called from onCreate.
	 * 
	 * @param game The game to be hosted
	 * @param width The width to use for the game
	 * @param height The height to use for the game
	 */
	public void start(Game game, int width, int height) {
	    try {
	    	org.newdawn.slick.util.Log.setLogSystem(new AndroidLogSystem());
	    	org.newdawn.slick.util.Log.error("Slick-AE 0");

			Renderer.setRenderer(new GDXRenderer());
			Renderer.setLineStripRenderer(Renderer.QUAD_BASED_LINE_STRIP_RENDERER);
			InternalTextureLoader.get().setHoldTextureData(true);
			
			ResourceLoader.removeAllResourceLocations();
			ResourceLoader.addResourceLocation(new AndroidResourceLocation(getAssets()));
			
	    	container = new GDXGameContainer(game, width, height); 
	    	container.setAndroidApplication(this);
	    	initialize(container, false);
	    } catch (SlickException e) {
	    	Log.e("SLICK", "Failed to create container", e);
	    }
	}
}