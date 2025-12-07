package com.lti.flipfit.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lti.flipfit.entity.GymCustomer;
import com.lti.flipfit.response.RestApiResponse;
import com.lti.flipfit.services.GymFlipFitCustomerService;

@RestController
@RequestMapping("/api/customer")
public class GymFlipFitCustomerRestController {

	@Autowired
	private GymFlipFitCustomerService customerService;
	
	
	@GetMapping("/list")
	public ResponseEntity<RestApiResponse> customerList() {

		
		List<GymCustomer> userData = customerService.getAllCustomerList();

		
		RestApiResponse response = new RestApiResponse("SUCCESS", "Customer list fetched successfully !", userData);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
