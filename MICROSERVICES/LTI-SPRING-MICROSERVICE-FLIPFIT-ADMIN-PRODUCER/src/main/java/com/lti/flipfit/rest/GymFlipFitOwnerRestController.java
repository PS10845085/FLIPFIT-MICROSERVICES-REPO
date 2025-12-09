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

/**
 * REST controller that exposes admin-facing endpoints for retrieving owner metadata.
 * <p>
 * Endpoints are mounted under <code>/api/admin</code> and return a unified
 * response envelope {@link RestApiResponse} for consistency across the API.
 * This controller delegates business logic to {@link GymFlipFitOwnerService}.
 * </p>
 *
 * <h2>Responsibilities</h2>
 * <ul>
 *   <li>Provide an endpoint to fetch the full list of Owners.</li>
 *   <li>Return consistent status and messaging via {@link RestApiResponse}.</li>
 *   <li>Use appropriate HTTP status codes (200 OK for successful reads).</li>
 * </ul>
 *
 * <h2>Notes</h2>
 * <ul>
 *   <li>Consider adding authentication/authorization (e.g., ADMIN-only) for this endpoint.</li>
 *   <li>If the list becomes large, add pagination and filtering to the service and controller.</li>
 * </ul>
 *
 * @author FlipFit
 * @since 1.0
 */

@RestController
@RequestMapping("/api/admin")
public class GymFlipFitOwnerRestController {

	@Autowired
	private GymFlipFitOwnerService ownerService;
	

    /**
     * Retrieves the complete list of owners.
     * <p>
     * Delegates to {@link GymFlipFitOwnerService#getAllOwnerList()} and returns the
     * results wrapped in a {@link RestApiResponse} with HTTP 200 OK.
     * </p>
     *
     * <h3>Endpoint</h3>
     * <pre>GET /api/admin/owner-list</pre>
     *
     * <h3>Sample Response</h3>
     * <pre>{@code
     * {
     *   "status": "SUCCESS",
     *   "message": "Owner list fetched successfully !",
     *   "data": [
     *     {
     *       "id": 1,
     *       "firstname": "Ankit",
     *       "lastname": "Singh",
     *       "email": "ankit@gmail.com",
     *       "mobile": 987654321,
     *       "center": { ... },
     *       "address": { ... },
     *       "createdAt": [2025,12,9,9,53,19,868398000],
     *       "updatedAt": [2025,12,9,9,53,19,868398000],
     *       "user": { "id": 3, "username": "Ankit", "roleid": 1, "status": "ACTIVE" }
     *     }
     *   ]
     * }
     * }</pre>
     *
     * @return a {@link ResponseEntity} containing a {@link RestApiResponse} with the list of {@link GymOwner} entities
     */

	@GetMapping("/owner-list")
	public ResponseEntity<RestApiResponse> customerList() {
		
		List<GymOwner> ownerData = ownerService.getAllOwnerList();

		RestApiResponse response = new RestApiResponse("SUCCESS", "Owner list fetched successfully !", ownerData);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
}
