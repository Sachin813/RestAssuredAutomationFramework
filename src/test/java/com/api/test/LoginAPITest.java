package com.api.test;

import static io.restassured.RestAssured.*;

import static org.hamcrest.Matchers.*;

import java.io.IOException;

import org.testng.annotations.Test;

import static com.api.utils.ConfigManager.*;

import static com.api.utils.SpecUtil.*;
import com.ui.pojo.UserCredentials;

import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;

public class LoginAPITest {
	
	UserCredentials userCredentials = new UserCredentials("iamfd", "password");
	
	
	@Test
	public void loginAPITest() throws IOException {
		
		given().spec(requestSpec(userCredentials)).log().uri().and()
		.body(userCredentials)
		.when().post("login")
		.then().spec(responseSpec_OK())
		.and().body("message", equalTo("Success")).and()
		.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema.json"));
		
	}

}
