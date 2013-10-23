package com.kdragon.mygdxgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class MenuScreen implements Screen {
	
	Skin skin;
    Stage stage;
    

	final KrazyGame game;

    OrthographicCamera camera;

    public MenuScreen(final KrazyGame gam) {
            game = gam;

            camera = new OrthographicCamera();
            camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            
            
            stage = new Stage();
            Gdx.input.setInputProcessor(stage);
     
            
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
                   
                	game.setScreen(new GameScreen(game));
                	dispose();
                    return true;
                }
        	});
            gameButton.setBounds(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, 256.0f, 64.0f);
            stage.addActor(gameButton);
            

    }
    @Override
    public void render(float delta) {
            Gdx.gl.glClearColor(0, 0, 0.2f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            camera.update();
            stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
            stage.draw();
            
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
		stage.dispose();
		
	}
}
