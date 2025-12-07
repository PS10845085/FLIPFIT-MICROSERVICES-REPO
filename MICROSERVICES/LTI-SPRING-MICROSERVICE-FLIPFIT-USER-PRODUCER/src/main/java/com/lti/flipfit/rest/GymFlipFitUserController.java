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

/*
 * REST controller responsible for user authentication operations.
 * <p>
 * Exposes endpoints under <code>/api/auth</code> for:
 * <ul>
 *   <li>User Registration</li>
 *   <li>User Login</li>
 * </ul>
 * Delegates all business logic to {@link UserService}.
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

	public GymFlipFitUserController(UserService userService, AuthenticationManager authManager, UserDetailsService uds,
			JwtService jwtService) {
		super();
		this.userService = userService;
		this.authManager = authManager;
		this.uds = uds;
		this.jwtService = jwtService;
	}

	/*
	 * Registers a new user account. <p> Accepts a validated {@link UserDto} request
	 * body and forwards to the service layer. Returns an {@link ApiResponse}
	 * wrapped in a {@link ResponseEntity}.
	 *
	 * <p><b>Endpoint:</b> <code>POST /api/auth/register</code></p>
	 *
	 * @param userDto the DTO containing user registration details; validated via
	 * {@link Valid}
	 * 
	 * @return response entity containing API response with success or error
	 * information
	 *
	 * @see UserService#register(UserDto)
	 */

	@PostMapping("/register")
	public ResponseEntity<RestApiResponse> register(@RequestBody @Valid UserDto userDto) {
		return userService.register(userDto);
	}

	/*
	 * Authenticates a user and initiates a login session/token issuance. <p>
	 * Accepts a validated {@link LoginDto} request body and delegates
	 * authentication to the service layer. Returns an {@link ApiResponse} wrapped
	 * in a {@link ResponseEntity}.
	 *
	 * <p><b>Endpoint:</b> <code>POST /api/auth/login</code></p>
	 *
	 * @param loginDto the DTO containing login credentials (e.g., email/username
	 * and password); validated via {@link Valid}
	 * 
	 * @return response entity containing API response indicating login success or
	 * failure
	 *
	 * @see UserService#login(LoginDto)
	 */

	@Operation(summary = "Login user", description = "Authenticate user and return JWT token")
	    @ApiResponses({
	        @ApiResponse(responseCode = "200", description = "Login successful"),
	        @ApiResponse(responseCode = "401", description = "Invalid credentials")
	    })
	

	@PostMapping("/login")
	public ResponseEntity<RestApiResponse> login(@RequestBody @Valid LoginDto loginDto) {

		authManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));

		UserDetails user = uds.loadUserByUsername(loginDto.getUsername());

		GymUser userData = userService.getUserByUserName(loginDto.getUsername());

		// Optional: include authorities as a claim if you need them in the filter
		Map<String, Object> extraClaims = Map.of("roles",
				List.of(Map.of("authority", RoleUtils.mapRoleIdToRoleName(userData.getRoleid()))), "id",
				userData.getId(), "userroleid", userData.getRoleid());

		String token = jwtService.generateToken(userData, extraClaims);

		Map<String, Object> payload = Map.of("token", token, "userName", userData.getUsername(), "userId", userData.getId(), "userRoleId",userData.getRoleid());

		RestApiResponse response = new RestApiResponse("SUCCESS", "Login successfully !", payload);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

}
