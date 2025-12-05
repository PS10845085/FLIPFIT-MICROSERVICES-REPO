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

import com.lti.flipfit.entity.GymFlipFitAdmin;
import com.lti.flipfit.entity.GymFlipFitCustomer;
import com.lti.flipfit.repository.GymFlipFitCustomerRepository;
import com.lti.flipfit.services.GymFlipFitAdminService;
import com.lti.flipfit.services.GymFlipFitCustomerService;

import jakarta.ws.rs.core.MediaType;

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

	@RequestMapping(value = "/customer/{id}", produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET)
	public ResponseEntity<GymFlipFitAdmin> getCustomerById(@PathVariable Long id) {
		System.out.println("custid ---");
		return adminService.findCustomerById(id);// .map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/getAllCustData")
	public List<com.lti.flipfit.entity.GymFlipFitCustomer> getAllCustomer() {
		return customerRepository.findAll();
	}

	@PutMapping("/updateCustomers/{id}")
	public ResponseEntity<GymFlipFitCustomer> updateCustomer(@PathVariable Long id,
			@RequestBody GymFlipFitCustomer customerDetails) {

		return customerService.updateCustomer(id, customerDetails).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping(value = "/customers", consumes = "application/json", produces = "application/json")
	public ResponseEntity<GymFlipFitCustomer> createCustomer(@RequestBody GymFlipFitCustomer customer) {
		GymFlipFitCustomer created = customerService.createCustomer(customer);
		// For a RESTful API, return 201 + Location header:
		// URI location = URI.create("/api/customers/" + created.getCustomerId());
		// return ResponseEntity.created(location).body(created);
		// If you prefer 200 OK:
		return ResponseEntity.ok(created);
	}

	/** Hard delete endpoint: DELETE /api/customers/{id} */
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		customerService.deleteById(id);
		return ResponseEntity.noContent().build(); // 204 No Content
	}

	/** Soft delete endpoint: DELETE /api/customers/{id}/soft */
	@DeleteMapping("/{id}/soft")
	public ResponseEntity<Void> softDelete(@PathVariable Long id) {
		customerService.softDeleteById(id);
		return ResponseEntity.noContent().build();
	}

}
