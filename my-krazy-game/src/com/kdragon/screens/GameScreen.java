package com.kdragon.screens;

import java.util.Iterator;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
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
import com.kdragon.other.ScoreData;

public class GameScreen implements Screen {
    final KrazyGame game;

 // constant useful for logging
    public static final String LOG = KrazyGame.class.getSimpleName();
	// a libgdx helper class that logs the current FPS each second
    private SpriteBatch batch;
    private Texture backgroundImage;
    private Texture shipImage;
    private Texture smallLaserImage;
    private Texture tripleLaserImage;
    private Texture bigLaserImage;
    private Texture enemyLaserImage;
    private Texture gemImage;
    private Texture healthImage;
    private Texture laserUpgradeImage;
    private Rectangle ship;
    private Texture mothershipImage;
    private Texture bossShipImage;
    private OrthographicCamera camera;
    private Texture asteroidImage;
    private Array<Rectangle> asteroids;
    private Array<Rectangle> gems;
    private Array<Rectangle> laserUpgrades;
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
    private int hitCount;
    private int laserDelay;
    private int enemyLaserDelay;
    private int bossLaserDelay;
    private int userLaser;
    private int score;
    private int health;
    private ScoreData scoreData;
    private Preferences prefs;
    
   

    

    public GameScreen(final KrazyGame gam) {
            this.game = gam;
            scoreData = new ScoreData();
            
            //Get Screen Height and Width 
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
        	healthImage = new Texture(Gdx.files.internal("firstAid.png"));
        	laserUpgradeImage = new Texture(Gdx.files.internal("laserIcon.png"));
        	smallLaserImage = new Texture(Gdx.files.internal("smallLaser.png"));
        	tripleLaserImage = new Texture(Gdx.files.internal("tripleLaser.png"));
        	bigLaserImage = new Texture(Gdx.files.internal("bigLaser.png"));
        	enemyLaserImage = new Texture(Gdx.files.internal("enemyLaser.png"));
        	
        	
        	//setup button
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
        	
        	//add background image
            Image backgound = new Image(backgroundImage);
            stage = new Stage(screenWidth,screenHeight,true);
            Gdx.input.setInputProcessor(stage);
            

            //create labels
            asteroidLabel = new Label("Score: 000000", new Label.LabelStyle(game.font, Color.WHITE));
            asteroidLabel.setPosition(screenWidth-200, screenHeight-80);
            asteroidLabel.setFontScaleX(2); 
            asteroidLabel.setFontScaleY(2);
            asteroidLabel.setColor(0, 1, 0, 1);
            score = 0;
            health = 100;
            asteroidCount = 7;
            
            mothershipLabel = new Label("Health: 100", new Label.LabelStyle(game.font, Color.WHITE));
            mothershipLabel.setPosition(screenWidth-200, screenHeight-110);
            mothershipLabel.setFontScaleX(2); 
            mothershipLabel.setFontScaleY(2);
            mothershipLabel.setColor(0, 1, 0, 1);
            mothershipCount = 0;
            
            
            hitCount = 0;
            
            
            //add user interface
            stage.addActor(backgound);
            stage.addActor(pauseButton);
            stage.addActor(asteroidLabel);
            stage.addActor(mothershipLabel);
            
            
                
                
                
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
            
            
            //setup asteroids weapons and rewards
            asteroids = new Array<Rectangle>();
            lasers = new Array<Rectangle>();
            enemyLasers = new Array<Rectangle>();
            gems = new Array<Rectangle>();
            laserUpgrades = new Array<Rectangle>();
            motherships = new Array<Rectangle>();
            bossShips = new Array<Rectangle>();
            
            //add explosions
            asteroidExplosions = new AsteroidExplosions();
            mothershipExplosions = new MothershipExplosions();
            
            //setup delays
            laserDelay = 0;
            enemyLaserDelay = 0;
            bossLaserDelay = 0;
            
            //setup player weapon
            userLaser = 0;

    }

