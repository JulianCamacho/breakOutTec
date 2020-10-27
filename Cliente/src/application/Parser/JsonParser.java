package application.Parser;

import com.google.gson.Gson;

public class JsonParser {
	
	public static void deserializeJson() {
		
		String userJson = "{'name': 'Jose'}";
		Gson gson = new Gson();
		
		JsonTestClass test = gson.fromJson(userJson, JsonTestClass.class);
		
		System.out.print(test.name);
		
	}
	
}
