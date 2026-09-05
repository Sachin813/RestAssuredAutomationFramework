package com.api.utils;

import static io.restassured.RestAssured.*;

import com.api.constants.Role;
import com.ui.pojo.UserCredentials;
import static org.hamcrest.Matchers.*;

import io.restassured.http.ContentType;

import static com.api.utils.ConfigManager.*;

public class AuthTokenProvider {

	private AuthTokenProvider() {

	}

	public static String getToken(Role role) {

		UserCredentials userCredentials = null;
		if (role == Role.FD) {
			userCredentials = new UserCredentials("iamfd", "password");
		} else if (role == Role.SUP) {
			userCredentials = new UserCredentials("iamsup", "password");
		} else if (role == Role.ENG) {
			userCredentials = new UserCredentials("iameng", "password");
		} else if (role == Role.QC) {
			userCredentials = new UserCredentials("iamqc", "password");
		}

		String token = given().baseUri(getProperty("BASE_URI")).and().contentType(ContentType.JSON).and()
				.accept(ContentType.JSON).log().uri().and().body(userCredentials).when()
				.post("login").then().log().all().and().statusCode(200).body("message", equalTo("Success")).extract()
				.jsonPath().getString("data.token");

		return token;

	}

}
