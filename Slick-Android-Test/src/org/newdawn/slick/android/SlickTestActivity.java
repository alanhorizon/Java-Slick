package org.newdawn.slick.android;

import org.newdawn.slick.SlickActivity;
import org.newdawn.slick.tests.TestGDXContainer;

import android.os.Bundle;

public class SlickTestActivity extends SlickActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        start(new TestGDXContainer(), 640, 320);
    }
}