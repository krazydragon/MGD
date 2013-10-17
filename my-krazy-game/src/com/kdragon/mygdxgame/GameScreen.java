package com.kdragon.mygdxgame;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class GameScreen implements Screen {
    final KrazyGame game;

 // constant useful for logging
    public static final String LOG = KrazyGame.class.getSimpleName();
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
    private AsteroidExplosions asteroidExplosions;
    private MothershipExplosions mothershipExplosions;
    private Stage stage;
    private Texture pauseImage;
    private Image pauseButton;
    private boolean isPlaying = true;
    private BitmapFont font;
    private Label asteroidLabel;
    private int asteroidCount;
    private Label mothershipLabel;
    private int mothershipCount;
    private Label hitLabel;
    private int hitCount;
    

    public GameScreen(final KrazyGame gam) {
            this.game = gam;

            font = new BitmapFont();
            screenHeight = Gdx.graphics.getHeight();
        	screenWidth =Gdx.graphics.getWidth();
        	//load images
        	Texture.setEnforcePotImages(false);
        	shipImage = new Texture(Gdx.files.internal("ship.png"));
        	mothershipImage = new Texture(Gdx.files.internal("motherShip.png"));
        	backgroundImage = new Texture(Gdx.files.internal("space.jpg"));
        	asteroidImage = new Texture(Gdx.files.internal("asteroid.png"));

        	pauseImage = new Texture(Gdx.files.internal("pause.png"));
        	pauseButton= new Image(pauseImage);
        	
        	pauseButton.setBounds(screenWidth/2, screenHeight-80, 64.0f, 64.0f);
        	pauseButton.setTouchable(Touchable.enabled);
        	pauseButton.addListener(new InputListener(){
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int    button){
                   
                    pause();
                    return true;
                }
        	});
        	
        	
            Image backgound = new Image(backgroundImage);
            stage = new Stage(screenWidth,screenHeight,true);
            Gdx.input.setInputProcessor(stage);
            

            asteroidLabel = new Label("Asteroids: 10", new Label.LabelStyle(font, Color.WHITE));
            asteroidLabel.setPosition(screenWidth-200, screenHeight-80);
            asteroidLabel.setFontScaleX(2); 
            asteroidLabel.setFontScaleY(2);
            asteroidLabel.setColor(0, 1, 0, 1);
            asteroidCount = 10;
            
            mothershipLabel = new Label("Motherships: 3", new Label.LabelStyle(font, Color.WHITE));
            mothershipLabel.setPosition(screenWidth-200, screenHeight-110);
            mothershipLabel.setFontScaleX(2); 
            mothershipLabel.setFontScaleY(2);
            mothershipLabel.setColor(0, 1, 0, 1);
            mothershipCount = 3;
            
            hitLabel = new Label("Hit: 0", new Label.LabelStyle(font, Color.WHITE));
            hitLabel.setPosition(screenWidth-200, screenHeight-140);
            hitLabel.setFontScaleX(2); 
            hitLabel.setFontScaleY(2);
            hitLabel.setColor(0, 1, 0, 1);
            hitCount = 0;
            
            stage.addActor(backgound);
            stage.addActor(pauseButton);
            stage.addActor(asteroidLabel);
            stage.addActor(mothershipLabel);
            stage.addActor(hitLabel);
            
                
                
                
        	// load the drop sound effect and the rain background "music"
            shipSound = Gdx.audio.newSound(Gdx.files.internal("ship.mp3"));
            asteroidSound = Gdx.audio.newSound(Gdx.files.internal("asteroid.mp3"));
            mothershipSound = Gdx.audio.newSound(Gdx.files.internal("mothership.mp3"));
            
               
        	// create the camera and the SpriteBatch
            camera = new OrthographicCamera();
            camera.setToOrtho(false, screenWidth, screenHeight);
        	batch = new SpriteBatch();
            


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
            
            asteroidExplosions = new AsteroidExplosions();
            mothershipExplosions = new MothershipExplosions();

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
        mothership.y = screenHeight; 
        mothership.width = 256;
        mothership.height = 256;
        motherships.add(mothership);
        
     }
    
    private void checkMothership(){
    	if(tempMothership.y + 64 < 0){
    		mothershipIterator.remove();
    		spawnMothership();
        	tempMothership = mothershipIterator.next();
        	if(mothershipCount>0){
      		   mothershipCount --;
          	   mothershipLabel.setText("Motherships: "+mothershipCount); 
          	 if((asteroidCount == 0)&&(mothershipCount == 0)){
         		game.setScreen(new WinScreen(game));
          	 }
      	   }
        	}else if(tempMothership.overlaps(ship)){
    		mothershipSound.play();
    		mothershipIterator.remove();
    		mothershipExplosions.addExplosionsHappening(new MothershipExplosions(),tempMothership.x,tempMothership.y);
    		spawnMothership();
        	tempMothership = mothershipIterator.next();
        	if(hitCount < 10){
        		hitCount = hitCount +3;
           	   hitLabel.setText("Hit: "+ hitCount);
           	if(hitCount >= 10){
         		game.setScreen(new LoseScreen(game));
         	   }
       	   }
        	
            
        	
    	}
    }
    
    private void checkAsteroid(){
    	
    	
    	if(TimeUtils.nanoTime() - lastasteroidTime > 1000000000) spawnAsteroid();
        
        
        Iterator<Rectangle> asteroidIterator = asteroids.iterator();
        
        while(asteroidIterator.hasNext()) {
           Rectangle asteroid = asteroidIterator.next();
           
           asteroid.y -= 800 * Gdx.graphics.getDeltaTime();
           
           if(asteroid.y + 64 < 0){
        	   asteroidIterator.remove();
        	   if(asteroidCount>0){
        		   asteroidCount --;
            	   asteroidLabel.setText("Asteroids:"+asteroidCount); 
            	   if((asteroidCount == 0)&&(mothershipCount == 0)){
                		game.setScreen(new WinScreen(game));
                	   }
        	   }
        	   
           }else if(asteroid.overlaps(ship)) {
        	   asteroidSound.play();
              asteroidIterator.remove();
              asteroidExplosions.addExplosionsHappening(new AsteroidExplosions(),asteroid.x,asteroid.y);
              if(hitCount < 10){
          		hitCount ++;
             	hitLabel.setText("Hit: "+ hitCount); 
             	if(hitCount >= 10){
             		game.setScreen(new LoseScreen(game));
             	   }
         	   }
           }
           
           
        }
    }

    @Override
    public void render(float delta) {
    	Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
        
        // tell the camera to update its matrices.
        camera.update();
        stage.draw();
        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        
        if( isPlaying ){
        	
            batch.draw(shipImage, ship.x, ship.y);
            for (int i = 0; i < asteroidExplosions.getExplosionsHappening().size(); i++) 
            {
                AsteroidExplosions getExp = asteroidExplosions.getExplosionsHappening().get(i);
                batch.draw(getExp.getCurrentFrame(),getExp.posx, getExp.posy);
            }
            for (int i = 0; i < mothershipExplosions.getExplosionsHappening().size(); i++) 
            {
                MothershipExplosions getExp = mothershipExplosions.getExplosionsHappening().get(i);
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
      
        checkMothership();
        checkAsteroid();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
            
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
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
    public void resume() {
    }

    @Override
    public void dispose() {
    	backgroundImage.dispose();
    	shipImage.dispose();
    	mothershipImage.dispose();
    	asteroidImage.dispose();
    	asteroidSound.dispose();
    	shipSound.dispose();
        
    }

}
