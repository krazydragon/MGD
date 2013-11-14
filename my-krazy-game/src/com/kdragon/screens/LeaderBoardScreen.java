package com.kdragon.screens;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.kdragon.mygdxgame.KrazyGame;
import com.kdragon.other.ScoreData;

public class LeaderBoardScreen implements Screen {

	
	private Stage stage;
	private Long startTime;
	
	
	final KrazyGame game;

    OrthographicCamera camera;

    public LeaderBoardScreen(final KrazyGame gam) {
    	
    	
    	
            game = gam;

            startTime = TimeUtils.millis();
            
            camera = new OrthographicCamera();
            camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            
            Texture.setEnforcePotImages(false);
            stage = new Stage(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),true);
            Texture howToTexture = new Texture(Gdx.files.internal("howto.png")); 
            Image howToImage = new Image( howToTexture);
            howToImage.setFillParent( true );

            Array<ScoreData> topScores = new Array<ScoreData>();
            
            ScoreData topScore = new ScoreData();
            
            topScore.setName("KrazyDragon");
            topScore.setScore(18);
            topScore.setTime("2013/11/14 01:18:29");
            
            topScores.add(topScore);
            
            ScoreData two = new ScoreData();
            
            two.setName("KrazyDragon");
            two.setScore(256);
            two.setTime("2013/11/14 01:18:29");
            
            topScores.add(two);
            
            ScoreData three = new ScoreData();
            
            three.setName("KrazyDragon");
            three.setScore(7);
            three.setTime("2013/11/14 01:18:29");
            
            topScores.add(three);
            
            ScoreData four = new ScoreData();
            
            four.setName("KrazyDragon");
            four.setScore(100);
            four.setTime("2013/11/14 01:18:29");
            
            topScores.add(four);
            
            
            ScoreData five = new ScoreData();
            
            five.setName("KrazyDragon");
            five.setScore(10000);
            five.setTime("2013/11/14 01:18:29");
            
            topScores.add(five);
            
            ScoreData six = new ScoreData();
             
            
            six.setName("KrazyDragon");
            six.setScore(5000);
            six.setTime("2013/11/14 01:18:29");
            
            topScores.add(six);
            
            
            
            System.out.println("****** Reverse Sorted String Array *******");
            for (ScoreData sd : topScores) {
                System.out.println(sd.score);
            }
            
            
            Label nameLabel = new Label(topScore.name, new Label.LabelStyle(game.font, Color.WHITE));
            Label nLabel = new Label(topScore.time, new Label.LabelStyle(game.font, Color.WHITE));
            Label addressLabel = new Label(String.valueOf(topScore.score), new Label.LabelStyle(game.font, Color.WHITE));
            Label aLabel = new Label("Address:", new Label.LabelStyle(game.font, Color.WHITE));
            

            Table table = new Table();
            table.columnDefaults(1).width(150);
            table.add(nameLabel).uniform();;
            table.add(nLabel).uniform();;
            table.row();
            table.add(addressLabel).uniform();;
            table.add(aLabel).uniform();;
            
            table.setFillParent(true);
            stage.addActor(table);
            

    }
    @Override
    public void render(float delta) {
            Gdx.gl.glClearColor(0, 0, 0.2f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            camera.update();
            stage.draw();
            if (TimeUtils.millis()>(startTime + 1000)){
            	if (Gdx.input.isTouched()) {
                    game.setScreen(new MenuScreen(game));
                    dispose();
           
                }
            }
            
            
    
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
}