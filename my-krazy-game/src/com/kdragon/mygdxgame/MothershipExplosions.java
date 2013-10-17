package com.kdragon.mygdxgame;



import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MothershipExplosions {
    private static final int FRAME_COLS = 3;
    private static final int FRAME_ROWS = 4;
    private Animation explosionAnimation;  
    private Texture explosionSheet;   
    private TextureRegion[] explosionFrames; 
    private float stateTime;
    private TextureRegion currentFrame;



    private ArrayList<MothershipExplosions> explosionsHappening;
    public float posx,posy;
    
    public MothershipExplosions() {
        
        explosionsHappening = new ArrayList<MothershipExplosions>();
        explosionSheet = new Texture(Gdx.files.internal("bomb.png")); 
        TextureRegion[][] tmp = TextureRegion.split(explosionSheet, explosionSheet.getWidth() / FRAME_COLS, explosionSheet.getHeight() / FRAME_ROWS);                             
        explosionFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
                for (int j = 0; j < FRAME_COLS; j++) {
                        explosionFrames[index++] = tmp[i][j];
                }
        }
        explosionAnimation = new Animation(0.025f, explosionFrames);  
        stateTime = 0f;

    }
    
    
   
    
    public TextureRegion getCurrentFrame()
    {
        stateTime += Gdx.graphics.getDeltaTime();                  
        currentFrame = explosionAnimation.getKeyFrame(stateTime,false);  
        
        return currentFrame;
    }

    public ArrayList<MothershipExplosions> getExplosionsHappening() {
        return explosionsHappening;
    }

    public void addExplosionsHappening(MothershipExplosions exp,float posX,float posY) {
        exp.posx = posX;
        exp.posy = posY;
        this.explosionsHappening.add(exp);
    }
    

}
