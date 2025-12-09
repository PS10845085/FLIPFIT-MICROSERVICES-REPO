package com.lti.flipfit.rest;

import java.util.List;  

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lti.flipfit.dto.CreateCenterRequest;
import com.lti.flipfit.dto.CenterApproveDto;
import com.lti.flipfit.entity.GymFlipFitCenter;
import com.lti.flipfit.response.RestApiResponse;
import com.lti.flipfit.service.GymFlipFitCenterService;

import jakarta.validation.Valid;


/**
 * REST controller that exposes endpoints for managing gym/center entities.
 * <p>
 * Endpoints are available under <code>/api</code> to list centers, fetch by id,
 * create, update, delete, and update center status. Responses for most endpoints
 * are wrapped with a unified envelope {@link RestApiResponse} for consistency.
 * </p>
 *
 * <h2>Responsibilities</h2>
 * <ul>
 *   <li>Delegate business operations to {@link GymFlipFitCenterService}.</li>
 *   <li>Return appropriate HTTP status codes for CRUD operations.</li>
 *   <li>Validate incoming DTOs via {@link jakarta.validation.Valid} for mutating endpoints.</li>
 * </ul>
 *
 * <h2>Notes</h2>
 * <ul>
 *   <li>Consider securing mutating endpoints with authentication & authorization.</li>
 *   <li>For consistency, you may refactor to use {@code @GetMapping}, {@code @PostMapping}, etc.</li>
 * </ul>
 *
 * @author FlipFit
 * @since 1.0
 */

@RestController
@RequestMapping("/api")

public class GymFlipFitCenterController {

	@Autowired
	private GymFlipFitCenterService centerService;


    // @Autowired
    // public CenterService centerService;

    /*
     * need to create BookingRequest DTO for request body
     * it includes userId, slotId, centerId
     */

    /**
     * Retrieves all centers available in the system.
     * <p>
     * Delegates to {@link GymFlipFitCenterService#findAllCenters()} and returns
     * the result wrapped in a {@link RestApiResponse} with HTTP 200 OK.
     * </p>
     *
     * <h3>Endpoint</h3>
     * <pre>GET /api/centers</pre>
     *
     * <h3>Sample Response</h3>
     * <pre>{@code
     * {
     *   "status": "SUCCESS",
     *   "message": "Centers fetched successfully !",
     *   "data": [ { ... }, { ... } ]
     * }
     * }</pre>
     *
     * @return response entity with a list of centers in a unified API envelope
     */


	@RequestMapping(value = "/centers", method = RequestMethod.GET)
	public ResponseEntity<RestApiResponse> getCenters() {
		List<GymFlipFitCenter> centerList = centerService.findAllCenters();
		RestApiResponse response = new RestApiResponse("SUCCESS", "Centers fetched successfully !", centerList);

		return ResponseEntity.status(HttpStatus.OK).body(response);
		
	}
	
	
	@RequestMapping(value = "/centers-list", method = RequestMethod.GET)
	public List<GymFlipFitCenter> getCentersList() {
		List<GymFlipFitCenter> centerList = centerService.findAllCenters();
		return centerList;
		
	}

/**
     * Retrieves all centers that are currently marked ACTIVE.
     * <p>
     * Delegates to {@link GymFlipFitCenterService#findAllActiveCenters()} and returns
     * the result wrapped in a {@link RestApiResponse} with HTTP 200 OK.
     * </p>
     *
     * <h3>Endpoint</h3>
     * <pre>GET /api/active-centers</pre>
     *
     * @return response entity containing only ACTIVE centers
     */

	@RequestMapping(value = "/active-centers", method = RequestMethod.GET)
	public ResponseEntity<RestApiResponse> getActiveCenters() {
		List<GymFlipFitCenter> centerList = centerService.findAllActiveCenters();
		RestApiResponse response = new RestApiResponse("SUCCESS", "Centers fetched successfully !", centerList);

		return ResponseEntity.status(HttpStatus.OK).body(response);
		
	}
	

