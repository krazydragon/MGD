package com.kdragon.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.TimeUtils;
import com.kdragon.mygdxgame.KrazyGame;

public class WinScreen implements Screen {

	final KrazyGame game;
	private Long startTime;

    OrthographicCamera camera;

    public WinScreen(final KrazyGame gam) {
            game = gam;
            startTime = TimeUtils.millis();
            camera = new OrthographicCamera();
            camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    }
    @Override
    public void render(float delta) {
            Gdx.gl.glClearColor(0, 0, 0.2f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            camera.update();
            game.batch.setProjectionMatrix(camera.combined);

            game.batch.begin();
            game.font.draw(game.batch, "You Win!!! ", Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
            game.font.draw(game.batch, "Click anywhere to continue", Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2 - 30);
            game.batch.end();

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
