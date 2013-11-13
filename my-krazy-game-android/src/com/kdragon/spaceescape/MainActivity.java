package com.kdragon.spaceescape;

import android.content.Intent;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.example.games.basegameutils.GameHelper;
import com.kdragon.mygdxgame.KrazyGame;
import com.kdragon.other.ActionResolver;

public class MainActivity extends AndroidApplication implements GameHelper.GameHelperListener, ActionResolver {
	protected  GameHelper gameHelper;

     public MainActivity(){
             gameHelper = new GameHelper(this);
             gameHelper.enableDebugLog(true, "GPGS");
     }
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGL20 = false;
        
        initialize(new KrazyGame(this), cfg);
        gameHelper.setup(this);
    }

	@Override
    public void onStart(){
            super.onStart();
            gameHelper.onStart(this);
    }

    @Override
    public void onStop(){
            super.onStop();
            gameHelper.onStop();

    }
    
    @Override
    public void onActivityResult(int request, int response, Intent data) {
            super.onActivityResult(request, response, data);
            gameHelper.onActivityResult(request, response, data);
    }
    
	@Override
	public boolean getSignedInGPGS() {
		return gameHelper.isSignedIn();
	}

	@Override
	public void loginGPGS() {
		 try {
             runOnUiThread(new Runnable(){
                     public void run() {
                             gameHelper.beginUserInitiatedSignIn();
                     }
             });
     } catch (final Exception ex) {
     }
		
	}

	@Override
	public void submitScoreGPGS(int score) {
		gameHelper.getGamesClient().submitScore("CgkIxt2hl7YKEAIQAQ", score);
		
	}

	@Override
	public void getLeaderboardGPGS() {
		startActivityForResult(gameHelper.getGamesClient().getLeaderboardIntent("CgkIxt2hl7YKEAIQAQ"), 100);
		
	}

	@Override
	public void onSignInFailed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSignInSucceeded() {
		// TODO Auto-generated method stub
		
	}
}