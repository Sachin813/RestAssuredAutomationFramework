package com.api.test;

import static io.restassured.RestAssured.*;

import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

import com.ui.pojo.UserCredentials;

import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;

public class LoginAPITest {
	
	UserCredentials userCred = new UserCredentials("iamfd", "password");
	
	
	@Test
	public void loginAPITest() {
		given().baseUri("http://64.227.160.186:9000/v1").and().
		contentType(ContentType.JSON)
		.and().accept(ContentType.JSON).log().uri().and()
		.body(userCred)
		.log().method()
		.log().headers().
		log().body().and().when().post("login")
		.then().log().all().statusCode(200)
		.time(lessThan(1500L))
		.and().body("message", equalTo("Success")).and()
		.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema.json"));
		
	}

}
