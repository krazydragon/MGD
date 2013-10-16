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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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
    private OrthographicCamera camera;
    private Texture asteroidImage;
    private Array<Rectangle> asteroids;
    private Array<Rectangle> motherships;
    private Iterator<Rectangle> mothershipIterator;
	private Rectangle tempMothership;
    private long lastasteroidTime;
    private Sound shipSound;
    private Sound asteroidSound;
    private Sound mothershipSound;
    private int screenWidth;
    private int screenHeight;
    int tempNum;
    private Explosions explosions;
    private Stage stage;
    private Texture pauseImage;
    private Image pauseButton;
    private boolean isPlaying = true;
 
    
    
    
    @Override
    public void create()
    {
    	
    	screenHeight = Gdx.graphics.getHeight();
    	screenWidth =Gdx.graphics.getWidth();
    	//load images
    	Texture.setEnforcePotImages(false);
    	shipImage = new Texture(Gdx.files.internal("ship.png"));
    	mothershipImage = new Texture(Gdx.files.internal("motherShip.png"));
    	backgroundImage = new Texture(Gdx.files.internal("space.jpg"));
    	asteroidImage = new Texture(Gdx.files.internal("asteroid.png"));

    	tempNum = 0;
    	pauseImage = new Texture(Gdx.files.internal("pause.png"));
    	pauseButton= new Image(pauseImage);
    	
    	pauseButton.setBounds(500, 1000, 64.0f, 6.0f);
    	pauseButton.setTouchable(Touchable.enabled);
    	pauseButton.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int    button){
               
                pause();
                return true;
            }
     });
    	
    	
        
            stage = new Stage(screenWidth,screenHeight,true);
            Gdx.input.setInputProcessor(stage);
            
  
            stage.addActor(pauseButton);
            
            
            
    	// load the drop sound effect and the rain background "music"
        shipSound = Gdx.audio.newSound(Gdx.files.internal("ship.mp3"));
        asteroidSound = Gdx.audio.newSound(Gdx.files.internal("asteroid.mp3"));
        mothershipSound = Gdx.audio.newSound(Gdx.files.internal("mothership.mp3"));
        
           
    	// create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenHeight, screenWidth);
    	batch = new SpriteBatch();
        Gdx.app.log( MyKrazyGame.LOG, "Creating game" );


        // create a Rectangle to logically represent the ships
        ship = new Rectangle();
        ship.x = 800 / 2 - 64 / 2; 
        ship.y = 100; 
        ship.width = 100;
        ship.height = 100;
        
        asteroids = new Array<Rectangle>();
        motherships = new Array<Rectangle>();
        mothershipIterator = motherships.iterator();
    	
        spawnAsteroid();
        spawnMothership();
        tempMothership = mothershipIterator.next();
        
        explosions = new Explosions();
    }
    
    private void spawnAsteroid() {
        Rectangle asteroid = new Rectangle();
        asteroid.x = MathUtils.random(0, screenWidth-64/2);
        asteroid.y = screenHeight*2;
        asteroid.width = 100;
        asteroid.height = 100;
        asteroids.add(asteroid);
        lastasteroidTime = TimeUtils.nanoTime();
     }
    
    private void spawnMothership() {
        Rectangle mothership = new Rectangle();
        mothership.x = 500 / 2 - 64 / 2; 
        mothership.y = screenHeight*2; 
        mothership.width = 256;
        mothership.height = 256;
        motherships.add(mothership);
        
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
        batch.draw(backgroundImage, 0, 0, screenHeight,screenWidth);
        if( isPlaying ){
        	
            batch.draw(shipImage, ship.x, ship.y);
            for (int i = 0; i < explosions.getExplosionsHappening().size(); i++) 
            {
                Explosions getExp = explosions.getExplosionsHappening().get(i);
                batch.draw(getExp.getCurrentFrame(),getExp.posx, getExp.posy);
            }
            for(Rectangle asteroid: asteroids) {
                batch.draw(asteroidImage, asteroid.x, asteroid.y);
             }
            for(Rectangle mothership: motherships) {
                batch.draw(mothershipImage, mothership.x, mothership.y);
                mothership.y -= 200 * Gdx.graphics.getDeltaTime();
                
             }
            
            
        }
        
        batch.end();
        stage.draw();
     // process user input
        if(Gdx.input.isTouched()) {
           Vector3 touchPos = new Vector3();
           touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
           camera.unproject(touchPos);
        	shipSound.play();  
           ship.x = touchPos.x - 64 / 2;
           
        }
        if(Gdx.input.isKeyPressed(Keys.LEFT)) ship.x -= 200 * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Keys.RIGHT)) ship.x += 200 * Gdx.graphics.getDeltaTime();
      
 
     
        if(TimeUtils.nanoTime() - lastasteroidTime > 1000000000) spawnAsteroid();
        
        
        Iterator<Rectangle> asteroidIterator = asteroids.iterator();
        
        while(asteroidIterator.hasNext()) {
           Rectangle asteroid = asteroidIterator.next();
           
           asteroid.y -= 800 * Gdx.graphics.getDeltaTime();
           
           if(asteroid.y + 64 < 0) asteroidIterator.remove();
           if(asteroid.overlaps(ship)) {
        	   asteroidSound.play();
              asteroidIterator.remove();
              explosions.addExplosionsHappening(new Explosions(),asteroid.x,asteroid.y);
              
           }
           
           
        }
        
        
        
        	if(tempMothership.y + 64 < 0){
        		mothershipIterator.remove();
        		spawnMothership();
            	tempMothership = mothershipIterator.next();
            	}else if(tempMothership.overlaps(ship)){
        		mothershipSound.play();
        		mothershipIterator.remove();
        		explosions.addExplosionsHappening(new Explosions(),tempMothership.x,tempMothership.y);
        		spawnMothership();
            	tempMothership = mothershipIterator.next();
        	}

        	
        
        // output the current FPS
        //fpsLogger.log();
    }
 
    @Override
    public void pause()
    {
    	if( isPlaying )
        {
            isPlaying = false;
        }
        else
        {
            isPlaying = true;
        }
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