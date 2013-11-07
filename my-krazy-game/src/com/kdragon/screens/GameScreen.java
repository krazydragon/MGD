package com.kdragon.screens;

import java.util.Iterator;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.kdragon.mygdxgame.KrazyGame;
import com.kdragon.other.AsteroidExplosions;
import com.kdragon.other.MothershipExplosions;

public class GameScreen implements Screen {
    final KrazyGame game;

 // constant useful for logging
    public static final String LOG = KrazyGame.class.getSimpleName();
	// a libgdx helper class that logs the current FPS each second
    private SpriteBatch batch;
    private Texture backgroundImage;
    private Texture shipImage;
    private Texture smallLaserImage;
    private Texture bigLaserImage;
    private Texture enemyLaserImage;
    private Texture gemImage;
    private Rectangle ship;
    private Texture mothershipImage;
    private Texture bossShipImage;
    private OrthographicCamera camera;
    private Texture asteroidImage;
    private Array<Rectangle> asteroids;
    private Array<Rectangle> gems;
    private Array<Rectangle> lasers;
    private Array<Rectangle> enemyLasers;
    private Array<Rectangle> motherships;
    private Array<Rectangle> bossShips;
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
    private Label asteroidLabel;
    private int asteroidCount;
    private Label mothershipLabel;
    private int mothershipCount;
    private Label hitLabel;
    private int hitCount;
    private int laserDelay;
    private int enemyLaserDelay;
    private int userLaser;
    
   

    

