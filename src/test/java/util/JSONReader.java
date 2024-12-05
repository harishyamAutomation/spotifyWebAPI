package util;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class JSONReader {

	//Method to fetch the value for provided key
	public static String getData(Response response, String key) {
		String value = null;
		
		value = response.jsonPath().getString(key);
		
		return value;
	}
	
}
