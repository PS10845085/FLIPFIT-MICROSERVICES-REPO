package com.lti.flipfit.services;

import java.util.List; 

import org.springframework.http.ResponseEntity;

import com.lti.flipfit.entity.GymAdmin;
import com.lti.flipfit.entity.GymCustomer;

public interface GymFlipFitAdminService  {
	
	public ResponseEntity<GymAdmin> findCustomerById(Long id);

	public List<GymCustomer> findAllCustomers();

	

}
