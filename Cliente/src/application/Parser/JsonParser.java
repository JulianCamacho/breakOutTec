package application.Parser;

import com.google.gson.Gson;

public class JsonParser {
	
	public static void deserializeJson() {
		
		String userJson = "{'matrix':[[4,4,4,4,4,4,4,4,4,4,4,4,4,4],[4,4,0,4,4,4,4,4,4,4,4,4,4,4],[3,3,3,3,3,3,3,3,3,3,3,3,3,3],[3,3,3,3,3,3,3,3,3,3,3,0,3,3],[2,2,2,2,2,2,2,2,2,2,2,2,2,2],[2,2,2,2,2,2,2,2,2,2,0,2,2,2],[1,1,1,1,1,1,1,1,1,1,1,1,1,1],[1,1,1,1,1,1,1,1,1,1,1,1,1,1]],"
				+ "'ives':0,"
				+ "'ballQuantity':2,"
				+ "'ballSpeed':7,"
				+ "'ballPosition':[42,69],"
				+ "'racketLenght':20,"
				+ "'racketPosition':50,"
				+ "'level':1,"
				+ "'score':900,"
				+ "'greenBrickValue':100,"
				+ "'yellowBrickValue':200,"
				+ "'orangeBrickValue':300,"
				+ "'redBrickValue':400}"
		;
		Gson gson = new Gson();
		
		JsonTestClass test = gson.fromJson(userJson, JsonTestClass.class);
		
		System.out.print(test.greenBrickValue);
		
	}
	
}