    public GameScreen(final KrazyGame gam) {
            this.game = gam;
            

            
            screenHeight = Gdx.graphics.getHeight();
        	screenWidth =Gdx.graphics.getWidth();
        	//load images
        	Texture.setEnforcePotImages(false);
        	shipImage = new Texture(Gdx.files.internal("ship.png"));
        	mothershipImage = new Texture(Gdx.files.internal("motherShip.png"));
        	bossShipImage = new Texture(Gdx.files.internal("boss.png"));
        	backgroundImage = new Texture(Gdx.files.internal("space.png"));
        	asteroidImage = new Texture(Gdx.files.internal("asteroid.png"));
        	gemImage = new Texture(Gdx.files.internal("gem.png"));
        	smallLaserImage = new Texture(Gdx.files.internal("smallLaser.png"));
        	bigLaserImage = new Texture(Gdx.files.internal("bigLaser.png"));
        	enemyLaserImage = new Texture(Gdx.files.internal("enemyLaser.png"));
        	
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
            

            asteroidLabel = new Label("Asteroids: 10", new Label.LabelStyle(game.font, Color.WHITE));
            asteroidLabel.setPosition(screenWidth-200, screenHeight-80);
            asteroidLabel.setFontScaleX(2); 
            asteroidLabel.setFontScaleY(2);
            asteroidLabel.setColor(0, 1, 0, 1);
            asteroidCount = 3;
            
            mothershipLabel = new Label("Motherships: 3", new Label.LabelStyle(game.font, Color.WHITE));
            mothershipLabel.setPosition(screenWidth-200, screenHeight-110);
            mothershipLabel.setFontScaleX(2); 
            mothershipLabel.setFontScaleY(2);
            mothershipLabel.setColor(0, 1, 0, 1);
            mothershipCount = 3;
            
            hitLabel = new Label("Hit: 0", new Label.LabelStyle(game.font, Color.WHITE));
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
            lasers = new Array<Rectangle>();
            enemyLasers = new Array<Rectangle>();
            gems = new Array<Rectangle>();
            motherships = new Array<Rectangle>();
            bossShips = new Array<Rectangle>();
            
            
            spawnBossShip();
            
            
            asteroidExplosions = new AsteroidExplosions();
            mothershipExplosions = new MothershipExplosions();
            
            laserDelay = 0;
            enemyLaserDelay = 0;

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
    
    private void spawnGem(float x, float y) {
        Rectangle gem = new Rectangle();
        gem.x = x;
        gem.y = y;
        gem.width = 100;
        gem.height = 100;
        gems.add(gem);
        
        
     }
    
    private void spawnLaser(float x, float y) {
        Rectangle laser = new Rectangle();
        laser.x = x + 35;
        laser.y = y + 75;
        laser.width = 100;
        laser.height = 100;
        lasers.add(laser);
        
     }
    
    private void spawnEnemyLaser(float x, float y) {
        Rectangle enemyLaser = new Rectangle();
        enemyLaser.x = x;
        enemyLaser.y = y;
        enemyLaser.width = 100;
        enemyLaser.height = 100;
        enemyLasers.add(enemyLaser);
        
     }
    
    private void spawnMothership() {
        Rectangle mothership = new Rectangle();
        Random rand = new Random();
        int x = rand.nextInt(screenWidth);
        mothership.x = x - 64 / 2; 
        mothership.y = screenHeight; 
        mothership.width = 256;
        mothership.height = 256;
        motherships.add(mothership);
        
     }
    
    private void spawnBossShip() {
        Rectangle boss = new Rectangle();
        
        Random rand = new Random();
        int x = rand.nextInt(screenWidth);
        boss.x = x - 64 / 2; 
        boss.y = screenHeight; 
        boss.width = 400;
        boss.height = 400;
        bossShips.add(boss);
        
     }
    
    private void checkMothership(){
    	Iterator<Rectangle> mothershipIterator = motherships.iterator();
        
        while(mothershipIterator.hasNext()) {
        	Rectangle mothership = mothershipIterator.next();
        	mothership.y -= 200 * Gdx.graphics.getDeltaTime();
        	enemyLaserDelay++;
            if(enemyLaserDelay == 80){
         	   spawnEnemyLaser(mothership.x + 100, mothership.y);
         	  
                enemyLaserDelay = 0;
            }
        	if(mothership.y + 64 < 0){
        		mothershipIterator.remove();
        		spawnMothership();
        		
            	
            }else if(mothership.overlaps(ship)){
         	   
            	mothershipSound.play();
        		mothershipIterator.remove();
        		mothershipExplosions.addExplosionsHappening(new MothershipExplosions(),mothership.x,mothership.y);
        		spawnMothership();
         	   
            }
        	
        	Iterator<Rectangle> laserIterator = lasers.iterator();
            
            while(laserIterator.hasNext()) {
            	Rectangle laser = laserIterator.next();
            	if(laser.overlaps(mothership)){
            		mothershipSound.play();
            		laserIterator.remove();
            		mothershipIterator.remove();
            		mothershipExplosions.addExplosionsHappening(new MothershipExplosions(),mothership.x,mothership.y);
            		spawnMothership();
            		if(mothershipCount>0){
               		   mothershipCount --;
                   	   mothershipLabel.setText("Motherships: "+mothershipCount); 
                   	 if(mothershipCount == 0){
                  		spawnBossShip();
                   	 }
                   	 
               	   }
                	if(hitCount < 10){
                		hitCount = hitCount +3;
                   	   hitLabel.setText("Hit: "+ hitCount);
                   	if(hitCount >= 10){
                 		//game.setScreen(new LoseScreen(game));
                 	   }
               	   }
            	}
            }
        }
        
        Iterator<Rectangle> bossIterator = bossShips.iterator();
        while(bossIterator.hasNext()) {
        	Rectangle bossShip = bossIterator.next();
        	enemyLaserDelay++;
            if(enemyLaserDelay == 60){
            	spawnEnemyLaser(bossShip.x, bossShip.y);
            	spawnEnemyLaser(bossShip.x + 100, bossShip.y);
            	spawnEnemyLaser(bossShip.x + 200, bossShip.y);
            	spawnEnemyLaser(bossShip.x + 300, bossShip.y);
                enemyLaserDelay = 0;
            }
        	if(bossShip.y + 64 < 0){
        		bossIterator.remove();
        		//spawnMothership();
        		
            	
            }else if(bossShip.overlaps(ship)){
         	   
            	mothershipSound.play();
        		bossIterator.remove();
        		mothershipExplosions.addExplosionsHappening(new MothershipExplosions(),bossShip.x,bossShip.y);
        		//spawnMothership();
         	   
            }
        	
        	Iterator<Rectangle> laserIterator = lasers.iterator();
            
            while(laserIterator.hasNext()) {
            	Rectangle laser = laserIterator.next();
            	if(laser.overlaps(bossShip)){
            		mothershipSound.play();
            		laserIterator.remove();
            		bossIterator.remove();
            		mothershipExplosions.addExplosionsHappening(new MothershipExplosions(),bossShip.x,bossShip.y);
            		//spawnMothership();
            		if(mothershipCount>0){
               		   mothershipCount --;
                   	   mothershipLabel.setText("Motherships: "+mothershipCount); 
                   	 if(mothershipCount == 0){
                  		//spawnBossShip();
                   	 }
                   	 
               	   }
                	if(hitCount < 10){
                		hitCount = hitCount +3;
                   	   hitLabel.setText("Hit: "+ hitCount);
                   	if(hitCount >= 10){
                 		//game.setScreen(new LoseScreen(game));
                 	   }
               	   }
            	}
            }
        }
    	Iterator<Rectangle> enemyLaserIterator = enemyLasers.iterator();
        
        while(enemyLaserIterator.hasNext()) {
        	
        	Rectangle enemyLaser = enemyLaserIterator.next();
        	if(enemyLaser.overlaps(ship)){
        		//game.setScreen(new LoseScreen(game));
    			enemyLaserIterator.remove();
    			
            	
    			
        	}
    	}
    	
    	
    	
    }
    
    
    private void checkAsteroid(){
    	
    	if( isPlaying ){
    		if(TimeUtils.nanoTime() - lastasteroidTime > 1000000000) spawnAsteroid();
            
            
            Iterator<Rectangle> asteroidIterator = asteroids.iterator();
            
            while(asteroidIterator.hasNext()) {
               Rectangle asteroid = asteroidIterator.next();
               
               asteroid.y -= 800 * Gdx.graphics.getDeltaTime();
               
               if(asteroid.y + 64 < 0){
            	   asteroidIterator.remove();
            	   
            	   
               }else if(asteroid.overlaps(ship)){
            	   
            	   asteroidSound.play();
                   asteroidIterator.remove();
                   asteroidExplosions.addExplosionsHappening(new AsteroidExplosions(),asteroid.x,asteroid.y);
                   break;
            	   
               }
               
               Iterator<Rectangle> laserIterator = lasers.iterator();
               
               while(laserIterator.hasNext()) {
               	Rectangle laser = laserIterator.next();
               	if(asteroid.overlaps(laser)) {
               		asteroidExplosions.addExplosionsHappening(new AsteroidExplosions(),asteroid.x,asteroid.y);
               		asteroidSound.play();
               		asteroidIterator.remove();
               		laserIterator.remove();
               		if(asteroidCount>0){
             		   asteroidCount --;
                 	   asteroidLabel.setText("Asteroids:"+asteroidCount); 
                 	   if(asteroidCount == 0){
                 		   spawnMothership();
                     	   }
             	   }
                   if(hitCount < 10){
               		hitCount ++;
                  	hitLabel.setText("Hit: "+ hitCount); 
                  	if(hitCount >= 10){
                  		//game.setScreen(new LoseScreen(game));
                  	   }
                  	
              	   }
                   Random rand = new Random();
                   int x = rand.nextInt(3);
                   if(x == 2){
                	   spawnGem(asteroid.x, asteroid.y);
                   }
                   break;
               	}  
               }
            }
    	}
    	Iterator<Rectangle> gemIterator = gems.iterator();
        
        while(gemIterator.hasNext()) {
        	
        	Rectangle gem = gemIterator.next();
    		if(gem.overlaps(ship)) {
         	   asteroidSound.play();
               gemIterator.remove();
               userLaser = 1;
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
            for(Rectangle laser: lasers) {
            	if(userLaser == 0){
            		batch.draw(smallLaserImage, laser.x, laser.y);
            	}else if(userLaser == 1){
            		batch.draw(bigLaserImage, laser.x, laser.y);
            	}
                laser.y += 700 * Gdx.graphics.getDeltaTime();
             }
            for(Rectangle enemylaser: enemyLasers) {
                batch.draw(enemyLaserImage, enemylaser.x, enemylaser.y);
                enemylaser.y -= 800 * Gdx.graphics.getDeltaTime();
                
             }
            for(Rectangle asteroid: asteroids) {
                batch.draw(asteroidImage, asteroid.x, asteroid.y);
             }
            for(Rectangle gem: gems) {
                batch.draw(gemImage, gem.x, gem.y);
             }
            for(Rectangle mothership: motherships) {
                batch.draw(mothershipImage, mothership.x, mothership.y); 
             }
            for(Rectangle boss: bossShips) {
                batch.draw(bossShipImage, boss.x, boss.y); 
                boss.y -= 15 * Gdx.graphics.getDeltaTime();
             }
            
            batch.draw(shipImage, ship.x, ship.y);
        }else if(!isPlaying){
        	game.font.draw(batch, "Game Paused", Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        }
        
        batch.end();
        
        checkMothership();
        checkAsteroid();
     // process user input
        if(Gdx.input.isTouched()) {
           Vector3 touchPos = new Vector3();
           touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
           camera.unproject(touchPos);
        	shipSound.play();  
           ship.x = touchPos.x - 64 / 2;
           ship.y = touchPos.y - 64 / 2;
           
           
           laserDelay++;
           if(laserDelay == 50){
        	   spawnLaser(ship.x, ship.y);
               laserDelay = 0;
           }
            
           
        }
        if(Gdx.input.isKeyPressed(Keys.ANY_KEY)){
        	if(Gdx.input.isKeyPressed(Keys.LEFT)) ship.x -= 200 * Gdx.graphics.getDeltaTime();
            if(Gdx.input.isKeyPressed(Keys.RIGHT)) ship.x += 200 * Gdx.graphics.getDeltaTime();
            
            if(Gdx.input.isKeyPressed(Keys.DOWN)) ship.y -= 200 * Gdx.graphics.getDeltaTime();
            if(Gdx.input.isKeyPressed(Keys.UP)) ship.y += 200 * Gdx.graphics.getDeltaTime();
        }
      
        
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