    //Generate Asteroid
    private void spawnAsteroid() {
        Rectangle asteroid = new Rectangle();
        asteroid.x = MathUtils.random(0, screenWidth-64/2);
        asteroid.y = screenHeight*2;
        asteroid.width = 64;
        asteroid.height = 64;
        asteroids.add(asteroid);
        lastasteroidTime = TimeUtils.nanoTime();
     }
    
  //Generate Rewards
    private void spawnGem(float x, float y) {
        Rectangle gem = new Rectangle();
        gem.x = x;
        gem.y = y;
        gem.width = 100;
        gem.height = 100;
        gems.add(gem);
        
        
     }
    
  //Generate weapon upgrades
    private void spawnLaserUpgrade(float x, float y) {
        Rectangle upgrade = new Rectangle();
        upgrade.x = x;
        upgrade.y = y;
        upgrade.width = 100;
        upgrade.height = 100;
        laserUpgrades.add(upgrade);
        
        
     }
    
    ////Generate player and enemy laser
    private void spawnLaser(float x, float y) {
        Rectangle laser = new Rectangle();
        laser.x = x;
        laser.y = y;
        laser.width = 32;
        laser.height = 32;
        lasers.add(laser);
        
     }
    
    private void spawnEnemyLaser(float x, float y) {
        Rectangle enemyLaser = new Rectangle();
        enemyLaser.x = x;
        enemyLaser.y = y;
        enemyLaser.width = 32;
        enemyLaser.height = 32;
        enemyLasers.add(enemyLaser);
        
     }
    
    
    //create enemies
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
        boss.x = x - 400 / 2; 
        boss.y = screenHeight; 
        boss.width = 400;
        boss.height = 400;
        bossShips.add(boss);
        
     }
    
    
    //detect enemies interaction with user.
    private void checkMothership(){
    	
    	//setup enemy ship
    	Iterator<Rectangle> mothershipIterator = motherships.iterator();
        
        while(mothershipIterator.hasNext()) {
        	
        	//set ship speed
        	Rectangle mothership = mothershipIterator.next();
        	mothership.y -= 200 * Gdx.graphics.getDeltaTime();
        	
        	//fire laser
        	enemyLaserDelay++;
            if(enemyLaserDelay == 80){
         	   spawnEnemyLaser(mothership.x + 100, mothership.y);
         	  
                enemyLaserDelay = 0;
            }
            
            //check and see if laser hit player
            Iterator<Rectangle> enemyLaserIterator = enemyLasers.iterator();
            
            while(enemyLaserIterator.hasNext()) {
            	
            	Rectangle enemyLaser = enemyLaserIterator.next();
            	if(enemyLaser.overlaps(ship)){
            		
        			enemyLaserIterator.remove();
        			health = health - 10;
            		mothershipLabel.setText("Health:" + health );
                	
        			
            	}
        	}
            
            //ship has reached bottom of screen create another
        	if(mothership.y + 64 < 0){
        		mothershipIterator.remove();
        		spawnMothership();
        		
            //ship collides with player	
            }else if(mothership.overlaps(ship)){
         	   
            	mothershipSound.play();
        		mothershipIterator.remove();
        		mothershipExplosions.addExplosionsHappening(new MothershipExplosions(),mothership.x,mothership.y);
        		spawnMothership();
        		health = health - 25;
        		mothershipLabel.setText("Health:" + health );
         	   
            }
        	
        	//check and see if user's laser has hit ship
        	Iterator<Rectangle> laserIterator = lasers.iterator();
            
            while(laserIterator.hasNext()) {
            	Rectangle laser = laserIterator.next();
            	if(laser.overlaps(mothership)){
            		mothershipSound.play();
            		laserIterator.remove();
            		
            		
            		score += 1000;
            		asteroidLabel.setText("Score:"+score); 
            		
            		if(hitCount <= 2){
            			hitCount ++;
                   	if(hitCount == 3){
                   		mothershipCount ++;
                   		mothershipIterator.remove();
                   		spawnMothership();
                   		hitCount = 0;
                   		mothershipExplosions.addExplosionsHappening(new MothershipExplosions(),mothership.x,mothership.y);
                 	   }
               	   }else if((hitCount>=4)&&(mothershipCount < 15)){
               		mothershipCount ++;  
               		mothershipIterator.remove();
               		spawnMothership();
               		mothershipExplosions.addExplosionsHappening(new MothershipExplosions(),mothership.x,mothership.y);
               		
               	   }else{
               		
               		   //Player has multiple lasers detect which one hit ship
               		Iterator<Rectangle> tempIterator = motherships.iterator();
                    
                    while(tempIterator.hasNext()) {
                    	Rectangle temp = tempIterator.next();
                    	if(temp.overlaps(laser)){
                    	tempIterator.remove(); 
                    	mothershipExplosions.addExplosionsHappening(new MothershipExplosions(),mothership.x,mothership.y);
                    	}
                    	
                    }
               		
               	   }
            		//upgrade player weapons and challenge player
                   	 if(mothershipCount == 3){
                   		spawnLaserUpgrade(mothership.x, mothership.y);
                   		hitCount = 4;
                   		
                   	 }else if(mothershipCount == 10){
                   		int z = 0;
                   		 while(z<=10){
                   			z++;
                   			spawnAsteroid();
                   		} 
                   		spawnBossShip();
					}else if(mothershipCount == 15){
						spawnLaserUpgrade(mothership.x, mothership.y);
						int z = 0;
                  		 while(z<=10){
                  			z++;
                  			spawnAsteroid();
                  		} 
                  		 mothershipCount++;
					}
                	
            	}
            }
        }
        
        //Create boss ship
        Iterator<Rectangle> bossIterator = bossShips.iterator();
        while(bossIterator.hasNext()) {
        	Rectangle bossShip = bossIterator.next();
        	
        	//create lasers
        	bossLaserDelay++;
            if(bossLaserDelay == 90){
            	spawnEnemyLaser(bossShip.x, bossShip.y);
            	spawnEnemyLaser(bossShip.x + 100, bossShip.y);
            	spawnEnemyLaser(bossShip.x + 200, bossShip.y);
            	spawnEnemyLaser(bossShip.x + 300, bossShip.y);
                bossLaserDelay = 0;
            }
            
            //detect if player gets hit by laser
            Iterator<Rectangle> enemyLaserIterator = enemyLasers.iterator();
            
            while(enemyLaserIterator.hasNext()) {
            	
            	Rectangle enemyLaser = enemyLaserIterator.next();
            	if(enemyLaser.overlaps(ship)){
        			enemyLaserIterator.remove();
        			health = health - 15;
            		mothershipLabel.setText("Health:" + health );
                	
        			
            	}
        	}
            //ship has reached bottom of screen create new one
        	if(bossShip.y + 64 < 0){
        		bossIterator.remove();
        		spawnBossShip();
        		
            
        		//detect boss ship collision with player ship
            }else if(bossShip.overlaps(ship)){
         	   
            	mothershipSound.play();
        		bossIterator.remove();
        		mothershipExplosions.addExplosionsHappening(new MothershipExplosions(),bossShip.x,bossShip.y);
        		spawnBossShip();
        		health = health - 35;
        		mothershipLabel.setText("Health:" + health );
         	   
            }
        	
        	//Detect if player has hit boss ship with laser
        	Iterator<Rectangle> laserIterator = lasers.iterator();
            
            while(laserIterator.hasNext()) {
            	Rectangle laser = laserIterator.next();
            	if(laser.overlaps(bossShip)){
            		mothershipSound.play();
            		laserIterator.remove();
            		
            		score += 50000;
            		asteroidLabel.setText("Score:"+score); 
            		mothershipExplosions.addExplosionsHappening(new MothershipExplosions(),bossShip.x,bossShip.y);
            		if(hitCount >= 4){
            			
            			
            			if(userLaser == 1){
            				hitCount ++;
                    	}else {
                    		hitCount = hitCount + 5;
                    	}
            			
            			//add multiple asteroids
            			Random rand = new Random();
            	        int x = rand.nextInt(3);
            			if((x == 1)){
                       		int z = 0;
                       		while(z<=10){
                       			z++;
                       			spawnAsteroid();
                       		}
                       		
                       		
                     	  }
            			//player wins
            			 if(hitCount >= 29){
                     		game.setScreen(new WinScreen(game));
                     		//add scre to google and local leaderboad
                     		game.actionResolver.submitScoreGPGS(score);
                     		scoreData.addNewScore(score);
                     		//check to see if boss was killed for the first time.
                        	boolean killedBossFirstTime = prefs.getBoolean("killedBossFirstTime", true);
                        	
                        	
                        	if (killedBossFirstTime){
                        		game.actionResolver.unlockAchievementGPGS("CgkIxt2hl7YKEAIQCw");
                        		prefs.putBoolean("killedBossFirstTime", false);
                            	prefs.flush();
                        	}
                     	  }
               	   }
            		
                	
            	}
            }
        }
    	
    	//upgrade player weapons 
        Iterator<Rectangle> laserUpgradeIterator = laserUpgrades.iterator();
        
        while(laserUpgradeIterator.hasNext()) {
        	
        	Rectangle laserUpgrade = laserUpgradeIterator.next();
    		if(laserUpgrade.overlaps(ship)) {
    			if(userLaser == 0){
    				asteroidSound.play();
    	         	  laserUpgradeIterator.remove();
    	               userLaser ++;
    	               spawnMothership();
            	}else if((userLaser == 1)||(userLaser == 2)) {
            		asteroidSound.play();
               	  laserUpgradeIterator.remove();
                     userLaser ++;
                     spawnMothership();
                	
            	}
         	   
            }
    	}
    	
    }
    
    //detect asteroid interaction
    private void checkAsteroid(){
    	//detect if game is paused
    	if( isPlaying ){
    		//set delay between asteroids
    		if(TimeUtils.nanoTime() - lastasteroidTime > 1000000000) spawnAsteroid();
            
            
            Iterator<Rectangle> asteroidIterator = asteroids.iterator();
            
            while(asteroidIterator.hasNext()) {
               Rectangle asteroid = asteroidIterator.next();
               //set speed of asteroid
               asteroid.y -= 800 * Gdx.graphics.getDeltaTime();
               
               //look to see if asteroid if off of screen
               if(asteroid.y + 64 < 0){
            	   asteroidIterator.remove();
            	   
            	//Detect asteroid collision with ship   
               }else if(asteroid.overlaps(ship)){
            	   
            	   asteroidSound.play();
                   asteroidIterator.remove();
                   asteroidExplosions.addExplosionsHappening(new AsteroidExplosions(),asteroid.x,asteroid.y);
                   health = health - 5;
           		mothershipLabel.setText("Health:" + health );
                   break;
            	   
               }
               
               //Detect if user laser hits asteroid
               Iterator<Rectangle> laserIterator = lasers.iterator();
               
               while(laserIterator.hasNext()) {
               	Rectangle laser = laserIterator.next();
               	if(asteroid.overlaps(laser)) {
               		asteroidExplosions.addExplosionsHappening(new AsteroidExplosions(),asteroid.x,asteroid.y);
               		asteroidSound.play();
               		asteroidIterator.remove();
               		laserIterator.remove();
               		score += 100;
               		asteroidLabel.setText("Score:"+score);
               		if(asteroidCount>0){
             		   asteroidCount --;
                 	    
                 	   if(asteroidCount == 0){
                 		   
                 		   spawnMothership();
                 		  game.actionResolver.incrementAchievementGPGS("CgkIxt2hl7YKEAIQCQ", 3);
                     	   }
             	   }
                   Random rand = new Random();
                   int x = rand.nextInt(3);
                   if(x == 2){
                	   spawnGem(asteroid.x, asteroid.y);
                   }
                   
               	}  
               }
            }
    	}
    	
    	//give player rewards
    	Iterator<Rectangle> gemIterator = gems.iterator();
        
        while(gemIterator.hasNext()) {
        	
        	Rectangle gem = gemIterator.next();
    		
    		if(gem.overlaps(ship)) {
    			if(userLaser == 0){
    				asteroidSound.play();
    	               gemIterator.remove();
    	               score += 1000;
    	          		asteroidLabel.setText("Score:"+score);
            	}else if((userLaser == 1)||(userLaser == 2)) {
            		asteroidSound.play();
                    gemIterator.remove();
                    health = health + 20;
               		mothershipLabel.setText("Health:" + health );
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
        //Check to see if game is paused
        
        if( isPlaying ){
        	
        	//add animations and graphics to screen
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
            //upgrade player weapons if player gets weapons upgrade
            for(Rectangle laser: lasers) {
            	if(userLaser == 0){
            		batch.draw(smallLaserImage, laser.x, laser.y);
            		laser.y += 600 * Gdx.graphics.getDeltaTime();
            	}else if(userLaser == 1){
            		batch.draw(tripleLaserImage, laser.x, laser.y);
            		laser.y += 750 * Gdx.graphics.getDeltaTime();
            	}else {
            		batch.draw(bigLaserImage, laser.x, laser.y);
            		laser.y += 900 * Gdx.graphics.getDeltaTime();
            	}
                
             }
            for(Rectangle enemylaser: enemyLasers) {
                batch.draw(enemyLaserImage, enemylaser.x, enemylaser.y);
                enemylaser.y -= 800 * Gdx.graphics.getDeltaTime();
                
             }
            for(Rectangle asteroid: asteroids) {
                batch.draw(asteroidImage, asteroid.x, asteroid.y);
             }
            //detect which part of game user is on to determine which reward plyaer recieves
            for(Rectangle gem: gems) {
            	if(userLaser ==0){
            		batch.draw(gemImage, gem.x, gem.y);
            	}else {
					batch.draw(healthImage, gem.x, gem.y);
				}
                
             }
            for(Rectangle laserUpgrade: laserUpgrades) {
                batch.draw(laserUpgradeImage, laserUpgrade.x, laserUpgrade.y);
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
        	//inform player game is paused
        	game.font.draw(batch, "Game Paused", Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        }
        
        batch.end();
        
        //check for collisions
        checkMothership();
        checkAsteroid();
     // process user input
        if(Gdx.input.isTouched()) {
           Vector3 touchPos = new Vector3();
           touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
           camera.unproject(touchPos);
        	 
           ship.x = touchPos.x - 64 / 2;
           ship.y = touchPos.y - 64 / 2;
           
           //fire laser
           laserDelay++;
           if(laserDelay == 50){
        	   if(userLaser <= 1){
        		   spawnLaser(ship.x + 20, ship.y + 35);
        		   shipSound.play(); 
           	}else if(userLaser >= 2){
           		spawnLaser(ship.x - 25, ship.y + 35);
           		spawnLaser(ship.x + 20, ship.y + 35);
           		spawnLaser(ship.x + 60, ship.y + 35);
           		shipSound.play(); 
           	}
        	   
               laserDelay = 0;
           }
            
           
        }
        //move player ship
        if(Gdx.input.isKeyPressed(Keys.ANY_KEY)){
        	if(Gdx.input.isKeyPressed(Keys.LEFT)) ship.x -= 200 * Gdx.graphics.getDeltaTime();
            if(Gdx.input.isKeyPressed(Keys.RIGHT)) ship.x += 200 * Gdx.graphics.getDeltaTime();
            
            if(Gdx.input.isKeyPressed(Keys.DOWN)) ship.y -= 200 * Gdx.graphics.getDeltaTime();
            if(Gdx.input.isKeyPressed(Keys.UP)) ship.y += 200 * Gdx.graphics.getDeltaTime();
        }
        
        //player dies no more health
      if(health <= 0){
    	game.setScreen(new LoseScreen(game));
    	game.actionResolver.submitScoreGPGS(score);
    	//check to see how many times user lost for negative achievement.
    	int loseAllot = prefs.getInteger("loseAllot", 0);
    	loseAllot++;
    	prefs.putInteger("loseAllot", loseAllot);
    	prefs.flush();
    	if (loseAllot == 5){
    		game.actionResolver.unlockAchievementGPGS("CgkIxt2hl7YKEAIQCw");
    	}
    	
    	scoreData.addNewScore(score);
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
