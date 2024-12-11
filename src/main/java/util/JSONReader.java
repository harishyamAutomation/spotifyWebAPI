package util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import core.OutputLog;
import io.restassured.response.Response;

public class JSONReader {

	//Method to fetch the value for provided key from the response
	public static String getData(Response response, String key) {
		String value = null;
		
		value = response.jsonPath().getString(key);
		
		return value;
	}
	
	public static JsonObject getJsonObject(String fileName) {
		OutputLog.info("TestData file path : "+fileName);
		JsonObject jsonObject = null;
		try {
			if(new File(fileName).exists()) {
				String file = Files.readString(Path.of(fileName));
				//jsonObject = new Gson().fromJson(file, JsonObject.class);
				jsonObject = JsonParser.parseString(file).getAsJsonObject();
				OutputLog.info("JSON Object : "+jsonObject.toString());
				return jsonObject;
			}else {
				throw new IllegalStateException("TestData file not found please verify path or filename");
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}
	
	//Method to fetch the value for provided key from the testData.json file
		public static String getData(String fileName, String key) {
			return getJsonObject(fileName).get(key).getAsString();
		}
		
		public static JsonArray getArray(String fileName, String key) {
			return getJsonObject(fileName).get(key).getAsJsonArray();
		}
	
}
