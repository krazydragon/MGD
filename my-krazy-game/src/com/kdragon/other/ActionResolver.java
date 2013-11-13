/*
 * project	my-krazy-game
 * 
 * package	com.kdragon.other
 * 
 * @author	Ronaldo Barnes
 * 
 * date		Nov 12, 2013
 */
package com.kdragon.other;


public interface ActionResolver {
	public boolean getSignedInGPGS();
    public void loginGPGS();
    public void submitScoreGPGS(int score);
    public void getLeaderboardGPGS();
}

