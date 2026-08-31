package com.api.test;

import static io.restassured.RestAssured.*;

import static org.hamcrest.Matchers.*;

import java.io.IOException;

import org.testng.annotations.Test;

import static com.api.utils.ConfigManager.*;
import com.ui.pojo.UserCredentials;

import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;

public class LoginAPITest {
	
	UserCredentials userCred = new UserCredentials("iamfd", "password");
	
	
	@Test
	public void loginAPITest() throws IOException {
		
		given().baseUri(getProperty("BASE_URI")).and().
		contentType(ContentType.JSON)
		.and().accept(ContentType.JSON).log().uri().and()
		.body(userCred)
		.log().method()
		.log().headers().
		log().body().and().when().post("login")
		.then().log().all().statusCode(200)
		.time(lessThan(2000L))
		.and().body("message", equalTo("Success")).and()
		.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema.json"));
		
	}

}
