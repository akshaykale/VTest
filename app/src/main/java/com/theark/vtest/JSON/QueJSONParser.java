package com.theark.vtest.JSON;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.theark.vtest.Question;

public class QueJSONParser {
	
	public static List<Question> parseFeed( String content) {
		
		try {
			JSONArray jsonArray = new JSONArray(content);
			
			List<Question> produList = new ArrayList<>();
			
			for (int i=0; i<jsonArray.length(); i++){
				
				JSONObject obj = jsonArray.getJSONObject(i);
				
				Question product = new Question();
				
				product.setQuestion(obj.getString("question"));
				product.setOption_a(obj.getString("option_a"));
				product.setOption_b(obj.getString("option_b"));
				product.setOption_c(obj.getString("option_c"));
				product.setOption_d( obj.getString("option_d"));
				product.setAnswer(obj.getString("answer"));
				//product.setS_ans(obj.getString("s_ans"));
				
				
				produList.add(product);
			}
			
			return produList;
			
		} catch (JSONException e) {
			e.printStackTrace();
			Log.d("%%%%%","Errorrrr");
			return null;
		}
	}
	
	
	
	
	

	
	

}
