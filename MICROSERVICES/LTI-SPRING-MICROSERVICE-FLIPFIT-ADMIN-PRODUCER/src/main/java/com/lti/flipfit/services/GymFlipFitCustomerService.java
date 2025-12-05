package com.lti.flipfit.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lti.flipfit.entity.GymFlipFitCustomer;

@Service
public interface GymFlipFitCustomerService {
	public GymFlipFitCustomer createCustomer(GymFlipFitCustomer customer);

	public List<com.lti.flipfit.entity.GymFlipFitCustomer> findAllCustomers();

	public Optional<GymFlipFitCustomer> findCustomerById(Long id);

	public void deleteCustomer(Long id);

	// public Optional<GymFlipFitCustomer> updateCustomer(Long id,
	// GymFlipFitCustomer customerDetails);
	// << align to controller taken bean not entity here

	Optional<com.lti.flipfit.entity.GymFlipFitCustomer> updateCustomer(Long id,
			com.lti.flipfit.entity.GymFlipFitCustomer customerDetails);

	public void deleteById(Long id);

	public void softDeleteById(Long id);
}
