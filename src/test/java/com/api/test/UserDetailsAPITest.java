package com.api.test;

import static org.hamcrest.Matchers.*;

import java.io.IOException;

import org.testng.annotations.Test;

import com.api.utils.SpecUtil;

import static com.api.constants.Role.*;

import static com.api.utils.AuthTokenProvider.*;

import static com.api.utils.ConfigManager.*;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.module.jsv.JsonSchemaValidator;

import static io.restassured.RestAssured.*;

public class UserDetailsAPITest {
	
	
	@Test(description= "Verify if the UserDetails API response is shown correctly", groups = { "api", "regression", "smoke" })
	public void userDetailsAPITest() throws IOException {
		
		given().spec(SpecUtil.requestSpecWithAuth(FD)).when().get("userdetails").then().spec(SpecUtil.responseSpec_OK()).body("message", equalTo("Success")).and()
		.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/UserDetailsResponseSchema.json"));
	}
	
}
