
package com.lti.flipfit.rest;


import com.lti.flipfit.response.RestApiResponse;
import com.lti.flipfit.dto.UserProfileRequestDto;
import com.lti.flipfit.entity.GymCustomer;
import com.lti.flipfit.services.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller that exposes endpoints related to the authenticated user's profile.
 * <p>
 * All endpoints under <code>/api/user</code> require a valid JWT token as per the
 * configured OpenAPI {@link SecurityRequirement}, making sure the consumer is authenticated.
 * </p>
 *
 * <h2>Responsibilities</h2>
 * <ul>
 *   <li>Receive profile requests with a validated payload.</li>
 *   <li>Delegate to {@link UserProfileService} to fetch the {@link GymCustomer} profile.</li>
 *   <li>Return a unified API response using {@link RestApiResponse} with appropriate HTTP status codes.</li>
 * </ul>
 *
 * <h2>Notes</h2>
 * <ul>
 *   <li>Swagger/OpenAPI annotations are used to document the API in UI (e.g., Swagger UI).</li>
 *   <li>Validation is performed using {@link jakarta.validation.Valid} for request DTO.</li>
 * </ul>
 *
 * @author FlipFit
 * @since 1.0
 */

@RestController
@RequestMapping("/api/user")
@Tag(name = "User Profile", description = "Endpoints to fetch user profile")
@SecurityRequirement(name = "BearerAuth") // requires JWT in Swagger UI
public class UserProfileController {

    private final UserProfileService userProfileService;

    /**
        * Constructs the {@code UserProfileController} with required service dependency.
        *
        * @param userProfileService the service layer dependency that handles user profile retrieval
        */

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    /**
        * Fetches a user's profile by user id.
        * <p>
        * Accepts a POST request with a JSON body containing the user id in {@link UserProfileRequestDto}.
        * Validates the payload, calls the service layer to fetch the user profile, and returns a
        * {@link RestApiResponse} object. The controller currently returns {@link HttpStatus#CREATED}
        * for the response; you may consider {@link HttpStatus#OK} (200) if no resource is being created.
        * </p>
        *
        * <h3>Security</h3>
        * <ul>
        *   <li>Requires a valid Bearer JWT token as per {@link SecurityRequirement}.</li>
        * </ul>
        *
        * <h3>Sample Request</h3>
        * <pre>{@code
        * POST /api/user/profile
        * Content-Type: application/json
        *
        * {
        *   "userid": 123
        * }
        * }</pre>
        *
        * <h3>Responses</h3>
        * <ul>
        *   <li><b>201 Created</b>: When profile is fetched successfully (current behavior).</li>
        *   <li><b>400 Bad Request</b>: When validation fails for the payload.</li>
        *   <li><b>401 Unauthorized</b>: When JWT token is missing/invalid.</li>
        *   <li><b>404 Not Found</b>: If the {@link UserProfileService} throws a not-found scenario (depending on implementation).</li>
        * </ul>
        *
        * @param req the validated request body containing the user id
        * @return a {@link ResponseEntity} wrapping {@link RestApiResponse} containing the {@link GymCustomer} profile
        */

    @Operation(summary = "Get user profile by id (POST)")
    @PostMapping("/profile")
    public ResponseEntity<RestApiResponse> getProfile(@Valid @RequestBody UserProfileRequestDto req) {
    	// Delegates the fetching logic to the service layer
    	GymCustomer profile = userProfileService.getUserProfileById(req.getUserid());
    	 // Builds a uniform response envelope for clients
        RestApiResponse response = new RestApiResponse("SUCCESS", "User profile fetched successfully ", profile);
     // Note: Using CREATED (201) for a GET-like operation is uncommon; OK (200) is typical.
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
