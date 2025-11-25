package com.lti.filpfit.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.lti.filpfit.beans.GymFlipFitAdmin;
import com.lti.filpfit.beans.GymFlipFitCustomer;
import com.lti.filpfit.beans.GymFlipFitSlot;

public interface GymFlipFitAdminService {
	
	public List<GymFlipFitCustomer> findAllCustomers();
	public Optional<GymFlipFitCustomer> findCustomerById(Long id);
	public GymFlipFitCustomer updateCustomer(Long id, GymFlipFitCustomer customerDetails);

	public Optional<GymFlipFitAdmin> findAdminById(Long id);
	public GymFlipFitAdmin createAdmin(GymFlipFitAdmin admin);
	public GymFlipFitAdmin updateAdmin(Long id, GymFlipFitAdmin admin);
	public List<GymFlipFitAdmin> findAllAdmins();
	public void deleteAdmin(Long id);


}
