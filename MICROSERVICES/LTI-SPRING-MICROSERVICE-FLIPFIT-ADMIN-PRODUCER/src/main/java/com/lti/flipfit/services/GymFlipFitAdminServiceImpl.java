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

@Service
public class GymFlipFitAdminServiceImpl implements GymFlipFitAdminService {
	@Autowired
	private GymFlipFitCustomerService customerService;
	@Autowired
	private GymFlipFitAdminRepository adminRepo;
	@Autowired
	private GymFlipFitCustomerRepository custRepo;

	@Override
	/**
	 * findCustomerById
	 *
	 * Purpose: Implements findCustomerById functionality.
	 * 
	 * @param id Long
	 * @return Optional<GymFlipFitCustomer>
	 */
	public ResponseEntity<GymAdmin> findCustomerById(Long id) {
		GymAdmin data = adminRepo.findById(id).orElseThrow(() -> new RuntimeException("User ID not found"));

		return ResponseEntity.status(200).body(data);

	}

	@Override
	public List<GymCustomer> findAllCustomers() {
		List<com.lti.flipfit.entity.GymCustomer> allCustomerData =	custRepo.findAll();
		return (List<GymCustomer>) ResponseEntity.status(200).body(allCustomerData);
	}

}
