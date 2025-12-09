package com.lti.flipfit.services;

import java.util.List; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lti.flipfit.beans.GymFlipFitCustomer;
import com.lti.flipfit.entity.GymAdmin;
import com.lti.flipfit.entity.GymCustomer;
import com.lti.flipfit.repository.GymFlipFitAdminRepository;
import com.lti.flipfit.repository.GymFlipFitCustomerRepository;

/**
 * Service implementation for admin-facing operations.
 * <p>
 * This service coordinates access to admin and customer data via the related repositories:
 * {@link GymFlipFitAdminRepository} and {@link GymFlipFitCustomerRepository}.
 * </p>
 *
 * <h2>Responsibilities</h2>
 * <ul>
 *   <li>Fetch an {@link GymAdmin} representation by id (admin view of a user/customer).</li>
 *   <li>List all {@link GymCustomer} records.</li>
 * </ul>
 *
 * <h2>Design Notes</h2>
 * <ul>
 *   <li>Service methods should return domain objects (or DTOs), not {@link ResponseEntity}.
 *       Controllers are the right place to build HTTP responses.</li>
 *   <li>Prefer domain-specific exceptions (e.g., {@code AdminNotFoundException}) over generic {@link RuntimeException}.</li>
 * </ul>
 *
 * @since 1.0
 */

@Service
public class GymFlipFitAdminServiceImpl implements GymFlipFitAdminService {
	@Autowired
	private GymFlipFitCustomerService customerService;
	@Autowired
	private GymFlipFitAdminRepository adminRepo;
	@Autowired
	private GymFlipFitCustomerRepository custRepo;


    /**
     * Retrieves an admin-view record by id.
     * <p>
     * Throws a runtime exception if the record is not found. Consider replacing
     * with a domain-specific exception and handling it via a {@code @ControllerAdvice}.
     * </p>
     *
     * @param id the unique identifier of the admin/customer view record
     * @return an {@link ResponseEntity} wrapping the {@link GymAdmin} entity with HTTP 200
     *         (if you keep HTTP response construction here). Recommended: let the controller
     *         convert the returned {@link GymAdmin} to a {@link ResponseEntity}.
     * @deprecated Prefer returning {@link GymAdmin} and let controllers build {@link ResponseEntity}.
     */

	@Override
	
	public ResponseEntity<GymAdmin> findCustomerById(Long id) {
		GymAdmin data = adminRepo.findById(id).orElseThrow(() -> new RuntimeException("User ID not found"));

		return ResponseEntity.status(200).body(data);

	}

	 /**
	     * Retrieves all customers from the repository.
	     * <p>
	     * Returns the domain list directly. Avoid building {@link ResponseEntity} in services.
	     * </p>
	     *
	     * @return a non-null {@link List} of {@link GymCustomer}
	     */

	@Override
	public List<GymCustomer> findAllCustomers() {
		List<com.lti.flipfit.entity.GymCustomer> allCustomerData =	custRepo.findAll();
		return (List<GymCustomer>) ResponseEntity.status(200).body(allCustomerData);
	}

}
