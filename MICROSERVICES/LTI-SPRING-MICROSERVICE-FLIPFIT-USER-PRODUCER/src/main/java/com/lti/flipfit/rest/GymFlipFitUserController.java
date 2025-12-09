package com.lti.flipfit.rest;

import java.util.List;  
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lti.flipfit.services.*;
import com.lti.flipfit.utils.RoleUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import com.lti.flipfit.dto.LoginDto;
import com.lti.flipfit.dto.UserDto;
import com.lti.flipfit.entity.GymUser;
import com.lti.flipfit.response.RestApiResponse;
import com.lti.flipfit.security.JwtService;

/**
 * REST controller responsible for user authentication operations.
 * <p>
 * Exposes endpoints under <code>/api/auth</code> for:
 * <ul>
 *   <li>User Registration</li>
 *   <li>User Login (JWT issuance)</li>
 * </ul>
 * </p>
 *
 * <h2>Responsibilities</h2>
 * <ul>
 *   <li>Validate incoming authentication-related requests.</li>
 *   <li>Delegate core business logic to {@link UserService}.</li>
 *   <li>Issue JWT tokens via {@link JwtService} for authenticated users.</li>
 * </ul>
 *
 * <h2>Notes</h2>
 * <ul>
 *   <li>Uses Spring Security's {@link AuthenticationManager} to authenticate credentials.</li>
 *   <li>Populates extra claims into JWT, including <code>roles</code>, <code>id</code>, and <code>userroleid</code>.</li>
 *   <li>Swagger/OpenAPI annotations document endpoints and expected responses.</li>
 * </ul>
 *
 * @author FlipFit
 * @since 1.0
 */

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Endpoints for user login and JWT")
public class GymFlipFitUserController {

	@Autowired
	private UserService userService;
	@Autowired
	private final AuthenticationManager authManager;
	@Autowired
	private final UserDetailsService uds;
	@Autowired
	private final JwtService jwtService;
	

/**
     * Constructs the {@code GymFlipFitUserController} with required dependencies.
     *
     * @param userService the user business service
     * @param authManager authentication manager to validate credentials
     * @param uds         user-details service to load principals
     * @param jwtService  JWT service for token creation
     */


	public GymFlipFitUserController(UserService userService, AuthenticationManager authManager, UserDetailsService uds,
			JwtService jwtService) {
		super();
		this.userService = userService;
		this.authManager = authManager;
		this.uds = uds;
		this.jwtService = jwtService;
	}

	 /**
	     * Registers a new user account.
	     * <p>
	     * Accepts a validated {@link UserDto} request body and forwards to the service layer.
	     * Returns a {@link RestApiResponse} wrapped in a {@link ResponseEntity}.
	     * </p>
	     *
	     * <h3>Endpoint</h3>
	     * <pre>POST /api/auth/register</pre>
	     *
	     * <h3>Sample Request</h3>
	     * <pre>{@code
	     * POST /api/auth/register
	     * Content-Type: application/json
	     *
	     * {
	     *   "username": "john",
	     *   "password": "secret",
	     *   "roleid": 3,
	     *   "centerid": 2,
	     *   "firstname": "John",
	     *   "lastname": "Doe",
	     *   "email": "john@example.com",
	     *   "mobile": 9876543210,
	     *   "status": "PENDING",
	     *   "line1": "Street 1",
	     *   "city": "New Delhi",
	     *   "state": "Delhi",
	     *   "postalcode": "110001",
	     *   "country": "India"
	     * }
	     * }</pre>
	     *
	     * <h3>Responses</h3>
	     * <ul>
	     *   <li><b>201 Created</b> or <b>200 OK</b>: depending on your service return.</li>
	     *   <li><b>400 Bad Request</b>: validation errors.</li>
	     * </ul>
	     *
	     * @param userDto the DTO containing user registration details; validated via {@link Valid}
	     * @return response entity containing API response with success or error information
	     * @see UserService#register(UserDto)
	     */

	@PostMapping("/register")
	public ResponseEntity<RestApiResponse> register(@RequestBody @Valid UserDto userDto) {
		return userService.register(userDto);
	}


	 /**
	     * Authenticates a user and issues a JWT token upon success.
	     * <p>
	     * Validates credentials via Spring Security's {@link AuthenticationManager},
	     * loads principal via {@link UserDetailsService}, enriches JWT with additional claims
	     * (role, id), and returns token payload in a unified {@link RestApiResponse}.
	     * </p>
	     *
	     * <h3>Endpoint</h3>
	     * <pre>POST /api/auth/login</pre>
	     *
	     * <h3>Sample Request</h3>
	     * <pre>{@code
	     * POST /api/auth/login
	     * Content-Type: application/json
	     *
	     * {
	     *   "username": "john",
	     *   "password": "secret"
	     * }
	     * }</pre>
	     *
	     * <h3>Responses</h3>
	     * <ul>
	     *   <li><b>200 OK</b>: Login successful (recommended).</li>
	     *   <li><b>401 Unauthorized</b>: Invalid credentials.</li>
	     * </ul>
	     *
	     * <h3>Payload</h3>
	     * <pre>{@code
	     * {
	     *   "token": "<jwt>",
	     *   "userName": "john",
	     *   "userId": 42,
	     *   "userRoleId": 3
	     * }
	     * }</pre>
	     *
	     * @param loginDto the DTO containing login credentials; validated via {@link Valid}
	     * @return response entity with JWT token and basic user info
	     */


	@Operation(summary = "Login user", description = "Authenticate user and return JWT token")
	    @ApiResponses({
	        @ApiResponse(responseCode = "200", description = "Login successful"),
	        @ApiResponse(responseCode = "401", description = "Invalid credentials")
	    })
	

	@PostMapping("/login")
	public ResponseEntity<RestApiResponse> login(@RequestBody @Valid LoginDto loginDto) {
	    
		// Authenticate credentials against configured AuthenticationManager
		authManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
		
		// Load principal (optional use for additional checks; kept for completeness)
		UserDetails user = uds.loadUserByUsername(loginDto.getUsername());
		 
		// Domain user entity
		GymUser userData = userService.getUserByUserName(loginDto.getUsername());

		// Optional: include authorities as a claim if you need them in the filter
		Map<String, Object> extraClaims = Map.of("roles",
				List.of(Map.of("authority", RoleUtils.mapRoleIdToRoleName(userData.getRoleid()))), "id",
				userData.getId(), "userroleid", userData.getRoleid());


        // Issue JWT with extra claims
		String token = jwtService.generateToken(userData, extraClaims);

        // Response payload
		Map<String, Object> payload = Map.of("token", token, "userName", userData.getUsername(), "userId", userData.getId(), "userRoleId",userData.getRoleid());

		RestApiResponse response = new RestApiResponse("SUCCESS", "Login successfully !", payload);
		
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
