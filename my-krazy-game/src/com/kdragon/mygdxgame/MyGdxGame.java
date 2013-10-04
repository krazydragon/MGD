package com.kdragon.mygdxgame;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class MyGdxGame implements ApplicationListener {
    // constant useful for logging
    public static final String LOG = MyGdxGame.class.getSimpleName();
 
    // a libgdx helper class that logs the current FPS each second
    private SpriteBatch batch;
    private FPSLogger fpsLogger;
    private Texture backgroundImage;
    private Texture shipImage;
    private Rectangle ship;
    private OrthographicCamera camera;
 
    @Override
    public void create()
    {
    	//load images
    	shipImage = new Texture(Gdx.files.internal("ship.png"));
    	backgroundImage = new Texture(Gdx.files.internal("space.png"));
    	
    	
    	// create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getHeight(), Gdx.graphics.getWidth());
    	batch = new SpriteBatch();
        Gdx.app.log( MyGdxGame.LOG, "Creating game" );


        // create a Rectangle to logically represent the bucket
        ship = new Rectangle();
        ship.x = 800 / 2 - 64 / 2; // center the bucket horizontally
        ship.y = 100; // bottom left corner of the bucket is 20 pixels above the bottom screen edge
        ship.width = 64;
        ship.height = 64;
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
        //Gdx.gl.glClearColor( 0f, 1f, 0f, 1f );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
        
        // tell the camera to update its matrices.
        camera.update();
        
        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(backgroundImage, 0, 0, Gdx.graphics.getHeight(),Gdx.graphics.getWidth());
        batch.draw(shipImage, ship.x, ship.y);
        batch.end();
        
     // process user input
        if(Gdx.input.isTouched()) {
           Vector3 touchPos = new Vector3();
           touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
           camera.unproject(touchPos);
           ship.x = touchPos.x - 64 / 2;
        }
        if(Gdx.input.isKeyPressed(Keys.LEFT)) ship.x -= 200 * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Keys.RIGHT)) ship.x += 200 * Gdx.graphics.getDeltaTime();
      
 
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
    	backgroundImage.dispose();
    	shipImage.dispose();
        Gdx.app.log( MyGdxGame.LOG, "Disposing game" );
    }
}