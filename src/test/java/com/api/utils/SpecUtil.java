package com.api.utils;

import static com.api.utils.ConfigManager.*;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.api.constants.Role;
import com.ui.pojo.UserCredentials;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecUtil {
	@Test
	public static RequestSpecification requestSpec() {
		RequestSpecification request = new RequestSpecBuilder().setBaseUri(getProperty("BASE_URI"))
				.setContentType(ContentType.JSON).setAccept(ContentType.JSON).log(LogDetail.URI).log(LogDetail.METHOD)
				.log(LogDetail.HEADERS).log(LogDetail.BODY).build();

		return request;
	}

	@Test
	public static RequestSpecification requestSpec(Object userCreds) {
		RequestSpecification request = new RequestSpecBuilder().setBaseUri(getProperty("BASE_URI"))
				.setContentType(ContentType.JSON).setAccept(ContentType.JSON).setBody(userCreds).log(LogDetail.URI)
				.log(LogDetail.METHOD).log(LogDetail.HEADERS).log(LogDetail.BODY).build();

		return request;
	}
	
	@Test
	public static RequestSpecification requestSpecWithAuth(Role role) {
		RequestSpecification request = new RequestSpecBuilder().setBaseUri(getProperty("BASE_URI"))
				.setContentType(ContentType.JSON).setAccept(ContentType.JSON).addHeader("Authorization", AuthTokenProvider.getToken(role)).log(LogDetail.URI)
				.log(LogDetail.METHOD).log(LogDetail.HEADERS).log(LogDetail.BODY).build();

		return request;
	}
	
	

	@Test
	public static RequestSpecification requestSpecWithAuth(Role role, Object payload) {
		RequestSpecification request = new RequestSpecBuilder().setBaseUri(getProperty("BASE_URI"))
				.setContentType(ContentType.JSON).setAccept(ContentType.JSON).addHeader("Authorization", AuthTokenProvider.getToken(role)).setBody(payload).log(LogDetail.URI)
				.log(LogDetail.METHOD).log(LogDetail.HEADERS).log(LogDetail.BODY).build();

		return request;
	}
	
	
	

	@Test
	public static ResponseSpecification responseSpec_OK() {
		ResponseSpecification response = new ResponseSpecBuilder().expectContentType(ContentType.JSON)
				.expectStatusCode(200).expectResponseTime(Matchers.lessThan(1000L)).log(LogDetail.ALL).build();

		return response;
	}
	
	@Test
	public static ResponseSpecification responseSpec_JSON(int statusCode) {
		ResponseSpecification response = new ResponseSpecBuilder().expectContentType(ContentType.JSON)
				.expectStatusCode(statusCode).expectResponseTime(Matchers.lessThan(1000L)).log(LogDetail.ALL).build();

		return response;
	}
	

	@Test
	public static ResponseSpecification responseSpec_Text(int statusCode) {
		ResponseSpecification response = new ResponseSpecBuilder()
				.expectStatusCode(statusCode).expectResponseTime(Matchers.lessThan(1000L)).log(LogDetail.ALL).build();

		return response;
	}

}
