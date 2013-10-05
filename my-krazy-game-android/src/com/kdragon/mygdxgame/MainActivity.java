/*
 * project	my-krazy-game-android
 * 
 * package	com.kdragon.mygdxgame
 * 
 * @author	Ronaldo Barnes
 * 
 * date		Oct 1, 2013
 */
package com.kdragon.mygdxgame;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class MainActivity extends AndroidApplication {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGL20 = false;
        
        initialize(new MyGdxGame(), cfg);
    }
}