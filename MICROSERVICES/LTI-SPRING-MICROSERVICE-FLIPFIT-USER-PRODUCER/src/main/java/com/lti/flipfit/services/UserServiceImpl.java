package com.lti.flipfit.services;

import java.time.LocalDateTime; 
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lti.flipfit.dto.LoginDto;
import com.lti.flipfit.dto.UserDto;
import com.lti.flipfit.entity.GymAddress;
import com.lti.flipfit.entity.GymCustomer;
import com.lti.flipfit.entity.GymRole;
import com.lti.flipfit.entity.GymUser;
import com.lti.flipfit.repository.GymAddressRepository;
import com.lti.flipfit.repository.GymCustomerRepository;
import com.lti.flipfit.repository.GymRoleRepository;
import com.lti.flipfit.repository.GymUserRepository;
import com.lti.flipfit.response.ApiResponse;

import jakarta.transaction.Transactional;

import jakarta.validation.*;


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
	private final GymUserRepository userRepository;
	@Autowired
    private final GymAddressRepository addressRepository;
	@Autowired
    private final GymCustomerRepository customerRepository;
	@Autowired
    private final PasswordEncoder passwordEncoder;
	@Autowired
    private final GymRoleRepository roleRepository;


    // for checking caching is working or not
    private final AtomicInteger loginExecCount = new AtomicInteger();

    

	public UserServiceImpl(GymUserRepository userRepository, GymAddressRepository addressRepository, GymCustomerRepository customerRepository, GymRoleRepository roleRepository, PasswordEncoder passwordEncoder) {
	        this.userRepository = userRepository;
	        this.addressRepository = addressRepository;
	        this.customerRepository = customerRepository;
	        this.passwordEncoder = passwordEncoder;
	        this.roleRepository = roleRepository;
	    }


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
    @Transactional

    public ResponseEntity<ApiResponse> register(UserDto userDto) {
        // Validate username uniqueness
        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("FAILURE", "Username already exists", null));
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

        // Validate email & mobile (now belong to customer)
        if (userDto.getEmail() == null) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse("FAILURE", "Email is required", null));
        }
        if (userDto.getMobile() == null) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse("FAILURE", "Mobile is required", null));
        }

        // Validate address fields for customer
        if (userDto.getLine1() == null || userDto.getCity() == null || userDto.getPostalcode() == null) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse("FAILURE", "Address (line1, city, postal code) is required", null));
        }

        // 1) Create & save GymAddress (for customer)
        GymAddress address = new GymAddress();
        address.setLine1(userDto.getLine1());
        address.setLine2(userDto.getLine2());
        address.setCity(userDto.getCity());
        address.setState(userDto.getState());
        address.setPostalcode(userDto.getPostalcode());
        address.setCountry(userDto.getCountry());

        GymAddress savedAddress = addressRepository.save(address);

        // 2) Create minimal GymUser (no email/mobile/address here)
        GymUser gymUser = new GymUser();
        gymUser.setUsername(userDto.getUsername());
        gymUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        gymUser.setRoleid(userDto.getRoleid());
        gymUser.setStatus("PENDING"); // or "ACTIVE" per your flow
        gymUser.setCreatedAt(LocalDateTime.now());

        GymUser savedUser = userRepository.save(gymUser);
        // 3) Create GymCustomer linked to user & address
        GymCustomer customer = new GymCustomer();
        customer.setFirstname(userDto.getFirstname());        // add to DTO if not present
        customer.setLastname(userDto.getLastname());          // add to DTO if not present
        customer.setEmail(userDto.getEmail());
        customer.setMobile(userDto.getMobile());                             
        customer.setAddress(savedAddress);
        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());
        customer.setCenterid(userDto.getCenterid()); 
        customer.setUser(savedUser);

        GymCustomer savedCustomer = customerRepository.save(customer);
        
        GymRole customerRoleData = roleRepository.findById(savedUser.getRoleid())
	            .orElseThrow(() -> new RuntimeException("Role is not found"));

        // 4) Response (return both or just IDs)
        var payload = Map.of(
            "userId", savedUser.getId(),
            "customerId", savedCustomer.getId()
        );
        
        ApiResponse response = new ApiResponse("SUCCESS", savedCustomer.getFirstname() + " registered successfully as "+customerRoleData.getRolename(), payload);
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
		@Cacheable(value = "userCache", key = "#loginDto.username")
		public ResponseEntity<ApiResponse> login(LoginDto loginDto) {
			
		    // Validate username existence
			GymCustomer gymCustomer = customerRepository.findCustomerWithUserAndAddress(loginDto.getUsername(), "ACTIVE")
		            .orElseThrow(() -> new RuntimeException("User not found or Pending/Inactive"));

			//GymUser customer = gymUser.;
			
					
			//checking caching is working on not
		    System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++");
		    System.out.println("login executed count = " + loginExecCount.incrementAndGet());
		    
		    // Validate password
		    if (!passwordEncoder.matches(loginDto.getPassword(), gymCustomer.getUser().getPassword())) {
		        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
		                .body(new ApiResponse("FAILURE", "Invalid credentials", null));
		    }
		
		    var payload = Map.of(
		            "userFirstName", gymCustomer.getFirstname(),
		            "userLastName", gymCustomer.getLastname(),
		            "userMobile", gymCustomer.getMobile(),
		            "userEmail", gymCustomer.getEmail(),
		            "userAddressDetail", gymCustomer.getAddress()
		        );
		    // Return success response
		    ApiResponse response = new ApiResponse("SUCCESS", "Login successful", payload);
		    return ResponseEntity.status(HttpStatus.OK).body(response);
		}
		




}
