package webAPI;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.Arrays;
import java.util.List;

import org.testng.annotations.Test;

import base.BaseTest;
import core.OutputLog;
import core.PropertyReader;
import core.StatusCode;
import io.restassured.response.Response;
import util.AccessToken;
import util.UserRequestSpecification;

import static io.restassured.RestAssured.*;

public class TestAlbum extends BaseTest {
		
	@Test(description="Get Artists Data", priority=1)
	public void getArtist() {
		int counter = 0;
		
		//System.out.println("*** : "+PropertyReader.getProperty("accessToken"));
		
		OutputLog.info(">>> Executing getArtist");
		
		String baseURL = PropertyReader.getProperty("version")+PropertyReader.getProperty("artist");
		
		String artistID = "70B80Lwx2sxti0M1Ng9e8K";
		
		Response response = given().spec(UserRequestSpecification.getInstance().getRequestSpecification())
				.auth().oauth2(PropertyReader.getProperty("accessToken")).when().get(baseURL+"/"+artistID);
		
		if(response.statusCode()==401 && counter < 3) { //Get new access token if existing on expired
			counter++;
			System.out.println("Under if condition");
			AccessToken.getAccessToken();
			System.out.println("### : "+PropertyReader.getProperty("accessToken"));
			getArtist();			
		}else {
			assertThat(response.getStatusCode(), is(StatusCode.SUCCESS.code));
			System.out.println("ArtistResponse : "+response.asString());
			OutputLog.info(">>> Executed");
		}
		
	}
	
	@Test(description="Get Album", priority=2)
	public void getAlbum() {
		int counter = 0;
		
		//System.out.println("*** : "+PropertyReader.getProperty("accessToken"));
		
		String baseURL = PropertyReader.getProperty("baseURI")+PropertyReader.getProperty("version")+PropertyReader.getProperty("album");
		
		String albumID = "4rvCQOx2G4DYIq2dnTIN5U";
		
		Response response = given().spec(UserRequestSpecification.getInstance().getRequestSpecification())
				.auth().oauth2(PropertyReader.getProperty("accessToken")).when().get(baseURL+"/"+albumID);
		
		if(response.statusCode()==401 && counter < 3) { //Get new access token if existing on expired
			counter++;
			System.out.println("Under if condition");
			AccessToken.getAccessToken();
			System.out.println("### : "+PropertyReader.getProperty("accessToken"));
			getArtist();			
		}else {
			assertThat(response.getStatusCode(), is(400));
			System.out.println("AlbumResponse : "+response.asString());
		}
	}
	
	@Test(description="Get Several Albums", priority=3)
	public void getSeveralAlbums() {
		int counter = 0;
		
		//System.out.println("*** : "+PropertyReader.getProperty("accessToken"));
		
		String baseURL = PropertyReader.getProperty("baseURI")+PropertyReader.getProperty("version")+PropertyReader.getProperty("album");
		
		List<String> ids = Arrays.asList("4rvCQOx2G4DYIq2dnTIN5U", "6vEROmmCdjaXEvh12gq0aQ", "7o0JYpcdfqQcMOufBCSWou");
		
		Response response = given().spec(UserRequestSpecification.getInstance().getRequestSpecification())
				.auth().oauth2(PropertyReader.getProperty("accessToken")).queryParam("ids", ids).when().get(baseURL);
		
		if(response.statusCode()==401 && counter < 3) { //Get new access token if existing on expired
			counter++;
			System.out.println("Under if condition");
			AccessToken.getAccessToken();
			System.out.println("### : "+PropertyReader.getProperty("accessToken"));
			getArtist();			
		}else {
			assertThat(response.getStatusCode(), is(200));
			System.out.println("AlbumResponse : "+response.asString());
		}
	}
	
	@Test(description="Get Album Tracks", priority=4)
	public void getAlbumTracks() {
		int counter = 0;
		
		//System.out.println("*** : "+PropertyReader.getProperty("accessToken"));
		
		String baseURL = PropertyReader.getProperty("baseURI")+PropertyReader.getProperty("version")+PropertyReader.getProperty("album");
		
		String albumID = "4rvCQOx2G4DYIq2dnTIN5U";
		
		Response response = given().spec(UserRequestSpecification.getInstance().getRequestSpecification())
				.auth().oauth2(PropertyReader.getProperty("accessToken")).when().get(baseURL+"/"+albumID+PropertyReader.getProperty("track"));
		
		if(response.statusCode()==401 && counter < 3) { //Get new access token if existing on expired
			counter++;
			System.out.println("Under if condition");
			AccessToken.getAccessToken();
			System.out.println("### : "+PropertyReader.getProperty("accessToken"));
			getArtist();			
		}else {
			assertThat(response.getStatusCode(), is(200));
			System.out.println("AlbumResponse : "+response.asString());
		}
	}
	
