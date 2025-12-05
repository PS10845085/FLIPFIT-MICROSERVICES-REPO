package com.lti.flipfit.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lti.flipfit.dto.CreateCenterRequest;
import com.lti.flipfit.entity.GymFlipFitCenter;
import com.lti.flipfit.service.GymFlipFitCenterService;

@RestController
@RequestMapping("/api")
@CrossOrigin(
  origins = "http://localhost:4200",
  allowedHeaders = {"Authorization", "Content-Type", "Accept", "Origin", "X-Requested-With"},
  methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS},
  allowCredentials = "true"
)

public class GymFlipFitCenterController {

	@Autowired
	private GymFlipFitCenterService centerService;

	/*
	 * @Autowired public CenterService centerService;
	 */

	/*
	 * need to create BookingRequest DTO for request body it includes userId,
	 * slotId, centerId
	 */

	@RequestMapping(value = "/centers", method = RequestMethod.GET)
	public List<GymFlipFitCenter> getCenters() {
		return centerService.findAllCenters();
	}

	@RequestMapping(value = "/centers/{id}", method = RequestMethod.GET)
	public ResponseEntity<GymFlipFitCenter> getCenterById(@PathVariable Long id) {
		return centerService.findCenterById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@RequestMapping(value = "/createCenter", method = RequestMethod.POST)
	public ResponseEntity<GymFlipFitCenter> createCenter(@RequestBody CreateCenterRequest createCenterRequest) {
		GymFlipFitCenter savedCenter = centerService.saveCenter(createCenterRequest);
		return new ResponseEntity<>(savedCenter, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/updateCenter/{id}", method = RequestMethod.PUT)
	public ResponseEntity<GymFlipFitCenter> updateCenter(@PathVariable Long id,
			@RequestBody GymFlipFitCenter gymFlipFitCenter) {
		GymFlipFitCenter updatedCenter = centerService.updateCenter(id, gymFlipFitCenter);
		if (updatedCenter != null) {
			return ResponseEntity.ok(updatedCenter);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@RequestMapping(value = "/deleteCenter/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteCenter(@PathVariable Long id) {
		centerService.deleteCenter(id);
		return ResponseEntity.noContent().build();
	}

}