    /**
     * Retrieves a single center by its id.
     * <p>
     * Returns 200 OK if found; otherwise returns 404 Not Found.
     * This endpoint returns the domain entity directly without the unified envelope,
     * which is acceptable but can be aligned with other endpoints for consistency.
     * </p>
     *
     * <h3>Endpoint</h3>
     * <pre>GET /api/centers/{id}</pre>
     *
     * @param id the unique identifier of the center to fetch
     * @return response entity with the center or 404 if absent
     */

	@RequestMapping(value = "/centers/{id}", method = RequestMethod.GET)
	public ResponseEntity<GymFlipFitCenter> getCenterById(@PathVariable Long id) {
		return centerService.findCenterById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

/**
     * Creates a new center using the provided request payload.
     * <p>
     * Validates the incoming {@link CreateCenterRequest}, persists the center, and
     * returns the saved entity wrapped in {@link RestApiResponse} with HTTP 201 Created.
     * </p>
     *
     * <h3>Endpoint</h3>
     * <pre>POST /api/createCenter</pre>
     *
     * @param createCenterRequest DTO containing center fields to create
     * @return response entity with the created center and 201 Created
     */

	@RequestMapping(value = "/createCenter", method = RequestMethod.POST)
	public ResponseEntity<RestApiResponse> createCenter(@RequestBody CreateCenterRequest createCenterRequest) {
		GymFlipFitCenter savedCenter = centerService.saveCenter(createCenterRequest);
		
		RestApiResponse response = new RestApiResponse("SUCCESS", "Center created successfully !", savedCenter);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

    /**
     * Updates an existing center by its id with the provided payload.
     * <p>
     * Returns 200 OK with the updated entity when successful; otherwise returns 404 Not Found.
     * </p>
     *
     * <h3>Endpoint</h3>
     * <pre>PUT /api/updateCenter/{id}</pre>
     *
     * @param id the id of the center to update
     * @param gymFlipFitCenter payload containing fields to update
     * @return response entity with the updated center or 404 if the id does not exist
     */

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

    /**
     * Deletes an existing center by its id.
     * <p>
     * Returns 204 No Content on success.
     * </p>
     *
     * <h3>Endpoint</h3>
     * <pre>DELETE /api/deleteCenter/{id}</pre>
     *
     * @param id the id of the center to delete
     * @return response entity with 204 No Content after successful deletion
     */

	@RequestMapping(value = "/deleteCenter/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteCenter(@PathVariable Long id) {
		centerService.deleteCenter(id);
		return ResponseEntity.noContent().build();
	}

	 /**
	     * Updates the status of a center (e.g., ACTIVE, INACTIVE, PENDING).
	     * <p>
	     * Accepts a validated {@link CenterApproveDto} with center id and the new status,
	     * delegates to the service layer to perform the update, and returns the updated
	     * entity wrapped in {@link RestApiResponse} with 200 OK.
	     * </p>
	     *
	     * <h3>Endpoint</h3>
	     * <pre>PUT /api/update-center-status</pre>
	     *
	     * <h3>Sample Request</h3>
	     * <pre>{@code
	     * PUT /api/update-center-status
	     * Content-Type: application/json
	     *
	     * {
	     *   "id": 2,
	     *   "status": "ACTIVE"
	     * }
	     * }</pre>
	     *
	     * @param req the DTO carrying center id and the desired status
	     * @return response entity containing the updated center
	     */

	@PutMapping("/update-center-status")
    public ResponseEntity<RestApiResponse> updateUserStatusById(@Valid @RequestBody CenterApproveDto req) {
		GymFlipFitCenter updatedCenter = centerService.updateCenterStatusById(req.getId(), req.getStatus());
      
        RestApiResponse response = new RestApiResponse("SUCCESS", "Center status updated successfully ", updatedCenter);

		return ResponseEntity.status(HttpStatus.OK).body(response);
    }
	

}
