package com.lti.flipfit.services;

import java.time.LocalDateTime;  
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lti.flipfit.constants.ConstantLists;
import com.lti.flipfit.dto.LoginDto;
import com.lti.flipfit.dto.UserDto;
import com.lti.flipfit.entity.GymAddress;
import com.lti.flipfit.entity.GymAdmin;
import com.lti.flipfit.entity.GymCustomer;
import com.lti.flipfit.entity.GymOwner;
import com.lti.flipfit.entity.GymRole;
import com.lti.flipfit.entity.GymUser;
import com.lti.flipfit.repository.GymAddressRepository;
import com.lti.flipfit.repository.GymAdminRepository;
import com.lti.flipfit.repository.GymCustomerRepository;
import com.lti.flipfit.repository.GymOwnerRepository;
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
	private final GymAdminRepository adminRepository;

	@Autowired
	private final GymOwnerRepository ownerRepository;
	
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

    

	public UserServiceImpl(GymUserRepository userRepository, GymAddressRepository addressRepository, GymCustomerRepository customerRepository, GymRoleRepository roleRepository,GymAdminRepository adminRepository,GymOwnerRepository ownerRepository,  PasswordEncoder passwordEncoder) {
	        this.userRepository = userRepository;
	        this.addressRepository = addressRepository;
	        this.customerRepository = customerRepository;
	        this.adminRepository = adminRepository;
	        this.ownerRepository = ownerRepository;
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

        // 0) Validation (fast-fail)
        Optional<String> validationError = validateRegisterInput(userDto);
        if (validationError.isPresent()) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse("FAILURE", validationError.get(), null));
        }

        // Check username uniqueness
        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("FAILURE", "Username already exists", null));
        }
        
        // Fetch role to enrich response message
        GymRole customerRoleData = roleRepository.findById(userDto.getRoleid())
                .orElseThrow(() -> new RuntimeException("Role is not found"));

        // 1) Save address
        GymAddress savedAddress = saveAddress(userDto);

        // 2) Save user (minimal fields)
        GymUser savedUser = saveUser(userDto);

        
        // 3) Save customer, owner and admin  linking user + address
        
        
       if(ConstantLists.OWNER_ROLE_ID.equals(savedUser.getRoleid())  ) {
    	   GymOwner savedOwner = saveOwner(userDto, savedUser, savedAddress);
       }else if (ConstantLists.ADMIN_ROLE_ID.equals(savedUser.getRoleid()) ) {
    	   GymAdmin savedAdmin = saveAdmin(userDto, savedUser, savedAddress);
		}else if (ConstantLists.CUSTOMER_ROLE_ID.equals(savedUser.getRoleid()) ) {
	       GymCustomer savedCustomer = saveCustomer(userDto, savedUser, savedAddress);
		}



        Map<String, Object> payload = Map.of(
                "userId", savedUser.getId()
        );

        ApiResponse response = new ApiResponse(
                "SUCCESS",
                "Registered successfully as " + customerRoleData.getRolename(),
                payload
        );

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
		

	    // -----------------------------
	    // Validation
	    // -----------------------------
	    private Optional<String> validateRegisterInput(UserDto dto) {
	        if (dto.getUsername() == null || dto.getUsername().isBlank()) {
	            return Optional.of("Username is required");
	        }
	        if (dto.getPassword() == null || dto.getPassword().length() < 6) {
	            return Optional.of("Password must be at least 6 characters");
	        }
	        if (dto.getRoleid() == null) {
	            return Optional.of("Role is required");
	        }
	        if (dto.getEmail() == null) {
	            return Optional.of("Email is required");
	        }
	        if (dto.getMobile() == null) {
	            return Optional.of("Mobile is required");
	        }
	        if (dto.getLine1() == null || dto.getLine1().isBlank()
	                || dto.getCity() == null || dto.getCity().isBlank()
	                || dto.getPostalcode() == null || dto.getPostalcode().isBlank()) {
	            return Optional.of("Address (line1, city, postal code) is required");
	        }
	        return Optional.empty();
	    }
	    
	    



	    // -----------------------------
	    //  Helper methods
	    // -----------------------------

	    /**
	     * Persists a GymAddress derived from the UserDto.
	     */
	    private GymAddress saveAddress(UserDto dto) {
	        GymAddress address = new GymAddress();
	        address.setLine1(dto.getLine1());
	        address.setLine2(dto.getLine2());
	        address.setCity(dto.getCity());
	        address.setState(dto.getState());
	        address.setPostalcode(dto.getPostalcode());
	        address.setCountry(dto.getCountry());
	        return addressRepository.save(address);
	    }

	    /**
	     * Persists a GymUser (minimal user record; no email/mobile/address).
	     */
	    private GymUser saveUser(UserDto dto) {
	        GymUser gymUser = new GymUser();
	        gymUser.setUsername(dto.getUsername());
	        gymUser.setPassword(passwordEncoder.encode(dto.getPassword()));
	        gymUser.setRoleid(dto.getRoleid());
	        gymUser.setStatus("PENDING"); // or "ACTIVE" based on your flow
	        gymUser.setCreatedAt(LocalDateTime.now());
	        return userRepository.save(gymUser);
	    }

	    /**
	     * Persists a GymCustomer linked to a GymUser and GymAddress.
	     */
	    private GymCustomer saveCustomer(UserDto dto, GymUser user, GymAddress address) {
	        GymCustomer customer = new GymCustomer();
	        customer.setFirstname(dto.getFirstname());
	        customer.setLastname(dto.getLastname());
	        customer.setEmail(dto.getEmail());
	        customer.setMobile(dto.getMobile());
	        customer.setAddress(address);
	        customer.setCreatedAt(LocalDateTime.now());
	        customer.setUpdatedAt(LocalDateTime.now());
	        customer.setCenterid(dto.getCenterid());
	        customer.setUser(user);
	        return customerRepository.save(customer);
	    }
	    
	    
	    /**
	     * Persists a GymOwner linked to a GymUser and GymAddress. 
	     */
	    private GymOwner saveOwner(UserDto userDto, GymUser savedUser, GymAddress savedAddress) {
	    	GymOwner owner = new GymOwner();
	        owner.setFirstname(userDto.getFirstname());
	        owner.setLastname(userDto.getLastname());
	        owner.setEmail(userDto.getEmail());
	        owner.setMobile(userDto.getMobile());
	        owner.setAddress(savedAddress);
	        owner.setCreatedAt(LocalDateTime.now());
	        owner.setUpdatedAt(LocalDateTime.now());
	        owner.setCenterid(userDto.getCenterid());
	        owner.setUser(savedUser);
	        return ownerRepository.save(owner);
		}
	    
	    /**
	     * Persists a GymAdmin linked to a GymUser and GymAddress.
	     */
	    private GymAdmin saveAdmin(UserDto userDto, GymUser savedUser, GymAddress savedAddress) {
	    	GymAdmin admin = new GymAdmin();
	        admin.setFirstname(userDto.getFirstname());
	        admin.setLastname(userDto.getLastname());
	        admin.setEmail(userDto.getEmail());
	        admin.setMobile(userDto.getMobile());
	        admin.setAddress(savedAddress);
	        admin.setCreatedAt(LocalDateTime.now());
	        admin.setUpdatedAt(LocalDateTime.now());
	        admin.setCenterid(userDto.getCenterid());
	        admin.setUser(savedUser);
	        return adminRepository.save(admin);
		}


		@Override
		public GymUser getUserByUserName(String username) {
			// TODO Auto-generated method stub
			GymUser userData = userRepository.findByUsername(username)
						.orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
			return userData;

		}


}
