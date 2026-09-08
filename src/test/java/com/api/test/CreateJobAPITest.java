package com.api.test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.constants.Model;
import com.api.constants.Problem;
import com.api.constants.Product;
import com.api.constants.Role;
import com.api.utils.DateTimeUtils;
import com.api.utils.SpecUtil;
import com.ui.pojo.CreateJobPayload;
import com.ui.pojo.Customer;
import com.ui.pojo.CustomerAddress;
import com.ui.pojo.CustomerProduct;
import com.ui.pojo.Problems;

import io.restassured.module.jsv.JsonSchemaValidator;

public class CreateJobAPITest {
	
	private CreateJobPayload createJobPayload;
	
	@BeforeMethod(description = "Create Payload for the CReate Job API")
	public void setup() {
		Customer customer = new Customer("Sachin", "Rana", "8130807959", "", "sr@gmail.com", "");
		CustomerAddress customerAddress = new CustomerAddress("c 304", "Jupiter", "MG Road", "Baniwala", "near isbt",
				"110085", "India", "Uttrakhand");
		CustomerProduct customerProduct = new CustomerProduct(DateTimeUtils.getTimeWithDaysAgo(10), "1200040122991812",
				"1200040122991812", "1200040122991812", DateTimeUtils.getTimeWithDaysAgo(10), Product.NEXUS_2.getCode(), Model.NEXUS_2_BLUE.getCode());
		Problems problems = new Problems(Problem.PHONE_OR_APP_CRASHES.getCode(), "Bettery Issue");
		List<Problems> problemsList = new ArrayList<>();
		problemsList.add(problems);

		createJobPayload = new CreateJobPayload(0, 2, 1, 1, customer, customerAddress, customerProduct,
				problemsList);
	}
	
	@Test(description = "Verify if create job api is able to create inwarrenty job " , groups = { "api", "regression", "smoke" })
	public void createJobAPITest() {

		

		given().spec(SpecUtil.requestSpecWithAuth(Role.FD, createJobPayload)).when().post("/job/create").then()
				.spec(SpecUtil.responseSpec_OK())
				.body(JsonSchemaValidator
						.matchesJsonSchemaInClasspath("response-schema/CreateJobResponseSchema-FD.json"))
				.body("message", equalTo("Job created successfully. ")).body("data.mst_service_location_id", equalTo(1))
				.body("data.job_number", startsWith("JOB_"));
		

	}
}
