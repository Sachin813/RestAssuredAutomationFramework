package com.api.test;

import static com.api.utils.SpecUtil.requestSpec;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ui.pojo.UserCredentials;

import io.restassured.module.jsv.JsonSchemaValidator;

public class LoginAPITest {
	private UserCredentials userCredentials;

	@BeforeMethod(description = "create the payload for the login api")
	public void setup() {
		userCredentials = new UserCredentials("iamfd", "password");
	}

	@Test(description = "Verifying if Login API is working for user iamfd", groups = { "api", "regression", "smoke" })
	public void loginAPITest() throws IOException {

		given().spec(requestSpec(userCredentials)).log().uri().and().body(userCredentials).when().post("login").then()
				.spec(responseSpec_OK()).and().body("message", equalTo("Success")).and()
				.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema.json"));

	}

}
