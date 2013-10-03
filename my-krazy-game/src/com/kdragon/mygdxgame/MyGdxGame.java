package com.kdragon.mygdxgame;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class MyGdxGame implements ApplicationListener {
    // constant useful for logging
    public static final String LOG = MyGdxGame.class.getSimpleName();
 
    // a libgdx helper class that logs the current FPS each second
    private SpriteBatch batch;
    private FPSLogger fpsLogger;
    private Texture texture;
 
    @Override
    public void create()
    {
    	texture = new Texture(Gdx.files.internal("space.png"));
    	Texture.setEnforcePotImages(false);
    	batch = new SpriteBatch();
        Gdx.app.log( MyGdxGame.LOG, "Creating game" );
        //fpsLogger = new FPSLogger();
    }
 
    @Override
    public void resize(
        int width,
        int height )
    {
        Gdx.app.log( MyGdxGame.LOG, "Resizing game to: " + width + " x " + height );
        
    }
 
    @Override
    public void render()
    {
        // the following code clears the screen with the given RGB color (green)
        Gdx.gl.glClearColor( 0f, 1f, 0f, 1f );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
        
        batch.begin();
        batch.draw(texture, 0, 0, Gdx.graphics.getHeight(),Gdx.graphics.getWidth());
        batch.end();
 
        // output the current FPS
        //fpsLogger.log();
    }
 
    @Override
    public void pause()
    {
        Gdx.app.log( MyGdxGame.LOG, "Pausing game" );
    }
 
    @Override
    public void resume()
    {
        Gdx.app.log( MyGdxGame.LOG, "Resuming game" );
    }
 
    @Override
    public void dispose()
    {
    	texture.dispose();
        Gdx.app.log( MyGdxGame.LOG, "Disposing game" );
    }
}