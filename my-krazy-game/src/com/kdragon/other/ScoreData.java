package com.kdragon.other;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.OrderedMap;

public class ScoreData  implements Json.Serializable{
	
	
	public int score;
	public String time;
	public String name;
	

	 public ScoreData()
	    {
	        
	    }
	@Override
	public void write(Json json) {
		// TODO Auto-generated method stub
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        
        time = dateFormat.format(cal.getTime()); 
		json.writeValue( "time", time );
        json.writeValue( "score", score );
        json.writeValue( "name", name);
		
	}

	@Override
	public void read(Json json, OrderedMap<String, Object> jsonData) {

		ScoreData scoreData = new ScoreData();
		scoreData.score = json.readValue( "score", Integer.class, jsonData );
		scoreData.time = json.readValue( "time", String.class, jsonData );
		scoreData.name = json.readValue("name", String.class, jsonData);
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

}
