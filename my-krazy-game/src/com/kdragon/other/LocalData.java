package com.kdragon.other;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.OrderedMap;

public class LocalData  implements Json.Serializable{
	
	
	private Array topScores;
	private Array todayScores;
	

	 public LocalData()
	    {
	        
	    }
	@Override
	public void write(Json json) {
		// TODO Auto-generated method stub
		
		
		
	}

	@Override
	public void read(Json json, OrderedMap<String, Object> jsonData) {
		// TODO Auto-generated method stub
	}

}
