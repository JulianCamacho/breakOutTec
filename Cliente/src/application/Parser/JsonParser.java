package application.Parser;

import com.google.gson.Gson;

public class JsonParser {
	
	public static JsonTestClass deserializeJson() {
		
		String userJson = "{'matrix':[[4,4,4,4,4,4,4,4,4,4,4,4,4,4],[4,4,0,4,4,4,4,4,4,4,4,4,4,4],[3,3,3,3,3,3,3,3,3,3,3,3,3,3],[3,3,3,3,3,3,3,3,3,3,3,0,3,3],[2,2,2,2,2,2,2,2,2,2,2,2,2,2],[2,2,2,2,2,2,2,2,2,2,0,2,2,2],[1,1,1,1,1,1,1,1,1,1,1,1,1,1],[1,1,1,1,1,1,1,1,1,1,1,1,1,1]],"
				+ "'lives':0,"
				+ "'ballQuantity':2,"
				+ "'ballSpeed':7,"
				+ "'ballPosition':[42,69],"
				+ "'racketLenght':20,"
				+ "'racketPosition':4,"
				+ "'level':1,"
				+ "'score':900,"
				+ "'greenBrickValue':100,"
				+ "'yellowBrickValue':200,"
				+ "'orangeBrickValue':300,"
				+ "'redBrickValue':400}"
		;
		Gson gson = new Gson();
		
		JsonTestClass json = gson.fromJson(userJson, JsonTestClass.class);
		
		return json;
		
	}
	
}
