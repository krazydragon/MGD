package com.kdragon.screens;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.kdragon.mygdxgame.KrazyGame;

public class MenuScreen implements Screen {
	
	private SpriteBatch batch;
	private Skin skin;
	private Stage stage;
    private static final float BUTTON_WIDTH = 300f;
    private static final float BUTTON_HEIGHT = 60f;
    private static final float BUTTON_SPACING = 10f;
    private Texture asteroidImage;
    private Array<Rectangle> asteroids;
    private long lastasteroidTime;
    private int screenWidth;
    private int screenHeight;
    
	final KrazyGame game;

    OrthographicCamera camera;

    public MenuScreen(final KrazyGame gam) {
    	
    	
    		screenHeight = Gdx.graphics.getHeight();
    		screenWidth =Gdx.graphics.getWidth();
    		
    		final float buttonX = ( screenWidth - BUTTON_WIDTH ) / 2;
    		float currentY = screenHeight/2;
            game = gam;
            batch = new SpriteBatch();
            camera = new OrthographicCamera();
            camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            
            
            
            stage = new Stage();
            Gdx.input.setInputProcessor(stage);
     
            asteroidImage = new Texture(Gdx.files.internal("asteroid.png"));
            Texture mainTexture = new Texture(Gdx.files.internal("menu.png"));
            Image mainImage = new Image( mainTexture);
            skin = new Skin();
            // Generate a 1x1 green texture and store it in the skin named "green".
            Pixmap pixmap = new Pixmap(100, 100, Format.RGBA8888);
            pixmap.setColor(Color.GREEN);
            pixmap.fill();
     
            skin.add("green", new Texture(pixmap));
            skin.add("default",game.font);
     
            // Configure a TextButtonStyle and name it "default". 
            TextButtonStyle textButtonStyle = new TextButtonStyle();
            textButtonStyle.up = skin.newDrawable("green", Color.DARK_GRAY);
            textButtonStyle.down = skin.newDrawable("green", Color.DARK_GRAY);
            textButtonStyle.checked = skin.newDrawable("green", Color.BLUE);
            textButtonStyle.over = skin.newDrawable("green", Color.LIGHT_GRAY);
     
            textButtonStyle.font = skin.getFont("default");
     
            skin.add("default", textButtonStyle);
     
            // Create a button with the "default" TextButtonStyle.
            final TextButton gameButton=new TextButton("PLAY",textButtonStyle);
            gameButton.addListener(new InputListener(){
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int    button){
                	if (game.actionResolver.getSignedInGPGS()) game.setScreen(new GameScreen(game));
                	else game.actionResolver.loginGPGS();
                	dispose();
                    return true;
                }
        	});
            
            gameButton.setBounds(buttonX, currentY, BUTTON_WIDTH, BUTTON_HEIGHT);
            
            
            final TextButton insructionButton = new TextButton("Global Leaderboard",textButtonStyle);
            insructionButton.addListener(new InputListener(){
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int    button){
                	if (game.actionResolver.getSignedInGPGS()) game.actionResolver.getLeaderboardGPGS();
                    else game.actionResolver.loginGPGS();
                	//game.setScreen(new HowToScreen(game));
                	dispose();
                    return true;
                }
        	});
            
            insructionButton.setBounds(buttonX, currentY -= BUTTON_HEIGHT + BUTTON_SPACING, BUTTON_WIDTH, BUTTON_HEIGHT);
            
            final TextButton localButton = new TextButton("Local LeaderBoard",textButtonStyle);
            localButton.addListener(new InputListener(){
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int    button){
                   
                	game.setScreen(new LeaderBoardScreen(game));
                	dispose();
                    return true;
                }
        	});
            localButton.setBounds(buttonX, currentY -= BUTTON_HEIGHT + BUTTON_SPACING, BUTTON_WIDTH, BUTTON_HEIGHT);
            
            final TextButton achievementButton = new TextButton("Achievements",textButtonStyle);
            achievementButton.addListener(new InputListener(){
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int    button){
                   
                	if (game.actionResolver.getSignedInGPGS()) game.actionResolver.getAchievementsGPGS();
                    else game.actionResolver.loginGPGS();
                	dispose();
                    return true;
                }
        	});
            
            achievementButton.setBounds(buttonX, currentY -= BUTTON_HEIGHT + BUTTON_SPACING, BUTTON_WIDTH, BUTTON_HEIGHT);
            
            final TextButton creditsButton = new TextButton("CREDITS",textButtonStyle);
            creditsButton.addListener(new InputListener(){
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int    button){
                   
                	game.setScreen(new CreditScreen(game));
                	dispose();
                    return true;
                }
        	});
            creditsButton.setBounds(buttonX, currentY -= BUTTON_HEIGHT + BUTTON_SPACING, BUTTON_WIDTH, BUTTON_HEIGHT);
            
            
            
            stage.addActor(mainImage);
            stage.addActor(gameButton);
            stage.addActor(insructionButton);
            stage.addActor(achievementButton);
            stage.addActor(localButton);
            stage.addActor(creditsButton);
            
            asteroids = new Array<Rectangle>();
            spawnAsteroid();

    }
    
    
    @Override
    public void render(float delta) {
            Gdx.gl.glClearColor(0, 0, 0.2f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            camera.update();
            stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
            stage.draw();
            batch.setProjectionMatrix(camera.combined);
            batch.begin();
            for(Rectangle asteroid: asteroids) {
                batch.draw(asteroidImage, asteroid.x, asteroid.y);
             }
            batch.end();
            
            checkAsteroid();
            
    }
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void dispose() {

		
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
	
private void checkAsteroid(){
    	
    	
    		if(TimeUtils.nanoTime() - lastasteroidTime > 1000000000) spawnAsteroid();
            
            
            Iterator<Rectangle> asteroidIterator = asteroids.iterator();
            
            while(asteroidIterator.hasNext()) {
               Rectangle asteroid = asteroidIterator.next();
               
               asteroid.y -= 800 * Gdx.graphics.getDeltaTime();
               
               if(asteroid.y + 64 < 0){
            	   asteroidIterator.remove();
            	   
            	   }
            	   
               }
    	
    }
}
