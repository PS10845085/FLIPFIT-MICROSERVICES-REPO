package com.lti.flipfit.rest;

import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("center")
public class GymFlipFitCenterController {
	
	/*@Autowired
	public CenterService centerService;*/
 
	/*
	 * need to create BookingRequest DTO for request body it includes userId,
	 * slotId, centerId
	 */
 
	/*@RequestMapping(value = "/centers", produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET)
	public List<GymFlipFitCenter> getCenters() {
		return centerService.findAllCenters();
	}
 
	@RequestMapping(value = "/centers/{id}", produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET)
	public ResponseEntity<GymFlipFitCenter> getCenterById(@PathVariable Long id) {
		return centerService.findCenterById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}
 
	@RequestMapping(value = "/createCenter", produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST)
	public ResponseEntity<GymFlipFitCenter> createCenter(@RequestBody GymFlipFitCenter gymFlipFitCenter) {
		GymFlipFitCenter savedCenter = centerService.saveCenter(gymFlipFitCenter);
		return new ResponseEntity<>(savedCenter, HttpStatus.CREATED);
	}
 
	@RequestMapping(value = "/updateCenter/{id}", produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT)
	public ResponseEntity<GymFlipFitCenter> updateCenter(@PathVariable Long id,
			@RequestBody GymFlipFitCenter gymFlipFitCenter) {
		GymFlipFitCenter updatedCenter = centerService.updateCenter(id, gymFlipFitCenter);
		if (updatedCenter != null) {
			return ResponseEntity.ok(updatedCenter);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
 
	@RequestMapping(value = "/deleteCenter/{id}", produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteCenter(@PathVariable Long id) {
		centerService.deleteCenter(id);
		return ResponseEntity.noContent().build();
	}*/
}
