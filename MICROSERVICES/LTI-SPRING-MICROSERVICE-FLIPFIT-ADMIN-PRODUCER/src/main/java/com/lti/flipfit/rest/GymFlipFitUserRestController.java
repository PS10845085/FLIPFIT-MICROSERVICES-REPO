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

/**
 * REST controller that exposes admin-facing endpoints for user and customer administration.
 * <p>
 * Endpoints are mounted under <code>/api/admin</code> and return a unified
 * response envelope {@link RestApiResponse} for consistency. This controller
 * delegates business logic to {@link GymFlipFitUserService} and {@link GymFlipFitCustomerService}.
 * </p>
 *
 * <h2>Responsibilities</h2>
 * <ul>
 *   <li>Update a user's status (ACTIVE / INACTIVE / PENDING) by id.</li>
 *   <li>List all customers visible to admin.</li>
 *   <li>Return appropriate HTTP status codes and consistent response structure.</li>
 * </ul>
 *
 * <h2>Notes</h2>
 * <ul>
 *   <li>Consider securing these endpoints with role-based authorization (ADMIN-only).</li>
 *   <li>If the customer list grows large, add pagination and filtering to the service and controllers.</li>
 * </ul>
 *
 * @author FlipFit
 * @since 1.0
 */

@RestController
@RequestMapping("/api/admin")
public class GymFlipFitUserRestController {

	@Autowired
	private GymFlipFitUserService userService;
	
	@Autowired
	private GymFlipFitCustomerService customerService;

    /**
     * Updates the status of a user by id.
     * <p>
     * Accepts a validated {@link UserApproveDto} that contains the user's id and the desired status.
     * Delegates to {@link GymFlipFitUserService#updateUserStatusById(Long, com.lti.flipfit.constant.Status)} and
     * returns the updated domain entity in a {@link RestApiResponse} envelope with HTTP 200 OK.
     * </p>
     *
     * <h3>Endpoint</h3>
     * <pre>PUT /api/admin/update-user-status</pre>
     *
     * <h3>Sample Request</h3>
     * <pre>{@code
     * PUT /api/admin/update-user-status
     * Content-Type: application/json
     *
     * {
     *   "id": 3,
     *   "status": "ACTIVE"
     * }
     * }</pre>
     *
     * <h3>Responses</h3>
     * <ul>
     *   <li><b>200 OK</b>: User status updated successfully.</li>
     *   <li><b>400 Bad Request</b>: Validation failure (if DTO invalid).</li>
     *   <li><b>404 Not Found</b>: If service throws a not-found exception (depending on implementation).</li>
     * </ul>
     *
     * @param req the DTO containing user id and target status; validated via {@link Valid}
     * @return a {@link ResponseEntity} containing {@link RestApiResponse} with the updated {@link GymUser}
     */

	@PutMapping("/update-user-status")
    public ResponseEntity<RestApiResponse> updateUserStatusById(@Valid @RequestBody UserApproveDto req) {
    	GymUser updatedUser = userService.updateUserStatusById(req.getId(), req.getStatus());
      
        RestApiResponse response = new RestApiResponse("SUCCESS", "User status updated successfully ", updatedUser);

		return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Retrieves the complete list of customers for admin.
     * <p>
     * Delegates to {@link GymFlipFitCustomerService#getAllCustomerList()} and returns
     * the results wrapped in a {@link RestApiResponse} with HTTP 200 OK.
     * </p>
     *
     * <h3>Endpoint</h3>
     * <pre>GET /api/admin/customer-list</pre>
     *
     * <h3>Sample Response</h3>
     * <pre>{@code
     * {
     *   "status": "SUCCESS",
     *   "message": "Customer list fetched successfully !",
     *   "data": [ { ... }, { ... } ]
     * }
     * }</pre>
     *
     * @return a {@link ResponseEntity} containing {@link RestApiResponse} with the list of {@link GymCustomer}
     */

	@GetMapping("/customer-list")
	public ResponseEntity<RestApiResponse> customerList() {
		
		List<GymCustomer> userData = customerService.getAllCustomerList();

		RestApiResponse response = new RestApiResponse("SUCCESS", "Customer list fetched successfully !", userData);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
}
