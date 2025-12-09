
package com.lti.flipfit.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lti.flipfit.entity.GymRole;
import com.lti.flipfit.response.RestApiResponse;
import com.lti.flipfit.service.GymRoleService;

/**
 * REST controller that exposes endpoints for retrieving role metadata used across the application.
 * <p>
 * All endpoints under <code>/api/roles</code> are read-only and return a standardized
 * response envelope {@link RestApiResponse}. This controller delegates the retrieval
 * logic to {@link GymRoleService}.
 * </p>
 *
 * <h2>Responsibilities</h2>
 * <ul>
 *   <li>Expose an endpoint to list all available roles (e.g., OWNER, ADMIN, CUSTOMER).</li>
 *   <li>Return data in a consistent API envelope with status and message.</li>
 *   <li>Use appropriate HTTP status codes for read operations.</li>
 * </ul>
 *
 * <h2>Notes</h2>
 * <ul>
 *   <li>Consider adding Swagger annotations for better API documentation if needed.</li>
 *   <li>Endpoint currently unauthenticated—secure it if needed for your environment.</li>
 * </ul>
 *
 * @author FlipFit
 * @since 1.0
 */

@RestController
@RequestMapping("/api/roles")
public class GymRoleController {

    private final GymRoleService gymRoleService;

/**
     * Constructs the {@code GymRoleController} with the required service dependency.
     *
     * @param gymRoleService service abstraction that provides role metadata
     */

    public GymRoleController(GymRoleService gymRoleService) {
        this.gymRoleService = gymRoleService;
    }

/**
     * Retrieves all roles available in the system.
     * <p>
     * Delegates to {@link GymRoleService#getAllRoles()} and returns the results
     * wrapped in a {@link RestApiResponse} with a 200 OK status.
     * </p>
     *
     * <h3>Endpoint</h3>
     * <pre>GET /api/roles</pre>
     *
     * <h3>Sample Response</h3>
     * <pre>{@code
     * {
     *   "status": "SUCCESS",
     *   "message": "Roles fetched successfully !",
     *   "data": [
     *     { "id": 1, "name": "OWNER" },
     *     { "id": 2, "name": "ADMIN" },
     *     { "id": 3, "name": "CUSTOMER" }
     *   ]
     * }
     * }</pre>
     *
     * @return a {@link ResponseEntity} containing a {@link RestApiResponse} with the list of {@link GymRole} entities
     */

    @GetMapping
    public ResponseEntity<RestApiResponse> getAllRoles() {
        List<GymRole> roles = gymRoleService.getAllRoles();
        RestApiResponse response = new RestApiResponse("SUCCESS", "Roles fetched successfully !", roles);

		return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
