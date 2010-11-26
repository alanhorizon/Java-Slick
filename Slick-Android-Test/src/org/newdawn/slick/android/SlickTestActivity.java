package org.newdawn.slick.android;

import org.newdawn.slick.AndroidResourceLocation;
import org.newdawn.slick.GDXGameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.renderer.GDXRenderer;
import org.newdawn.slick.tests.TestGDXContainer;
import org.newdawn.slick.util.ResourceLoader;

import android.os.Bundle;
import android.util.Log;

import com.badlogic.gdx.backends.android.AndroidApplication;

public class SlickTestActivity extends AndroidApplication {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        try {
			Renderer.setRenderer(new GDXRenderer());
			Renderer.setLineStripRenderer(Renderer.QUAD_BASED_LINE_STRIP_RENDERER);
			
			ResourceLoader.removeAllResourceLocations();
			ResourceLoader.addResourceLocation(new AndroidResourceLocation(getAssets()));
        	GDXGameContainer container = new GDXGameContainer(new TestGDXContainer(), 800, 480);        
        	initialize(container, false);
        } catch (SlickException e) {
        	Log.e("SLICK", "Failed to create container", e);
        }
    }
}