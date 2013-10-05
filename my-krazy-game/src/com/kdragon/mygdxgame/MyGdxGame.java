/*
 * project	my-krazy-game
 * 
 * package	com.kdragon.mygdxgame
 * 
 * @author	Ronaldo Barnes
 * 
 * date		Oct 1, 2013
 */
package com.kdragon.mygdxgame;

import java.util.Iterator;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class MyGdxGame implements ApplicationListener {
    // constant useful for logging
    public static final String LOG = MyGdxGame.class.getSimpleName();
 
    // a libgdx helper class that logs the current FPS each second
    private SpriteBatch batch;
    private Texture backgroundImage;
    private Texture shipImage;
    private Rectangle ship;
    private Texture ship2Image;
    private Rectangle ship2;
    private OrthographicCamera camera;
    private Texture asteroidImage;
    private Array<Rectangle> asteroids;
    private long lastasteroidTime;
    private Sound shipSound;
    private Sound asteroidSound;
    
    @Override
    public void create()
    {
    	//load images
    	shipImage = new Texture(Gdx.files.internal("ship.png"));
    	ship2Image = new Texture(Gdx.files.internal("motherShip.png"));
    	backgroundImage = new Texture(Gdx.files.internal("space.png"));
    	asteroidImage = new Texture(Gdx.files.internal("asteroid.png"));
    	
    	// load the drop sound effect and the rain background "music"
        shipSound = Gdx.audio.newSound(Gdx.files.internal("ship.mp3"));
        asteroidSound = Gdx.audio.newSound(Gdx.files.internal("asteroid.mp3"));
        
    	// create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getHeight(), Gdx.graphics.getWidth());
    	batch = new SpriteBatch();
        Gdx.app.log( MyGdxGame.LOG, "Creating game" );


        // create a Rectangle to logically represent the ships
        ship = new Rectangle();
        ship.x = 800 / 2 - 64 / 2; 
        ship.y = 100; 
        ship.width = 128;
        ship.height = 128;
        
     
        ship2 = new Rectangle();
        ship2.x = 500 / 2 - 64 / 2; 
        ship2.y = 1500; 
        ship2.width = 256;
        ship2.height = 256;
        
        asteroids = new Array<Rectangle>();
        spawnAsteroid();
    }
    
    private void spawnAsteroid() {
        Rectangle asteroid = new Rectangle();
        asteroid.x = MathUtils.random(0, 800-64);
        asteroid.y = Gdx.graphics.getHeight();
        asteroid.width = 64;
        asteroid.height = 64;
        asteroids.add(asteroid);
        lastasteroidTime = TimeUtils.nanoTime();
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
        batch.draw(ship2Image, ship2.x, ship2.y);
        for(Rectangle asteroid: asteroids) {
            batch.draw(asteroidImage, asteroid.x, asteroid.y);
         }
        batch.end();
        
     // process user input
        if(Gdx.input.isTouched()) {
           Vector3 touchPos = new Vector3();
           touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
           camera.unproject(touchPos);
           ship.x = touchPos.x - 64 / 2;
           shipSound.play();
        }
        if(Gdx.input.isKeyPressed(Keys.LEFT)) ship.x -= 200 * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Keys.RIGHT)) ship.x += 200 * Gdx.graphics.getDeltaTime();
      
 
     
        if(TimeUtils.nanoTime() - lastasteroidTime > 1000000000) spawnAsteroid();
        
        
        Iterator<Rectangle> iter = asteroids.iterator();
        while(iter.hasNext()) {
           Rectangle asteroid = iter.next();
           asteroid.y -= 200 * Gdx.graphics.getDeltaTime();
           if(asteroid.y + 64 < 0) iter.remove();
           if((asteroid.overlaps(ship))||(asteroid.overlaps(ship2))) {
              asteroidSound.play();
              iter.remove();
           }
        }
        
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
    	ship2Image.dispose();
    	asteroidImage.dispose();
    	asteroidSound.dispose();
    	shipSound.dispose();
        Gdx.app.log( MyGdxGame.LOG, "Disposing game" );
    }
}