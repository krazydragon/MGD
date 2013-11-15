package com.kdragon.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.TimeUtils;
import com.kdragon.mygdxgame.KrazyGame;
import com.kdragon.other.ScoreData;

public class LeaderBoardScreen implements Screen {

	
	private Stage stage;
	private Long startTime;
	boolean buttTogg;
	private static final float BUTTON_WIDTH = 300f;
    private static final float BUTTON_HEIGHT = 60f;
    private int screenWidth;
    private int screenHeight;
    final float buttonX = ( screenWidth - BUTTON_WIDTH ) / 2;
	
	
	final KrazyGame game;

    OrthographicCamera camera;

    public LeaderBoardScreen(final KrazyGame gam) {
    	
    	
    	
            game = gam;

            startTime = TimeUtils.millis();
            
            camera = new OrthographicCamera();
            camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            
            Texture.setEnforcePotImages(false);
            stage = new Stage(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),true);
            Gdx.input.setInputProcessor(stage);
            Texture howToTexture = new Texture(Gdx.files.internal("howto.png")); 
            Image howToImage = new Image( howToTexture);
            howToImage.setFillParent( true );

            
            
            ScoreData topScore = new ScoreData();
            
            
            
            //get scores
            final int[] topScores = topScore.getgetTopScores();
            final int[] todayScores = topScore.getgetToDayScores();
            
            
            
                for(int i =0; i < topScores.length; i++){
                System.out.println(topScores[i]);
                }
            
            
            ///add labels
            Label num1Label = new Label("#1", new Label.LabelStyle(game.font, Color.WHITE));
            Label uname1Label = new Label("Player 1", new Label.LabelStyle(game.font, Color.WHITE));
            final Label score1Label = new Label(String.valueOf(topScores[0]), new Label.LabelStyle(game.font, Color.WHITE));
            
            Label num2Label = new Label("#2", new Label.LabelStyle(game.font, Color.WHITE));
            Label uname2Label = new Label("Player 1", new Label.LabelStyle(game.font, Color.WHITE));
            final Label score2Label = new Label(String.valueOf(topScores[1]), new Label.LabelStyle(game.font, Color.WHITE));
            
            Label num3Label = new Label("#3", new Label.LabelStyle(game.font, Color.WHITE));
            Label uname3Label = new Label("Player 1", new Label.LabelStyle(game.font, Color.WHITE));
            final Label score3Label = new Label(String.valueOf(topScores[2]), new Label.LabelStyle(game.font, Color.WHITE));
            
            Label num4Label = new Label("#4", new Label.LabelStyle(game.font, Color.WHITE));
            Label uname4Label = new Label("Player 1", new Label.LabelStyle(game.font, Color.WHITE));
            final Label score4Label = new Label(String.valueOf(topScores[3]), new Label.LabelStyle(game.font, Color.WHITE));
            
            Label num5Label = new Label("#5", new Label.LabelStyle(game.font, Color.WHITE));
            Label uname5Label = new Label("Player 1", new Label.LabelStyle(game.font, Color.WHITE));
            final Label score5Label = new Label(String.valueOf(topScores[4]), new Label.LabelStyle(game.font, Color.WHITE));
            
            Label num6Label = new Label("#6", new Label.LabelStyle(game.font, Color.WHITE));
            Label uname6Label = new Label("Player 1", new Label.LabelStyle(game.font, Color.WHITE));
            final Label score6Label = new Label(String.valueOf(topScores[5]), new Label.LabelStyle(game.font, Color.WHITE));
            
            Label num7Label = new Label("#7", new Label.LabelStyle(game.font, Color.WHITE));
            Label uname7Label = new Label("Player 1", new Label.LabelStyle(game.font, Color.WHITE));
            final Label score7Label = new Label(String.valueOf(topScores[6]), new Label.LabelStyle(game.font, Color.WHITE));
            
            Label num8Label = new Label("#8", new Label.LabelStyle(game.font, Color.WHITE));
            Label uname8Label = new Label("Player 1", new Label.LabelStyle(game.font, Color.WHITE));
            final Label score8Label = new Label(String.valueOf(topScores[7]), new Label.LabelStyle(game.font, Color.WHITE));
            
            Label num9Label = new Label("#9", new Label.LabelStyle(game.font, Color.WHITE));
            Label uname9Label = new Label("Player 1", new Label.LabelStyle(game.font, Color.WHITE));
            final Label score9Label = new Label(String.valueOf(topScores[8]), new Label.LabelStyle(game.font, Color.WHITE));
            
