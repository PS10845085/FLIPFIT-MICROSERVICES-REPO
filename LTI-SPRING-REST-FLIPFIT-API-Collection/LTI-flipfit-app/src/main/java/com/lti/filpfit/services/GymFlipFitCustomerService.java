package com.lti.filpfit.services;

import java.util.List;
import java.util.Optional;

import com.lti.filpfit.beans.GymFlipFitCustomer;

public interface GymFlipFitCustomerService {

	public List<GymFlipFitCustomer> findAllCustomers();
	public Optional<GymFlipFitCustomer> findCustomerById(Long id);
	public GymFlipFitCustomer saveCustomer(GymFlipFitCustomer customer);
	public GymFlipFitCustomer updateCustomer(Long id, GymFlipFitCustomer customerDetails);
	public void deleteCustomer(Long id);

}
