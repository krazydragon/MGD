package com.kdragon.mygdxgame;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.kdragon.mygdxgame.KrazyGame;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "my-krazy-game";
		cfg.useGL20 = false;
		cfg.width = 1920;
		cfg.height = 1080;
		
		new LwjglApplication(new KrazyGame(new ActionResolverDesktop()), cfg);
	}
}
