package com.lti.flipfit.rest;

import java.util.List; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lti.flipfit.entity.GymAdmin;
import com.lti.flipfit.entity.GymCustomer;
import com.lti.flipfit.repository.GymFlipFitCustomerRepository;
import com.lti.flipfit.services.GymFlipFitAdminService;
import com.lti.flipfit.services.GymFlipFitCustomerService;

import jakarta.ws.rs.core.MediaType;

/**
 * REST controller that exposes admin-facing endpoints for managing and retrieving
 * customer and admin data. Endpoints are mounted under <code>/api</code>.
 *
 * <h2>Responsibilities</h2>
 * <ul>
 *   <li>Delegate fetching and mutation operations to appropriate service layers.</li>
 *   <li>Return consistent HTTP responses for CRUD operations.</li>
 *   <li>Expose both soft and hard delete endpoints for customers.</li>
 * </ul>
 *
 * <h2>Notes</h2>
 * <ul>
 *   <li>Consider role-based authorization for mutating endpoints.</li>
 *   <li>Prefer composed Spring annotations (e.g., {@code @GetMapping}) for readability.</li>
 *   <li>For consistency across your API, you may wrap responses in a shared envelope (e.g., {@code RestApiResponse}).</li>
 * </ul>
 *
 * @author FlipFit
 * @since 1.0
 */

@RestController
@RequestMapping("/api")
public class GymFlipFitAdminRestController {
	@Autowired
	private GymFlipFitAdminService adminService;

	// adding seprate
	@Autowired
	private GymFlipFitCustomerService customerService;

	@Autowired
	private GymFlipFitCustomerRepository customerRepository;

	 /**
	     * Constructs the controller with required dependencies.
	     *
	     * @param adminService       service layer for admin-related operations
	     * @param customerService    service layer for customer-related operations
	     * @param customerRepository repository for customer persistence access
	     */


	 public GymFlipFitAdminRestController(GymFlipFitAdminService adminService,
	                                         GymFlipFitCustomerService customerService,
	                                         GymFlipFitCustomerRepository customerRepository) {
	        this.adminService = adminService;
	        this.customerService = customerService;
	        this.customerRepository = customerRepository;
	    }

/**
     * Retrieves an admin view of a customer by id.
     * <p>
     * Delegates to {@link GymFlipFitAdminService#findCustomerById(Long)} and returns a
     * {@link ResponseEntity} with the {@link GymAdmin} representation.
     * </p>
     *
     * <h3>Endpoint</h3>
     * <pre>GET /api/customer/{id}</pre>
     *
     * @param id the unique identifier of the customer
     * @return response entity containing the admin-view of customer
     */


	@RequestMapping(value = "/customer/{id}", produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET)
	public ResponseEntity<GymAdmin> getCustomerById(@PathVariable Long id) {
		System.out.println("custid ---");
		return adminService.findCustomerById(id);// .map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	 /**
	     * Retrieves all customers.
	     * <p>
	     * Returns a raw list from {@link GymFlipFitCustomerRepository#findAll()}. For
	     * larger datasets, consider pagination and filtering.
	     * </p>
	     *
	     * <h3>Endpoint</h3>
	     * <pre>GET /api/getAllCustData</pre>
	     *
	     * @return a list of {@link GymCustomer} entities
	     */

	@GetMapping("/getAllCustData")
	public List<GymCustomer> getAllCustomer() {
		return customerRepository.findAll();
	}

/**
     * Updates a customer by id.
     * <p>
     * Delegates to {@link GymFlipFitCustomerService#updateCustomer(Long, GymCustomer)} and
     * returns 200 OK with the updated customer or 404 Not Found if the id does not exist.
     * </p>
     *
     * <h3>Endpoint</h3>
     * <pre>PUT /api/updateCustomers/{id}</pre>
     *
     * @param id              the customer id to update
     * @param customerDetails the payload containing updated fields
     * @return response entity with the updated {@link GymCustomer} or 404 if absent
     */

	@PutMapping("/updateCustomers/{id}")
	public ResponseEntity<GymCustomer> updateCustomer(@PathVariable Long id,
			@RequestBody GymCustomer customerDetails) {

		return customerService.updateCustomer(id, customerDetails).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

/**
     * Creates a new customer.
     * <p>
     * Delegates to {@link GymFlipFitCustomerService#createCustomer(GymCustomer)} and returns
     * 200 OK with the created entity. For REST conventions, you may prefer returning
     * 201 Created with a <code>Location</code> header.
     * </p>
     *
     * <h3>Endpoint</h3>
     * <pre>POST /api/customers</pre>
     *
     * @param customer the new customer entity to create
     * @return response entity with the created {@link GymCustomer}
     */

	@PostMapping(value = "/customers", consumes = "application/json", produces = "application/json")
	public ResponseEntity<GymCustomer> createCustomer(@RequestBody GymCustomer customer) {
		GymCustomer created = customerService.createCustomer(customer);
		// For a RESTful API, return 201 + Location header:
		// URI location = URI.create("/api/customers/" + created.getCustomerId());
		// return ResponseEntity.created(location).body(created);
		// If you prefer 200 OK:
		return ResponseEntity.ok(created);
	}


/**
     * Hard delete endpoint — permanently deletes a customer by id.
     * <p>
     * Returns 204 No Content on success. For auditing, ensure service tracks hard deletes.
     * </p>
     *
     * <h3>Endpoint</h3>
     * <pre>DELETE /api/delete/{id}</pre>
     *
     * @param id the id of the customer to delete
     * @return response entity with 204 No Content
     */

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		customerService.deleteById(id);
		return ResponseEntity.noContent().build(); // 204 No Content
	}


/**
     * Soft delete endpoint — marks a customer as deleted without removing the record.
     * <p>
     * Returns 204 No Content on success. Typically implemented by setting a status flag
     * (e.g., <code>INACTIVE</code> or <code>DELETED</code>) and keeping the record for auditing.
     * </p>
     *
     * <h3>Endpoint</h3>
     * <pre>DELETE /api/{id}/soft</pre>
     *
     * @param id the id of the customer to soft delete
     * @return response entity with 204 No Content
     */

	@DeleteMapping("/{id}/soft")
	public ResponseEntity<Void> softDelete(@PathVariable Long id) {
		customerService.softDeleteById(id);
		return ResponseEntity.noContent().build();
	}

}