	@Test(description = "Get User's Saved Albums", priority=5)
	public void getUserSavedAlbums() {
		int counter = 0;
		
		String baseURL = PropertyReader.getProperty("baseURI")+PropertyReader.getProperty("version")+"/me"+PropertyReader.getProperty("album");
		
		String albumID = "4rvCQOx2G4DYIq2dnTIN5U";
		
		Response response = given().spec(UserRequestSpecification.getInstance().getRequestSpecification())
				.auth().oauth2(PropertyReader.getProperty("userLibReadToken")).when().get(baseURL);
		
		if(response.statusCode()==401 && counter < 3) { //Get new access token if existing on expired
			counter++;
			System.out.println("Under if condition");
			AccessToken.refreshUserLibReadToken();
			System.out.println("### : "+PropertyReader.getProperty("userLibReadToken"));
			getArtist();			
		}else {
			assertThat(response.getStatusCode(), is(200));
			System.out.println("AlbumResponse : "+response.asString());
		}
	}
	
	@Test(description="Save Album for current user", priority=6)
	public void saveAlbumForUser() {
		int counter = 0;
		
		String baseURL = PropertyReader.getProperty("baseURI")+PropertyReader.getProperty("version")+"/me"+PropertyReader.getProperty("album");
		
		String albumID = "2dYfxP5IZgWaQiMd5Rklhq";
		
		Response response = given().spec(UserRequestSpecification.getInstance().getRequestSpecification())
				.auth().oauth2(PropertyReader.getProperty("userLibModifyToken")).queryParam("ids", albumID).when().put(baseURL);
		
		if(response.statusCode()==401 && counter < 3) { //Get new access token if existing on expired
			counter++;
			System.out.println("Under if condition");
			AccessToken.refreshUserLibReadToken();
			System.out.println("### : "+PropertyReader.getProperty("userLibModifyToken"));
			getArtist();			
		}else {
			assertThat(response.getStatusCode(), is(200));
			OutputLog.info(">>> Status Code: "+response.statusCode());
			System.out.println("AlbumResponse : "+response.asString());
		}
	}
	
	@Test(description="Remove User's Saved Album", priority=7)
	public void removeAlbumForUser() {
		int counter = 0;
		
		String baseURL = PropertyReader.getProperty("baseURI")+PropertyReader.getProperty("version")+"/me"+PropertyReader.getProperty("album");
		
		String albumID = "2dYfxP5IZgWaQiMd5Rklhq";
		
		Response response = given().spec(UserRequestSpecification.getInstance().getRequestSpecification())
				.auth().oauth2(PropertyReader.getProperty("userLibModifyToken")).queryParam("ids", albumID).when().delete(baseURL);
		
		if(response.statusCode()==401 && counter < 3) { //Get new access token if existing on expired
			counter++;
			System.out.println("Under if condition");
			AccessToken.refreshUserLibReadToken();
			System.out.println("### : "+PropertyReader.getProperty("userLibModifyToken"));
			getArtist();			
		}else {
			assertThat(response.getStatusCode(), is(200));
			OutputLog.info(">>> Status Code: "+response.statusCode());
			System.out.println("AlbumResponse : "+response.asString());
		}
	}
	
	@Test(description="Get New Releases", priority=8)
	public void getNewRelease() {
		int counter = 0;
		
		//System.out.println("*** : "+PropertyReader.getProperty("accessToken"));
		
		String baseURL = PropertyReader.getProperty("baseURI")+PropertyReader.getProperty("version")+PropertyReader.getProperty("newRelease");
		
		Response response = given().spec(UserRequestSpecification.getInstance().getRequestSpecification())
				.auth().oauth2(PropertyReader.getProperty("accessToken")).when().get(baseURL);
		
		if(response.statusCode()==401 && counter < 3) { //Get new access token if existing on expired
			counter++;
			System.out.println("Under if condition");
			AccessToken.getAccessToken();
			System.out.println("### : "+PropertyReader.getProperty("accessToken"));
			getArtist();			
		}else {
			assertThat(response.getStatusCode(), is(200));
			System.out.println("AlbumResponse : "+response.asString());
		}
	}
	
}
