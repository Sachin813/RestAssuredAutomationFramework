package com.api.test;

import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import com.api.constants.Role;
import com.api.utils.SpecUtil;
import com.ui.pojo.CreateJobPayload;
import com.ui.pojo.Customer;
import com.ui.pojo.CustomerAddress;
import com.ui.pojo.CustomerProduct;
import com.ui.pojo.Problems;

import io.restassured.module.jsv.JsonSchemaValidator;

import static io.restassured.RestAssured.*;

public class CreateJobAPITest {

	@Test
	public void createJobAPITest() {
		Customer customer = new Customer("Sachin", "Rana", "8130807959", "", "sr@gmail.com", "");
		CustomerAddress customerAddress = new CustomerAddress("c 304", "Jupiter", "MG Road", "Baniwala", "near isbt",
				"110085", "India", "Uttrakhand");
		CustomerProduct customerProduct = new CustomerProduct("2025-04-06T18:30:00.000Z", "120504092990812",
				"120504092990812", "120504092990812", "2025-04-06T18:30:00.000Z", 1, 1);
		Problems problems = new Problems(1, "Bettery Issue");
		List<Problems> problemsList = new ArrayList<>();
		problemsList.add(problems);

		CreateJobPayload createJobPayload = new CreateJobPayload(0, 2, 1, 1, customer, customerAddress, customerProduct,
				problemsList);

		given().spec(SpecUtil.requestSpecWithAuth(Role.FD, createJobPayload)).when().post("/job/create").then()
				.spec(SpecUtil.responseSpec_OK())
				.body(JsonSchemaValidator
						.matchesJsonSchemaInClasspath("response-schema/CreateJobResponseSchema-FD.json"))
				.body("message", equalTo("Job created successfully. ")).body("data.mst_service_location_id", equalTo(1))
				.body("data.job_number", startsWith("JOB_"));
		

	}
}
