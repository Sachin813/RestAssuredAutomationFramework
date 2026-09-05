package com.api.test;

import org.testng.annotations.Test;

import static com.api.constants.Role.*;
import com.api.utils.AuthTokenProvider;

import static com.api.utils.ConfigManager.*;

import static io.restassured.RestAssured.*;

import static org.hamcrest.Matchers.*;

import org.hamcrest.Matchers;

import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;

public class MasterAPITest {

	@Test
	public void verifyMasterAPIResponse() {
		given().baseUri(getProperty("BASE_URI")).and().header("Authorization", AuthTokenProvider.getToken(FD))
				.contentType("").and().log().uri().log().method().log().headers().and().when().post("/master").then()
				.log().all().statusCode(200).body("message", equalTo("Success")).time(lessThan(500L))
				.body("data", notNullValue()).body("data", hasKey("mst_oem")).body("data", hasKey("mst_oem"))
				.body("data", hasKey("mst_product")).body("$", hasKey("data")).body("$", hasKey("message"))
				.body("data.mst_oem.size()", equalTo(2)).body("data.mst_model.size()", equalTo(3))
				.body("data.mst_oem.id", everyItem(notNullValue())).body("data.mst_oem.name", everyItem(notNullValue())).body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/MasterAPIResponseSchema.json"));

	}
	
	
	
	@Test
	public void missingAuthTokenInMasterAPI() {
		given().baseUri(getProperty("BASE_URI")).header("Authorization", "").contentType("").log().uri().log().method().log().headers().when().post("/master").then().log().all().statusCode(401);
	}
}
