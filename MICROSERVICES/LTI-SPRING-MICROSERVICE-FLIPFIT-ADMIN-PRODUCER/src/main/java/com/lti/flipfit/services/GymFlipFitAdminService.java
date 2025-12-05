package com.lti.flipfit.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.lti.flipfit.beans.GymFlipFitCustomer;
import com.lti.flipfit.entity.GymFlipFitAdmin;

public interface GymFlipFitAdminService  {
	
	public ResponseEntity<GymFlipFitAdmin> findCustomerById(Long id);

	public List<GymFlipFitCustomer> findAllCustomers();

	

}
