package com.kdragon.mygdxgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kdragon.screens.SplashScreen;

public class KrazyGame  extends Game {
    
    public SpriteBatch batch;
    public BitmapFont font;

    public void create() {
            batch = new SpriteBatch();
            //Use LibGDX's default Arial font.
            font = new BitmapFont();
            this.setScreen(new SplashScreen(this));
    }

    public void render() {
            super.render(); //important!
    }
    
    public void dispose() {
            batch.dispose();
            font.dispose();
    }

}
