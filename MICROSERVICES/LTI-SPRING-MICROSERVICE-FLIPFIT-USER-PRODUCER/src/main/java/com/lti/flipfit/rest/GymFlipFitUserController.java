package com.lti.flipfit.rest;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lti.flipfit.services.*;

import jakarta.validation.Valid;

import com.lti.flipfit.dto.LoginDto;
import com.lti.flipfit.dto.UserDto;
import com.lti.flipfit.response.ApiResponse;



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
public class GymFlipFitUserController {
	
	@Autowired
	private UserService userService;
	


	/*
     * Registers a new user account.
     * <p>
     * Accepts a validated {@link UserDto} request body and forwards to the service layer.
     * Returns an {@link ApiResponse} wrapped in a {@link ResponseEntity}.
     *
     * <p><b>Endpoint:</b> <code>POST /api/auth/register</code></p>
     *
     * @param userDto the DTO containing user registration details; validated via {@link Valid}
     * @return response entity containing API response with success or error information
     *
     * @see UserService#register(UserDto)
     */


		@PostMapping("/register")
		    public ResponseEntity<ApiResponse> register(@RequestBody @Valid UserDto userDto) {
		        return userService.register(userDto);
		    }


	/*
	     * Authenticates a user and initiates a login session/token issuance.
	     * <p>
	     * Accepts a validated {@link LoginDto} request body and delegates authentication
	     * to the service layer. Returns an {@link ApiResponse} wrapped in a {@link ResponseEntity}.
	     *
	     * <p><b>Endpoint:</b> <code>POST /api/auth/login</code></p>
	     *
	     * @param loginDto the DTO containing login credentials (e.g., email/username and password); validated via {@link Valid}
	     * @return response entity containing API response indicating login success or failure
	     *
	     * @see UserService#login(LoginDto)
	     */


		@PostMapping("/login")
		    public ResponseEntity<ApiResponse> login(@RequestBody @Valid LoginDto loginDto) {
		        return userService.login(loginDto);
		    }



}
