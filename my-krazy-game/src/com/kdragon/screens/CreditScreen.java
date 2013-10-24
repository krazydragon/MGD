package com.kdragon.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.TimeUtils;
import com.kdragon.mygdxgame.KrazyGame;

public class CreditScreen implements Screen {
	
	private Texture CreditTexture;
	private Image CreditImage;
	private Stage stage;
	private Long startTime;

	final KrazyGame game;

    OrthographicCamera camera;

    public CreditScreen(final KrazyGame gam) {
            game = gam;

            startTime = TimeUtils.millis();
            
            camera = new OrthographicCamera();
            camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            Texture.setEnforcePotImages(false);
            stage = new Stage(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),true);
            CreditTexture = new Texture(Gdx.files.internal("space.jpg")); 
            CreditImage = new Image( CreditTexture);
            CreditImage.setFillParent( true );

            
            stage.addActor( CreditImage );

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
		// TODO Auto-generated method stub
		
	}
}
