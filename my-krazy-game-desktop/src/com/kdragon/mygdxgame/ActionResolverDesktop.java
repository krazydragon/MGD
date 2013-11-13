package com.kdragon.mygdxgame;

import com.kdragon.other.ActionResolver;

public class ActionResolverDesktop implements ActionResolver{
	boolean signedInStateGPGS = false;

    @Override
    public boolean getSignedInGPGS() {
            return signedInStateGPGS;
    }

    @Override
    public void loginGPGS() {
            System.out.println("loginGPGS");
            signedInStateGPGS = true;
    }

    @Override
    public void submitScoreGPGS(int score) {
            System.out.println("submitScoreGPGS " + score);
    }


    @Override
    public void getLeaderboardGPGS() {
            System.out.println("getLeaderboardGPGS");
    }

    

}
