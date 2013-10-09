package com.kdragon.mygdxgame;

import java.util.Iterator;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class MyKrazyGame implements ApplicationListener {
	
	// constant useful for logging
    public static final String LOG = MyKrazyGame.class.getSimpleName();
	// a libgdx helper class that logs the current FPS each second
    private SpriteBatch batch;
    private Texture backgroundImage;
    private Texture shipImage;
    private Rectangle ship;
    private Texture mothershipImage;
    private Rectangle ship2;
    private OrthographicCamera camera;
    private Texture asteroidImage;
    private Array<Rectangle> asteroids;
    private Array<Rectangle> motherships;
    private long lastasteroidTime;
    private Sound shipSound;
    private Sound asteroidSound;
    private int screenWidth;
    private int screenHeight;
    int tempNum;
    @Override
    public void create()
    {
    	
    	screenHeight = Gdx.graphics.getHeight();
    	screenWidth =Gdx.graphics.getWidth();
    	//load images
    	shipImage = new Texture(Gdx.files.internal("ship.png"));
    	mothershipImage = new Texture(Gdx.files.internal("motherShip.png"));
    	backgroundImage = new Texture(Gdx.files.internal("space.png"));
    	asteroidImage = new Texture(Gdx.files.internal("asteroid.png"));
    	
    	// load the drop sound effect and the rain background "music"
        shipSound = Gdx.audio.newSound(Gdx.files.internal("ship.mp3"));
        asteroidSound = Gdx.audio.newSound(Gdx.files.internal("asteroid.mp3"));
        
    	// create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getHeight(), Gdx.graphics.getWidth());
    	batch = new SpriteBatch();
        Gdx.app.log( MyKrazyGame.LOG, "Creating game" );


        // create a Rectangle to logically represent the ships
        ship = new Rectangle();
        ship.x = 800 / 2 - 64 / 2; 
        ship.y = 100; 
        ship.width = 128;
        ship.height = 128;
        
        asteroids = new Array<Rectangle>();
        motherships = new Array<Rectangle>();
        spawnAsteroid();
        spawnMothership();
    }
    
    private void spawnAsteroid() {
        Rectangle asteroid = new Rectangle();
        asteroid.x = MathUtils.random(0, screenWidth-64/2);
        asteroid.y = screenHeight*2;
        asteroid.width = 16;
        asteroid.height = 16;
        asteroids.add(asteroid);
        lastasteroidTime = TimeUtils.nanoTime();
     }
    
    private void spawnMothership() {
        Rectangle mothership = new Rectangle();
        mothership.x = 500 / 2 - 64 / 2; 
        mothership.y = 1500; 
        mothership.width = 256;
        mothership.height = 256;
        motherships.add(mothership);
        tempNum = 0;
     }
     
 
    @Override
    public void resize(
        int width,
        int height )
    {
        Gdx.app.log( MyKrazyGame.LOG, "Resizing game to: " + width + " x " + height );
        
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
        for(Rectangle asteroid: asteroids) {
            batch.draw(asteroidImage, asteroid.x, asteroid.y);
         }
        for(Rectangle mothership: motherships) {
            batch.draw(mothershipImage, mothership.x, mothership.y);
         }
        batch.end();
        
     // process user input
        if(Gdx.input.isTouched()) {
           Vector3 touchPos = new Vector3();
           touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
           camera.unproject(touchPos);
           
        	// shipSound.play();  
           
           ship.x = touchPos.x - 64 / 2;
           
        }
        if(Gdx.input.isKeyPressed(Keys.LEFT)) ship.x -= 200 * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Keys.RIGHT)) ship.x += 200 * Gdx.graphics.getDeltaTime();
      
 
     
        if(TimeUtils.nanoTime() - lastasteroidTime > 1000000000) spawnAsteroid();
        
        
        Iterator<Rectangle> iter = asteroids.iterator();
        Iterator<Rectangle> iter2 = motherships.iterator();
        
        while(iter.hasNext()) {
           Rectangle asteroid = iter.next();
           asteroid.y -= 200 * Gdx.graphics.getDeltaTime();
           if(asteroid.y + 64 < 0) iter.remove();
           if(asteroid.overlaps(ship)) {
              asteroidSound.play();
              iter.remove();
              tempNum++;
              if(tempNum == 1) {

            	  Rectangle mothership = iter2.next();
                  iter2.remove();
                  
               }
              
              if(tempNum == 2) {

            	  spawnMothership();
                  
               }
           }
           
           
               
               
               
               
           
        }
        
        // output the current FPS
        //fpsLogger.log();
    }
 
    @Override
    public void pause()
    {
        Gdx.app.log( MyKrazyGame.LOG, "Pausing game" );
    }
 
    @Override
    public void resume()
    {
        Gdx.app.log( MyKrazyGame.LOG, "Resuming game" );
    }
 
    @Override
    public void dispose()
    {
    	backgroundImage.dispose();
    	shipImage.dispose();
    	mothershipImage.dispose();
    	asteroidImage.dispose();
    	asteroidSound.dispose();
    	shipSound.dispose();
        Gdx.app.log( MyKrazyGame.LOG, "Disposing game" );
    }
}
