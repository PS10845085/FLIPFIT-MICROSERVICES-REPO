package com.lti.flipfit.services;

import java.util.List;
import java.util.Optional;

import com.lti.flipfit.entity.GymFlipFitCustomer;

public interface GymFlipFitCustomerService {

	public List<GymFlipFitCustomer> findAllCustomers();
	public Optional<GymFlipFitCustomer> findCustomerById(Long id);
	public GymFlipFitCustomer saveCustomer(GymFlipFitCustomer customer);
	public GymFlipFitCustomer updateCustomer(Long id, GymFlipFitCustomer customerDetails);
	public void deleteCustomer(Long id);

}
