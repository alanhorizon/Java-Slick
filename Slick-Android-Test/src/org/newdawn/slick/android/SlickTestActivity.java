package org.newdawn.slick.android;

import org.newdawn.slick.SlickActivity;
import org.newdawn.slick.tests.TestGDXContainer;

import android.os.Bundle;

/**
 * A simple test activity to show bootstrapping a Slick Game implementation
 * into android.
 * 
 * @author kevin
 */
public class SlickTestActivity extends SlickActivity {
	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        start(new TestGDXContainer(), 800, 480);
    }
}