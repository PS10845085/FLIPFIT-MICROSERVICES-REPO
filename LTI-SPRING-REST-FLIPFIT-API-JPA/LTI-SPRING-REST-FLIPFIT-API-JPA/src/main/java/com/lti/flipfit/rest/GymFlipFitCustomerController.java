/**
 * 
 */
package com.lti.flipfit.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lti.flipfit.entity.GymFlipFitCustomer;
import com.lti.flipfit.services.GymFlipFitCustomerService;

import jakarta.ws.rs.core.MediaType;


/**
 * REST controller for managing customers in the GymFlipFit application.
 *
 * <p>This controller provides endpoints for CRUD operations on customers, including
 * creating, updating, retrieving, and deleting customer records. All responses are
 * returned in JSON format.</p>
 *
 * <h2>Responsibilities:</h2>
 * <ul>
 *     <li>Expose REST endpoints for customer management</li>
 *     <li>Handle HTTP requests and delegate business logic to {@code GymFlipFitCustomerService}</li>
 * </ul>
 *
 * <h2>Endpoints:</h2>
 * <ul>
 *     <li><b>GET /customer/customers</b> - Retrieve all customers</li>
 *     <li><b>GET /customer/customers/{id}</b> - Retrieve a customer by ID</li>
 *     <li><b>POST /customer/createCustomer</b> - Create a new customer</li>
 *     <li><b>PUT /customer/updateCustomer/{id}</b> - Update customer details</li>
 *     <li><b>DELETE /customer/deleteCustomer/{id}</b> - Delete a customer</li>
 * </ul>
 *
 * @author Team Bravo
 * @version v1.0.0
 * @see GymFlipFitCustomer
 * @see GymFlipFitCustomerService
 */

@RestController
public class GymFlipFitCustomerController {

	@Autowired
	private GymFlipFitCustomerService customerService;

	// Nowdays we are using requestmapping which is the clubbed operation of
	// GET,POST,DELETE in single annotation

	@RequestMapping(value = "/customers", produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET)
	public List<GymFlipFitCustomer> getAllCustomers() {
		return customerService.findAllCustomers();
	}

	@RequestMapping(value = "/customers/{id}", produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET)
	public ResponseEntity<GymFlipFitCustomer> getCustomerById(@PathVariable Long id) {
		return customerService.findCustomerById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@RequestMapping(value = "/createCustomer", produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST)
	public ResponseEntity<GymFlipFitCustomer> createCustomer(@RequestBody GymFlipFitCustomer customer) {
		GymFlipFitCustomer savedCustomer = customerService.saveCustomer(customer);
		return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/updateCustomer/{id}", produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT)
	public ResponseEntity<GymFlipFitCustomer> updateCustomer(@PathVariable Long id, @RequestBody GymFlipFitCustomer customerDetails) {
		GymFlipFitCustomer updatedCustomer = customerService.updateCustomer(id, customerDetails);
		if (updatedCustomer != null) {
			return ResponseEntity.ok(updatedCustomer);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@RequestMapping(value = "/deleteCustomer/{id}", produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
		customerService.deleteCustomer(id);
		return ResponseEntity.noContent().build();
	}

}
