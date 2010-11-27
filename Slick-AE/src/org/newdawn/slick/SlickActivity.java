package org.newdawn.slick;

import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.renderer.GDXRenderer;
import org.newdawn.slick.util.ResourceLoader;

import android.util.Log;

import com.badlogic.gdx.backends.android.AndroidApplication;

public class SlickActivity extends AndroidApplication {

    public void start(Game game, int width, int height) {
	    try {
			Renderer.setRenderer(new GDXRenderer());
			Renderer.setLineStripRenderer(Renderer.QUAD_BASED_LINE_STRIP_RENDERER);
			
			ResourceLoader.removeAllResourceLocations();
			ResourceLoader.addResourceLocation(new AndroidResourceLocation(getAssets()));
	    	GDXGameContainer container = new GDXGameContainer(game, width, height);        
	    	initialize(container, false);
	    } catch (SlickException e) {
	    	Log.e("SLICK", "Failed to create container", e);
	    }
	}
}
