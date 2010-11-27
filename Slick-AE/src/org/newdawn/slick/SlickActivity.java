package org.newdawn.slick;

import org.newdawn.slick.opengl.InternalTextureLoader;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.renderer.GDXRenderer;
import org.newdawn.slick.util.ResourceLoader;

import android.util.Log;

import com.badlogic.gdx.backends.android.AndroidApplication;

public class SlickActivity extends AndroidApplication {
	private GDXGameContainer container;
	
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
	    	initialize(container, false);
	    } catch (SlickException e) {
	    	Log.e("SLICK", "Failed to create container", e);
	    }
	}
}