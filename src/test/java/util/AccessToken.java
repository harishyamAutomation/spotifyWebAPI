package util;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import core.PropertyReader;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class AccessToken {

	@Test
	public static void getAccessToken() {
		Map<String, String> requestBody = new HashMap<String, String>();
		requestBody.put("grant_type", "client_credentials");
		requestBody.put("client_id", "57285dd5a93a416b9a4fbd2210100a2d");
		requestBody.put("client_secret", "db328a1b29214aef8bf23fe58a374869");
		
		Response response = given().spec(UserRequestSpecification.getInstance().getAccessTokenSpecification())
				.contentType(ContentType.URLENC).formParams(requestBody).when().post();		
		assertThat(response.getStatusCode(), is(200));
		
		System.out.println(">>>> Access Token : "+JSONReader.getData(response, "access_token"));
		
		System.out.println(">>>> ResponseBody : "+response.body().asString());
		
		PropertyReader.setProperty("accessToken", JSONReader.getData(response, "access_token"));
	}
	
	@Test
	public static void getUserLibReadToken() {
		Map<String, String> requestBody = new HashMap<String, String>();
		
		//To get this code run this on your browser, https://accounts.spotify.com/authorize?client_id=57285dd5a93a416b9a4fbd2210100a2d&response_type=code&redirect_uri=http://localhost:8010/&scope=user-library-read
		String code = "AQAQkJTNHUCYjo7_UENQQpuLHZq2TuHCOZRn5WoyZUimt8KbYOo4dG5aeAQULI3cjlg2e_l-S-KLsk3dpiki7nMRGCNvn6idGjgMW202GDAs01dJS0DXFV7YrpWpIzNDQ0nDHKfBEH5r0b0oZlIrqYS8WrX2kvfWhq6l6BUu-TMoO5bQ_3adWi40TSJe";
		
		requestBody.put("grant_type", "authorization_code");
		requestBody.put("code", code);
		requestBody.put("redirect_uri", "http://localhost:8010/");
		requestBody.put("client_id", "57285dd5a93a416b9a4fbd2210100a2d");
		requestBody.put("client_secret", "db328a1b29214aef8bf23fe58a374869");
		
		Response response = given().spec(UserRequestSpecification.getInstance().getAccessTokenSpecification())
				.contentType(ContentType.URLENC).formParams(requestBody).when().post();
		
		assertThat(response.getStatusCode(), is(200));
		
		System.out.println(">>>> Access Token : "+JSONReader.getData(response, "access_token"));
		System.out.println(">>>> Refresh Token : "+JSONReader.getData(response, "refresh_token"));
		
		System.out.println(">>>> ResponseBody : "+response.body().asString());
		
		PropertyReader.setProperty("userLibReadToken", JSONReader.getData(response, "access_token"));
		PropertyReader.setProperty("userLibReadRefreshToken", JSONReader.getData(response, "refresh_token"));
	}
	
	@Test
	public static void refreshUserLibReadToken() {
		Map<String, String> requestBody = new HashMap<String, String>();
		
		requestBody.put("grant_type", "refresh_token");
		requestBody.put("refresh_token", PropertyReader.getProperty("userLibReadRefreshToken"));
		requestBody.put("client_id", "57285dd5a93a416b9a4fbd2210100a2d");
		requestBody.put("client_secret", "db328a1b29214aef8bf23fe58a374869");
				
		Response response = given().spec(UserRequestSpecification.getInstance().getAccessTokenSpecification())
				.contentType(ContentType.URLENC).formParams(requestBody).when().post();
		
		assertThat(response.getStatusCode(), is(200));
		
		//System.out.println(">>>> Access Token : "+JSONReader.getData(response, "access_token"));
		
		System.out.println(">>>> ResponseBody : "+response.body().asString());
		
		//System.out.println("Existing Refresh Token : "+PropertyReader.getProperty("userLibReadToken"));
		
		PropertyReader.setProperty("userLibReadToken", JSONReader.getData(response, "access_token"));	
		
		//System.out.println("Updated Refresh Token : "+PropertyReader.getProperty("userLibReadToken"));

		
	}
	
	@Test
	public static void getUserLibModifyToken() {
		Map<String, String> requestBody = new HashMap<String, String>();
		
		//To get this code run this on your browser, https://accounts.spotify.com/authorize?client_id=57285dd5a93a416b9a4fbd2210100a2d&response_type=code&redirect_uri=http://localhost:8010/&scope=user-library-read
		String code = "AQBu4WVsut_Y1YVTsVTmbFBM9FUvrkgQzU2KOayH16lRgr5i-YfKNCWkG2wDzz0ysnRI8R2-nQgwjQmhnSIW5OG2C5i5vqhuCoPK_leZxpu88Zi_V6RlRfpxCGHc_NiO8dcq4HQm5e7Hnh4CueTuy-eyz9VWk9Kt4UvyGV1UX1UTKxJU5Wn1oqo44KYnsqg";
		
		requestBody.put("grant_type", "authorization_code");
		requestBody.put("code", code);
		requestBody.put("redirect_uri", "http://localhost:8010/");
		requestBody.put("client_id", "57285dd5a93a416b9a4fbd2210100a2d");
		requestBody.put("client_secret", "db328a1b29214aef8bf23fe58a374869");
		
		Response response = given().spec(UserRequestSpecification.getInstance().getAccessTokenSpecification())
				.contentType(ContentType.URLENC).formParams(requestBody).when().post();
		
		assertThat(response.getStatusCode(), is(200));
		
		System.out.println(">>>> Access Token : "+JSONReader.getData(response, "access_token"));
		System.out.println(">>>> Refresh Token : "+JSONReader.getData(response, "refresh_token"));
		
		System.out.println(">>>> ResponseBody : "+response.body().asString());
		
		PropertyReader.setProperty("userLibModifyToken", JSONReader.getData(response, "access_token"));
		PropertyReader.setProperty("userLibModifyRefreshToken", JSONReader.getData(response, "refresh_token"));
	}
	
	@Test
	public static void refreshUserLibModifyToken() {
		Map<String, String> requestBody = new HashMap<String, String>();
		
		requestBody.put("grant_type", "refresh_token");
		requestBody.put("refresh_token", PropertyReader.getProperty("userLibModifyRefreshToken"));
		requestBody.put("client_id", "57285dd5a93a416b9a4fbd2210100a2d");
		requestBody.put("client_secret", "db328a1b29214aef8bf23fe58a374869");
				
		Response response = given().spec(UserRequestSpecification.getInstance().getAccessTokenSpecification())
				.contentType(ContentType.URLENC).formParams(requestBody).when().post();
		
		assertThat(response.getStatusCode(), is(200));
		
		//System.out.println(">>>> Access Token : "+JSONReader.getData(response, "access_token"));
		
		System.out.println(">>>> ResponseBody : "+response.body().asString());
		
		//System.out.println("Existing Refresh Token : "+PropertyReader.getProperty("userLibReadToken"));
		
		PropertyReader.setProperty("userLibModifyToken", JSONReader.getData(response, "access_token"));	
		
		//System.out.println("Updated Refresh Token : "+PropertyReader.getProperty("userLibReadToken"));

		
	}
}
