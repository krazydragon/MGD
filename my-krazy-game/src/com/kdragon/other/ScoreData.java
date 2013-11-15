package com.kdragon.other;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;

public class ScoreData  {
	
	@SuppressWarnings("unused")
	private int scoreId;
	@SuppressWarnings("unused")
	private int score;
	@SuppressWarnings("unused")
	private String time;
	@SuppressWarnings("unused")
	private String name;
	private int[] topScoreData;
	private int[] todayData;
	private Preferences prefs;
	private boolean fileSaved;
	@SuppressWarnings("unused")
	private Date oldDate;
	DateFormat dateFormat;
	
	
	
	

	 public ScoreData()
	    {
		 
		 topScoreData = new int[10];
	     todayData = new int[10];
	     prefs = Gdx.app.getPreferences("My Preferences");
	     fileSaved = prefs.getBoolean("fileSaved");
	     String topScoreString = null;
	     String todayString = null;
	     dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	     if(fileSaved){
	    	 
	    	 FileHandle file = Gdx.files.local("topScores.txt");
	    	 topScoreString = file.readString();
	    	 
	    	 String savedDay = prefs.getString("date");
	    	 System.out.println(savedDay);
	    	 
	    	
	 		Date newDate = new Date();
	 		String newStr = dateFormat.format(newDate.getTime());
	 		System.out.println(newStr);
	 		if(savedDay.equals(newStr)){
	 			FileHandle file2 = Gdx.files.local("todayScores.txt");
	 			todayString = file2.readString();
	 		}
	     }
		 if(topScoreString != null){
			 
			 String[] items = topScoreString.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s+","").split(",");

			 
			 for (int i = 0; i < items.length; i++) {
			     try {
			    	 topScoreData[i] = Integer.parseInt(items[i]);
			     } catch (NumberFormatException nfe) {};
			 }
			 
			 
		 }
		 if(todayString != null){
			 String[] items2 = todayString.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s+","").split(",");

			 
			 for (int i = 0; i < items2.length; i++) {
			     try {
			    	 todayData[i] = Integer.parseInt(items2[i]);
			     } catch (NumberFormatException nfe) {};
			 }
		 }
	    }
	
	public void setName(String _name){
		name =_name;
	}
	
	public void setTime(String _time){
		time =_time;
	}
	
	public void setScore(int _score){
		score =_score;
	}
	
	public void addNewScore(int tempScore){
		
		
		int temp1 = tempScore;
		int temp2;
		
		
		
		for(int i =0; i < topScoreData.length; i++){
			if(temp1 > topScoreData[i]){
				temp2 = topScoreData[i];
				topScoreData[i] = temp1;
				temp1 = temp2;
			}
		}
		
		FileHandle file = Gdx.files.local("topScores.txt");
		file.writeString(Arrays.toString(topScoreData), false);
		
		if(!fileSaved){
			prefs.putBoolean("fileSaved", true);
			fileSaved = true;
			prefs.flush();
		}
		
		temp1 = tempScore;
		
		
		for(int i =0; i < todayData.length; i++){
			if(temp1 > todayData[i]){
				temp2 = todayData[i];
				todayData[i] = temp1;
				temp1 = temp2;
			}
		}
		
		FileHandle file2 = Gdx.files.local("todayScores.txt");
		file2.writeString(Arrays.toString(todayData), false);
		
		
		Date date = new Date();
		
		prefs.putString("date", dateFormat.format(date.getTime()));
		prefs.flush();
	}
	
	public int[] getgetTopScores(){
		
		return topScoreData;
	}

	public int[] getgetToDayScores() {
		// TODO Auto-generated method stub
		return todayData;
	}

}
