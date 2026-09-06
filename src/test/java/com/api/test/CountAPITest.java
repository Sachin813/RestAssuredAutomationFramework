package com.api.test;

import static io.restassured.RestAssured.*;

import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

import com.api.utils.SpecUtil;

import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;

import static com.api.constants.Role.*;
import static com.api.utils.AuthTokenProvider.*;

import static com.api.utils.ConfigManager.*;

public class CountAPITest {

	@Test
	public void verifyCountAPIResponse() {
		given().spec(SpecUtil.requestSpecWithAuth(FD)).when().get("/dashboard/count").then().log().all().spec(SpecUtil.responseSpec_OK())
				.body("message", equalTo("Success")).body("data", notNullValue())
				.body("data.size()", equalTo(3)).body("data.count", everyItem(greaterThanOrEqualTo(0)))
				.body("data.label", everyItem(not(blankOrNullString()))).body("data.key", containsInAnyOrder("pending_for_delivery", "pending_fst_assignment", "created_today"))
				.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/CountResponseSchema-FD.json"));
	}

	@Test
	public void CountAPIMissingAuthToken() {
		given().spec(SpecUtil.requestSpec()).when().get("/dashboard/count").then().spec(SpecUtil.responseSpec_Text(401));
	}

}
