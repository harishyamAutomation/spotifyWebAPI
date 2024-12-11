package webAPI;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.testng.annotations.Test;

import base.BaseTest;
import core.Constants;
import core.OutputLog;
import core.PropertyReader;
import core.StatusCode;
import io.restassured.response.Response;
import util.JSONReader;
import util.UserRequestSpecification;

import static io.restassured.RestAssured.*;

public class TestAlbum extends BaseTest {
	int counter = 0;
		
	@Test(description="Get Artists Data", priority=1)
	public void getArtist() {
						
		String baseURL = PropertyReader.getProperty("version")+PropertyReader.getProperty("artist")+"/{artistID}";
		
		Response response = given().spec(UserRequestSpecification.getInstance().getRequestSpecification())
				.auth().oauth2(PropertyReader.getProperty("accessToken")).pathParam("artistID", JSONReader.getData(Constants.ALBUM_TEST_DATA, "artistID"))
				.when().get(baseURL);
		
		if(response.statusCode()==401 && counter < 3) { //Get new access token if existing one expired
			counter++;
			AccessToken.getAccessToken();
			getArtist();			
		}else {
			counter = 0;
			assertThat(response.getStatusCode(), is(StatusCode.SUCCESS.code));
			OutputLog.info("ArtistResponse : "+response.asString());
		}
		
	}
	
	@Test(description="Get Album", priority=2)
	public void getAlbum() {
				
		String baseURL = PropertyReader.getProperty("version")+PropertyReader.getProperty("album")+"/{albumID}";
				
		Response response = given().spec(UserRequestSpecification.getInstance().getRequestSpecification())
				.auth().oauth2(PropertyReader.getProperty("accessToken")).pathParam("albumID",  JSONReader.getData(Constants.ALBUM_TEST_DATA, "albumID"))
				.when().get(baseURL);
		
		if(response.statusCode()==401 && counter < 3) { //Get new access token if existing one expired
			counter++;
			AccessToken.getAccessToken();
			getAlbum();			
		}else {
			counter = 0;
			assertThat(response.getStatusCode(), is(200));
			OutputLog.info("AlbumResponse : "+response.asString());
		}
	}
	
	@Test(description="Get Several Albums", priority=3)
	public void getSeveralAlbums() {
				
		String baseURL = PropertyReader.getProperty("version")+PropertyReader.getProperty("album");
				
		String ids = StreamSupport.stream(JSONReader.getArray(Constants.ALBUM_TEST_DATA, "ids").spliterator(), false)
						.map(element -> element.getAsString())
						.collect(Collectors.joining(","));	
				
		Response response = given().spec(UserRequestSpecification.getInstance().getRequestSpecification())
				.auth().oauth2(PropertyReader.getProperty("accessToken")).queryParam("ids", ids).when().get(baseURL);
		
		if(response.statusCode()==401 && counter < 3) { //Get new access token if existing on expired
			counter++;
			AccessToken.getAccessToken();
			getSeveralAlbums();			
		}else {
			counter = 0;
			assertThat(response.getStatusCode(), is(200));
			OutputLog.info("AlbumResponse : "+response.asString());
		}
	}
	
	@Test(description="Get Album Tracks", priority=4)
	public void getAlbumTracks() {
				
		String baseURL = PropertyReader.getProperty("version")+PropertyReader.getProperty("album")+"/{albumID}"+PropertyReader.getProperty("track");

		//String albumID = "4rvCQOx2G4DYIq2dnTIN5U";
		
		Response response = given().spec(UserRequestSpecification.getInstance().getRequestSpecification())
				.auth().oauth2(PropertyReader.getProperty("accessToken")).pathParam("albumID", JSONReader.getData(Constants.ALBUM_TEST_DATA, "albumID"))
				.when().get(baseURL);
		
		if(response.statusCode()==401 && counter < 3) { //Get new access token if existing on expired
			counter++;
			AccessToken.getAccessToken();
			getAlbumTracks();			
		}else {
			counter = 0;
			assertThat(response.getStatusCode(), is(200));
			OutputLog.info("AlbumResponse : "+response.asString());
		}
	}
	
	@Test(description = "Get User's Saved Albums", priority=5)
	public void getUserSavedAlbums() {
		
		String baseURL = PropertyReader.getProperty("version")+"/me"+PropertyReader.getProperty("album");
		
		Response response = given().spec(UserRequestSpecification.getInstance().getRequestSpecification())
				.auth().oauth2(PropertyReader.getProperty("userLibReadToken")).when().get(baseURL);
		
		if(response.statusCode()==401 && counter < 3) { //Get new access token if existing on expired
			counter++;
			AccessToken.refreshUserLibReadToken();
			getUserSavedAlbums();			
		}else {
			counter = 0;
			assertThat(response.getStatusCode(), is(200));
			OutputLog.info("AlbumResponse : "+response.asString());
		}
	}
	
	@Test(description="Save Album for current user", priority=6)
	public void saveAlbumForUser() {
		
		String baseURL = PropertyReader.getProperty("version")+"/me"+PropertyReader.getProperty("album");
				
		Response response = given().spec(UserRequestSpecification.getInstance().getRequestSpecification())
				.auth().oauth2(PropertyReader.getProperty("userLibModifyToken")).queryParam("ids", JSONReader.getData(Constants.ALBUM_TEST_DATA, "saveAlbumID")).when().put(baseURL);
		
		if(response.statusCode()==401 && counter < 3) { //Get new access token if existing on expired
			counter++;
			AccessToken.refreshUserLibModifyToken();
			saveAlbumForUser();			
		}else {
			counter = 0;
			assertThat(response.getStatusCode(), is(200));
			OutputLog.info("AlbumResponse : "+response.asString());
		}
	}
	
	@Test(description="Remove User's Saved Album", priority=7)
	public void removeAlbumForUser() {
		
		String baseURL = PropertyReader.getProperty("version")+"/me"+PropertyReader.getProperty("album");
				
		Response response = given().spec(UserRequestSpecification.getInstance().getRequestSpecification())
				.auth().oauth2(PropertyReader.getProperty("userLibModifyToken")).queryParam("ids", JSONReader.getData(Constants.ALBUM_TEST_DATA, "saveAlbumID")).when().delete(baseURL);
		
		if(response.statusCode()==401 && counter < 3) { //Get new access token if existing on expired
			counter++;
			AccessToken.refreshUserLibModifyToken();
			removeAlbumForUser();			
		}else {
			counter = 0;
			assertThat(response.getStatusCode(), is(200));
			OutputLog.info("AlbumResponse : "+response.asString());
		}
	}
	
	@Test(description="Get New Releases", priority=8)
	public void getNewRelease() {
				
		String baseURL = PropertyReader.getProperty("baseURI")+PropertyReader.getProperty("version")+PropertyReader.getProperty("newRelease");
		
		Response response = given().spec(UserRequestSpecification.getInstance().getRequestSpecification())
				.auth().oauth2(PropertyReader.getProperty("accessToken")).when().get(baseURL);
		
		if(response.statusCode()==401 && counter < 3) { //Get new access token if existing on expired
			counter++;
			AccessToken.getAccessToken();
			getNewRelease();			
		}else {
			counter = 0;
			assertThat(response.getStatusCode(), is(200));
			OutputLog.info("AlbumResponse : "+response.asString());
		}
	}
	
}
