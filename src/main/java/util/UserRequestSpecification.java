package util;

import core.PropertyReader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class UserRequestSpecification {

	private static UserRequestSpecification userRequestSpecification;
	private static RequestSpecification requestSpecification;
	
	private UserRequestSpecification() {}
	
	public static synchronized UserRequestSpecification getInstance() {
		if(userRequestSpecification==null) {
			userRequestSpecification = new UserRequestSpecification();
		}
		return userRequestSpecification;
	}
	
	public RequestSpecification getRequestSpecification() {
		System.out.println(">>> User Request Specification");
		requestSpecification = new RequestSpecBuilder()
				.setBaseUri(PropertyReader.getProperty("baseURI"))
				.build();
		return requestSpecification;
	}
	
	public RequestSpecification getAccessTokenSpecification() {
		System.out.println(">>> Access Token Specification");
		requestSpecification = new RequestSpecBuilder()
				.setBaseUri(PropertyReader.getProperty("accessTokenURI"))
				.build();
		return requestSpecification;
	}
	
}
