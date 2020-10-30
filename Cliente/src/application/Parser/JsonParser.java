package application.Parser;

import com.google.gson.Gson;

public class JsonParser {
	
	public static JsonTestClass deserializeJson(Integer opc) {
		
		String  userJson = "{'matrix':[[4,4,4,4,4,4,4,4,4,4,4,4,4,4],[4,4,0,4,4,4,4,4,4,4,4,4,4,4],[3,3,3,3,3,3,3,3,3,3,3,3,3,3],[3,3,3,3,3,3,3,3,3,3,3,0,3,3],[2,2,2,2,2,2,2,2,2,2,2,2,2,2],[2,2,2,2,2,2,2,2,2,2,0,2,2,2],[1,1,1,1,1,1,1,1,1,1,1,1,1,1],[1,1,1,1,1,1,1,1,1,1,1,1,1,1]],"
				+ "'lives':3,"
				+ "'ballQuantity':1,"
				+ "'ballSpeed':3,"
				+ "'ballPosition':[42,69],"
				+ "'racketLenght':200,"
				+ "'racketPosition':400,"
				+ "'level':1,"
				+ "'score':900,"
				+ "'greenBrickValue':100,"
				+ "'yellowBrickValue':200,"
				+ "'orangeBrickValue':300,"
				+ "'redBrickValue':400}"
		;
		
		if(opc == 1) {
		 userJson = "{'matrix':[[4,4,4,4,5,4,4,4,4,4,7,4,4,4],[4,4,0,4,4,4,4,4,4,4,4,4,4,4],[3,3,3,3,9,3,3,5,3,3,3,3,3,3],[3,3,3,3,3,6,3,3,3,3,3,0,3,3],[2,2,2,2,2,2,2,2,2,2,2,2,2,2],[2,2,2,2,2,2,2,2,2,2,0,2,2,2],[1,1,1,1,1,1,1,1,1,1,1,1,1,1],[1,1,1,1,1,1,1,1,1,1,1,1,1,1]],"
				+ "'lives':4,"
				+ "'ballQuantity':1,"
				+ "'ballSpeed':3,"
				+ "'ballPosition':[42,69],"
				+ "'racketLenght':200,"
				+ "'racketPosition':400,"
				+ "'level':1,"
				+ "'score':900,"
				+ "'greenBrickValue':100,"
				+ "'yellowBrickValue':200,"
				+ "'orangeBrickValue':300,"
				+ "'redBrickValue':400}"
		;
		}
		if(opc == -1) {
		userJson = "{'matrix':[[4,4,4,4,4,4,4,4,4,4,4,4,4,4],[4,4,0,4,4,10,4,4,4,4,4,4,4,4],[3,3,3,3,3,3,3,3,8,3,3,3,3,3],[3,3,3,3,3,9,3,3,3,3,3,0,3,3],[2,2,2,2,2,2,2,2,2,2,2,2,2,2],[2,2,2,2,2,2,2,2,2,2,0,2,2,2],[1,1,1,1,1,1,1,1,1,1,1,1,1,1],[1,1,1,1,1,1,1,1,1,1,1,1,1,5]],"
				+ "'lives':2,"
				+ "'ballQuantity':2,"
				+ "'ballSpeed':3,"
				+ "'ballPosition':[42,69],"
				+ "'racketLenght':200,"
				+ "'racketPosition':400,"
				+ "'level':10,"
				+ "'score':190000,"
				+ "'greenBrickValue':100,"
				+ "'yellowBrickValue':200,"
				+ "'orangeBrickValue':300,"
				+ "'redBrickValue':400}"
		;
		}
		Gson gson = new Gson();
		
		JsonTestClass json = gson.fromJson(userJson, JsonTestClass.class);
		
		return json;
		
	}
	
}
