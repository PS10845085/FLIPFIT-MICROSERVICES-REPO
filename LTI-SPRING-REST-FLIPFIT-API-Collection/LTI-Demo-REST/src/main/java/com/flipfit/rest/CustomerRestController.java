/**
 * 
 */
package com.flipfit.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.flipfit.bean.Customer;
import com.flipfit.service.CustomerService;

import jakarta.ws.rs.core.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 
 */
@RestController
public class CustomerRestController {

	@Autowired
	private CustomerService customerService;

	// Nowdays we are using requestmapping which is the clubbed operation of
	// GET,POST,DELETE in single annotation

	@RequestMapping(value = "/customers", produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET)
	public List<Customer> getAllCustomers() {
		return customerService.findAllCustomers();
	}

	@RequestMapping(value = "/customers/{id}", produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET)
	public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
		return customerService.findCustomerById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@RequestMapping(value = "/createCustomer", produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST)
	public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
		Customer savedCustomer = customerService.saveCustomer(customer);
		return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/updateCustomer/{id}", produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT)
	public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customerDetails) {
		Customer updatedCustomer = customerService.updateCustomer(id, customerDetails);
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
