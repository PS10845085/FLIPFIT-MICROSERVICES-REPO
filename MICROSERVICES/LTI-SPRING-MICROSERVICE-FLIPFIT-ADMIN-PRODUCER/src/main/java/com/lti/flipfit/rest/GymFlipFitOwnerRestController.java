package com.lti.flipfit.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lti.flipfit.entity.GymOwner;
import com.lti.flipfit.response.RestApiResponse;
import com.lti.flipfit.services.GymFlipFitOwnerService;

@RestController
@RequestMapping("/api/admin")
public class GymFlipFitOwnerRestController {

	@Autowired
	private GymFlipFitOwnerService ownerService;
	
	
	@GetMapping("/owner-list")
	public ResponseEntity<RestApiResponse> customerList() {
		
		List<GymOwner> ownerData = ownerService.getAllOwnerList();

		RestApiResponse response = new RestApiResponse("SUCCESS", "Owner list fetched successfully !", ownerData);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
}
