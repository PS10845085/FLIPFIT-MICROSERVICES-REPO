package com.lti.flipfit.services;

import java.time.LocalDateTime;  

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lti.flipfit.dto.LoginDto;
import com.lti.flipfit.dto.UserDto;
import com.lti.flipfit.entity.GymUser;
import com.lti.flipfit.repository.GymUserRepository;
import com.lti.flipfit.response.ApiResponse;


/*
 * Service implementation for user-related operations such as registration and login.
 * <p>
 * Handles validation, password hashing, persistence, and response shaping for
 * authentication flows. This implementation uses {@link GymUserRepository} for data
 * access and {@link PasswordEncoder} for secure password storage/verification.
 * </p>
 *
 * <p><b>HTTP semantics used:</b></p>
 * <ul>
 *   <li>{@link HttpStatus#CREATED} (201) on successful registration</li>
 *   <li>{@link HttpStatus#BAD_REQUEST} (400) for validation failures</li>
 *   <li>{@link HttpStatus#UNAUTHORIZED} (401) for incorrect credentials</li>
 * </ul>
 *
 * <p><b>Security:</b> Passwords are encoded using {@link PasswordEncoder}.
 * Never return raw passwords or sensitive fields in responses.</p>
 */


@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private GymUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


/*
     * Registers a new {@link GymUser}.
     * <p>
     * Steps performed:
     * <ol>
     *   <li>Checks username uniqueness.</li>
     *   <li>Validates password minimum length (≥ 6 characters).</li>
     *   <li>Validates role presence.</li>
     *   <li>Encodes the password and persists the user.</li>
     *   <li>Returns a standardized {@link ApiResponse} with appropriate HTTP status.</li>
     * </ol>
     * </p>
     *
     * <p><b>Returns:</b></p>
     * <ul>
     *   <li>{@code 201 CREATED} with <code>SUCCESS</code> if registration succeeds.</li>
     *   <li>{@code 400 BAD_REQUEST} with <code>FAILURE</code> if validation fails.</li>
     * </ul>
     *
     * @param userDto Data transfer object containing username, password, and role id.
     * @return ResponseEntity containing an {@link ApiResponse} with status, message, and saved user.
     *
     * @implNote Passwords are stored as encoded hashes; ensure {@link PasswordEncoder} bean is configured.
     * @see GymUserRepository#findByUsername(String)
     * @see PasswordEncoder#encode(CharSequence)
     */

	@Override
	public ResponseEntity<ApiResponse> register(UserDto userDto) {
	    // Check username uniqueness

		if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
		        ApiResponse response = new ApiResponse("FAILURE", "Username already exists", null);
		        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		    }

	
	    // Validate password length
	    if (userDto.getPassword() == null || userDto.getPassword().length() < 6) {
	        return ResponseEntity.badRequest()
	                .body(new ApiResponse("FAILURE", "Password must be at least 6 characters", null));
	    }
	
	    // Validate role
	    if (userDto.getRoleid() == null) {
	        return ResponseEntity.badRequest()
	                .body(new ApiResponse("FAILURE", "Role is required", null));
	    }
	    
	 // Validate center
	    if (userDto.getCenterid() == null) {
	        return ResponseEntity.badRequest()
	                .body(new ApiResponse("FAILURE", "Center is required for which you're applying", null));
	    }
	    
	    
	 // Validate email
	    if (userDto.getEmail() == null) {
	        return ResponseEntity.badRequest()
	                .body(new ApiResponse("FAILURE", "Email is required", null));
	    }
		    
		    
	 // Validate mobile
	    if (userDto.getMobile() == null) {
	        return ResponseEntity.badRequest()
	                .body(new ApiResponse("FAILURE", "Mobile is required", null));
	    } 
	
	    // Create GymUser entity
	    GymUser gymUser = new GymUser();
	    gymUser.setUsername(userDto.getUsername());
	    gymUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
	    gymUser.setRoleid(userDto.getRoleid()); // Ensure GymRole exists
	    gymUser.setCenterid(userDto.getCenterid());
	    gymUser.setCreatedAt(LocalDateTime.now());
	    gymUser.setEmail(userDto.getEmail());
	    gymUser.setMobile(userDto.getMobile());
	    gymUser.setUserstatus("PENDING");
	
	    // Save user
	    GymUser savedUser = userRepository.save(gymUser);
	
	    // Return success response
	    ApiResponse response = new ApiResponse("SUCCESS", "User registered successfully", savedUser);
	    return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}




/*
     * Authenticates a user using username and password.
     * <p>
     * Steps performed:
     * <ol>
     *   <li>Fetches user by username; throws if not found.</li>
     *   <li>Verifies the provided password against the stored encoded hash.</li>
     *   <li>Returns a standardized {@link ApiResponse} with HTTP status.</li>
     * </ol>
     * </p>
     *
     * <p><b>Returns:</b></p>
     * <ul>
     *   <li>{@code 200 OK} with <code>SUCCESS</code> if authentication succeeds.</li>
     *   <li>{@code 401 UNAUTHORIZED} with <code>FAILURE</code> if password does not match.</li>
     *   <li>Throws {@link RuntimeException} if user is not found (consider mapping this to 404/401).</li>
     * </ul>
     *
     * @param loginDto DTO containing username and plain-text password for authentication.
     * @return ResponseEntity containing an {@link ApiResponse} with status, message, and user (or token, if implemented).
     *
     * @implNote Replace generic {@link RuntimeException} with a domain-specific exception for better error handling.
     * @see GymUserRepository#findByUsername(String)
     * @see PasswordEncoder#matches(CharSequence, String)
     */

		@Override
		public ResponseEntity<ApiResponse> login(LoginDto loginDto) {
		    // Validate username existence
		    GymUser gymUser = userRepository.findByUsernameAndUserstatus(loginDto.getUsername(), "ACTIVE")
		            .orElseThrow(() -> new RuntimeException("User not found or Pending/Inactive"));
		
		    // Validate password
		    if (!passwordEncoder.matches(loginDto.getPassword(), gymUser.getPassword())) {
		        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
		                .body(new ApiResponse("FAILURE", "Invalid credentials", null));
		    }
		
		
		    // Return success response with token
		    ApiResponse response = new ApiResponse("SUCCESS", "Login successful", gymUser);
		    return ResponseEntity.ok(response);
		}





}
