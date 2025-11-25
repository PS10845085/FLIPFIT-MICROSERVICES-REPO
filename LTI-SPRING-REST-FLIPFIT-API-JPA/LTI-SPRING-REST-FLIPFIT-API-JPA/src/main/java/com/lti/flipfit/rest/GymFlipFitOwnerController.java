/**
 * 
 */
package com.lti.flipfit.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lti.flipfit.entity.GymFlipFitCenter;
import com.lti.flipfit.entity.GymFlipFitOwner;
import com.lti.flipfit.entity.GymFlipFitSlot;
import com.lti.flipfit.services.GymFlipFitCenterService;
import com.lti.flipfit.services.GymFlipFitOwnerService;

import jakarta.ws.rs.core.MediaType;


/**
 * REST controller for managing gym owners and centers in the GymFlipFit application.
 *
 * <p>This controller provides endpoints for CRUD operations on gym owners and gym centers.
 * It allows creating, updating, retrieving, and deleting owners and centers. All responses
 * are returned in JSON format.</p>
 *
 * <h2>Responsibilities:</h2>
 * <ul>
 *     <li>Expose REST endpoints for owner and center management</li>
 *     <li>Handle HTTP requests and delegate business logic to {@code GymFlipFitOwnerService} and {@code GymFlipFitCenterService}</li>
 * </ul>
 *
 * <h2>Endpoints:</h2>
 * <h3>Owner Management:</h3>
 * <ul>
 *     <li><b>GET /owner/owners</b> - Retrieve all owners</li>
 *     <li><b>GET /owner/owners/{id}</b> - Retrieve owner by ID</li>
 *     <li><b>POST /owner/createOwner</b> - Create a new owner</li>
 *     <li><b>PUT /owner/updateOwner/{id}</b> - Update owner details</li>
 *     <li><b>DELETE /owner/deleteOwner/{id}</b> - Delete an owner</li>
 * </ul>
 *
 * <h3>Center Management:</h3>
 * <ul>
 *     <li><b>GET /owner/centers</b> - Retrieve all centers</li>
 *     <li><b>GET /owner/centers/{id}</b> - Retrieve center by ID</li>
 *     <li><b>POST /owner/createCenter</b> - Create a new center</li>
 *     <li><b>PUT /owner/updateCenter/{id}</b> - Update center details</li>
 *     <li><b>DELETE /owner/deleteCenter/{id}</b> - Delete a center</li>
 * </ul>
 *
 * @author Team Bravo
 * @version v1.0.0
 * @see GymFlipFitOwner
 * @see GymFlipFitCenter
 * @see GymFlipFitOwnerService
 * @see GymFlipFitCenterService
 */

@RestController
//@RequestMapping("owner")
public class GymFlipFitOwnerController {

	@Autowired
	private GymFlipFitOwnerService ownerService;
	@Autowired
	public GymFlipFitCenterService centerService;

	@RequestMapping(value = "/owners", produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET)
	public List<GymFlipFitOwner> getAllOwners() {
		return ownerService.findAllOwners();
	}

	@RequestMapping(value = "/owners/{id}", produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET)
	public ResponseEntity<GymFlipFitOwner> getOwnerById(@PathVariable Long id) {
		return ownerService.findOwnerById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@RequestMapping(value = "owner/createOwner", produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST)
	public ResponseEntity<GymFlipFitOwner> createOwner(@RequestBody GymFlipFitOwner owner) {
		GymFlipFitOwner savedOwner = ownerService.saveOwner(owner);
		return new ResponseEntity<>(savedOwner, HttpStatus.CREATED);
	}

	@RequestMapping(value = "owner/updateOwner/{id}", produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT)
	public ResponseEntity<GymFlipFitOwner> updateOwner(@PathVariable Long id, @RequestBody GymFlipFitOwner ownerDetails) {
		GymFlipFitOwner updatedOwner = ownerService.updateOwner(id, ownerDetails);
		if (updatedOwner != null) {
			return ResponseEntity.ok(updatedOwner);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@RequestMapping(value = "owner/deleteOwner/{id}", produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteOwner(@PathVariable Long id) {
		ownerService.deleteOwner(id);
		return ResponseEntity.noContent().build();
	}

	/*
	 * need to create BookingRequest DTO for request body it includes userId,
	 * slotId, centerId
	 */

	@RequestMapping(value = "/owner/centers", produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET)
	public List<GymFlipFitCenter> getCenters() {
		return centerService.findAllCenters();
	}

	@RequestMapping(value = "/owner/centers/{id}", produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET)
	public ResponseEntity<GymFlipFitCenter> getCenterById(@PathVariable Long id) {
		return centerService.findCenterById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@RequestMapping(value = "/owner/createCenter", produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST)
	public ResponseEntity<GymFlipFitCenter> createCenter(@RequestBody GymFlipFitCenter gymFlipFitCenter) {
		GymFlipFitCenter savedCenter = centerService.saveCenter(gymFlipFitCenter);
		return new ResponseEntity<>(savedCenter, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/owner/updateCenter/{id}", produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT)
	public ResponseEntity<GymFlipFitCenter> updateCenter(@PathVariable Long id,
			@RequestBody GymFlipFitCenter gymFlipFitCenter) {
		GymFlipFitCenter updatedCenter = centerService.updateCenter(id, gymFlipFitCenter);
		if (updatedCenter != null) {
			return ResponseEntity.ok(updatedCenter);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@RequestMapping(value = "/owner/deleteCenter/{id}", produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteCenter(@PathVariable Long id) {
		centerService.deleteCenter(id);
		return ResponseEntity.noContent().build();
	}

	// create slot for the class for a centre
	/*
	 * @Method: createSlot
	 * @Description: creating slots
	 * @MethodParameters: Slot json data passing to create
	 * @Exception 
	 */
	@RequestMapping(value = "/owner/createSlot", produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST)
	public ResponseEntity<GymFlipFitSlot> createSlot(@RequestBody GymFlipFitSlot slot) {
		GymFlipFitSlot savedSlot = ownerService.saveSlot(slot);
		return new ResponseEntity<>(savedSlot, HttpStatus.CREATED);
	}

	// Update slot for the class for a centre
	/*
	 * @Method: updateSlot
	 * @Description: updating slots
	 * @MethodParameters: Slot json data passing to create
	 * @Exception 
	 */
	@RequestMapping(value = "/owner/updateSlot", produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST)
	public ResponseEntity<GymFlipFitSlot> updateSlot(@RequestBody GymFlipFitSlot slot) {
		GymFlipFitSlot savedSlot = ownerService.saveSlot(slot);
		return new ResponseEntity<>(savedSlot, HttpStatus.CREATED);
	}
}
