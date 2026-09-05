package com.api.test;

import static io.restassured.RestAssured.*;

import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;

import static com.api.constants.Role.*;
import static com.api.utils.AuthTokenProvider.*;

import static com.api.utils.ConfigManager.*;

public class CountAPITest {

	@Test
	public void verifyCountAPIResponse() {
		given().baseUri(getProperty("BASE_URI")).and().header("Authorization", getToken(FD)).log().uri().log().method().log().headers().and()
				.accept(ContentType.JSON).when().get("/dashboard/count").then().log().all().statusCode(200)
				.body("message", equalTo("Success")).time(lessThan(1000L)).body("data", notNullValue())
				.body("data.size()", equalTo(3)).body("data.count", everyItem(greaterThanOrEqualTo(0)))
				.body("data.label", everyItem(not(blankOrNullString()))).body("data.key", containsInAnyOrder("pending_for_delivery", "pending_fst_assignment", "created_today"))
				.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/CountResponseSchema-FD.json"));
	}

	@Test
	public void missingAuthToken() {
		given().baseUri(getProperty("BASE_URI")).log().uri().log().method().log().headers().and().when().get("/dashboard/count").then().log().all().statusCode(401);
	}

}