            Label num10Label = new Label("#10", new Label.LabelStyle(game.font, Color.WHITE));
            Label uname10Label = new Label("Player 1", new Label.LabelStyle(game.font, Color.WHITE));
            final Label score10Label = new Label(String.valueOf(topScores[9]), new Label.LabelStyle(game.font, Color.WHITE));
            
            screenHeight = Gdx.graphics.getHeight();
    		screenWidth =Gdx.graphics.getWidth();
            
    		float currentY = screenHeight/2;
    		
    	    
            Skin skin = new Skin();
            // Generate a 1x1 green texture and store it in the skin named "green".
            Pixmap pixmap = new Pixmap(75, 25, Format.RGBA8888);
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
            
            

            //setup button
            final TextButton toggButton=new TextButton("Today's",textButtonStyle);
            toggButton.addListener(new InputListener(){
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int    button){
                	
                	if(!toggButton.isChecked()){
                		
                		score1Label.setText(String.valueOf(todayScores[0]));
                		score2Label.setText(String.valueOf(todayScores[1]));
                		score3Label.setText(String.valueOf(todayScores[2]));
                		score4Label.setText(String.valueOf(todayScores[3]));
                		score5Label.setText(String.valueOf(todayScores[4]));
                		score6Label.setText(String.valueOf(todayScores[5]));
                		score7Label.setText(String.valueOf(todayScores[6]));
                		score8Label.setText(String.valueOf(todayScores[7]));
                		score9Label.setText(String.valueOf(todayScores[8]));
                		score10Label.setText(String.valueOf(todayScores[9]));
                		toggButton.setText("All Time");
                		}else{
                			score1Label.setText(String.valueOf(topScores[0]));
                			score2Label.setText(String.valueOf(topScores[1]));
                			score3Label.setText(String.valueOf(topScores[2]));
                			score4Label.setText(String.valueOf(topScores[3]));
                			score5Label.setText(String.valueOf(topScores[4]));
                			score6Label.setText(String.valueOf(topScores[5]));
                			score7Label.setText(String.valueOf(topScores[6]));
                			score8Label.setText(String.valueOf(topScores[7]));
                			score9Label.setText(String.valueOf(topScores[8]));
                			score10Label.setText(String.valueOf(topScores[9]));
                			toggButton.setText("Today's");
                		}
                		
                	
                	
                    return true;
                }
        	});
            
            toggButton.setBounds(buttonX, currentY, BUTTON_WIDTH, BUTTON_HEIGHT);
            
            final TextButton backButton=new TextButton("Back",textButtonStyle);
            backButton.addListener(new InputListener(){
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int    button){
                	
                	game.setScreen(new MenuScreen(game));
                	
                	
                    return true;
                }
        	});
            
            backButton.setBounds(buttonX, currentY, BUTTON_WIDTH, BUTTON_HEIGHT);
            
            Table table = new Table();
            table.columnDefaults(1).width(150);
            
            
            table.add(toggButton);
            table.row();
            table.add(num1Label).uniform();
            table.add(uname1Label).uniform();
            table.add(score1Label).uniform();
            table.row();
            table.add(num2Label).uniform();
            table.add(uname2Label).uniform();
            table.add(score2Label).uniform();
            table.row();
            table.add(num3Label).uniform();
            table.add(uname3Label).uniform();
            table.add(score3Label).uniform();
            table.row();
            table.add(num4Label).uniform();
            table.add(uname4Label).uniform();
            table.add(score4Label).uniform();
            table.row();
            table.add(num5Label).uniform();
            table.add(uname5Label).uniform();
            table.add(score5Label).uniform();
            table.row();
            table.add(num6Label).uniform();
            table.add(uname6Label).uniform();
            table.add(score6Label).uniform();
            table.row();
            table.add(num7Label).uniform();
            table.add(uname7Label).uniform();
            table.add(score7Label).uniform();
            table.row();
            table.add(num8Label).uniform();
            table.add(uname8Label).uniform();
            table.add(score8Label).uniform();
            table.row();
            table.add(num9Label).uniform();
            table.add(uname9Label).uniform();
            table.add(score9Label).uniform();
            table.row();
            table.add(num10Label).uniform();
            table.add(uname10Label).uniform();
            table.add(score10Label).uniform();
            table.row();
           
            table.add(backButton);
            
            
            
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
                    //game.setScreen(new MenuScreen(game));
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