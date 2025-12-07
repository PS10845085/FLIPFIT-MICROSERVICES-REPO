package com.lti.flipfit.services;

import java.util.List; 
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lti.flipfit.entity.GymCustomer;
import com.lti.flipfit.entity.GymUser;

@Service
public interface GymFlipFitCustomerService {
	public GymCustomer createCustomer(GymCustomer customer);

	public List<GymCustomer> findAllCustomers();
	
	public List<GymCustomer> getAllCustomerList();

	public Optional<GymCustomer> findCustomerById(Long id);

	public void deleteCustomer(Long id);

	// public Optional<GymFlipFitCustomer> updateCustomer(Long id,
	// GymFlipFitCustomer customerDetails);
	// << align to controller taken bean not entity here

	Optional<GymCustomer> updateCustomer(Long id,
			GymCustomer customerDetails);

	public void deleteById(Long id);

	public void softDeleteById(Long id);
}
