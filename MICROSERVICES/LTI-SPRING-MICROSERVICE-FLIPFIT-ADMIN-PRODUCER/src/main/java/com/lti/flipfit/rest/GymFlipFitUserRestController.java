package com.lti.flipfit.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lti.flipfit.dto.UserApproveDto;
import com.lti.flipfit.entity.GymCustomer;
import com.lti.flipfit.entity.GymUser;
import com.lti.flipfit.response.RestApiResponse;
import com.lti.flipfit.services.GymFlipFitCustomerService;
import com.lti.flipfit.services.GymFlipFitUserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin")
public class GymFlipFitUserRestController {

	@Autowired
	private GymFlipFitUserService userService;
	
	@Autowired
	private GymFlipFitCustomerService customerService;
	
	@PutMapping("/update-user-status")
    public ResponseEntity<RestApiResponse> updateUserStatusById(@Valid @RequestBody UserApproveDto req) {
    	GymUser updatedUser = userService.updateUserStatusById(req.getId(), req.getStatus());
      
        RestApiResponse response = new RestApiResponse("SUCCESS", "User status updated successfully ", updatedUser);

		return ResponseEntity.status(HttpStatus.OK).body(response);
    }
	
	@GetMapping("/customer-list")
	public ResponseEntity<RestApiResponse> customerList() {
		
		List<GymCustomer> userData = customerService.getAllCustomerList();

		RestApiResponse response = new RestApiResponse("SUCCESS", "Customer list fetched successfully !", userData);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
}
