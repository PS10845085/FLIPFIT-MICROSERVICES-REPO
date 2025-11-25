package com.lti.filpfit.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lti.filpfit.beans.GymFlipFitAdmin;
import com.lti.filpfit.beans.GymFlipFitCustomer;
import com.lti.filpfit.beans.GymFlipFitSlot;
import com.lti.filpfit.constants.BookingStatus;
import com.lti.filpfit.exception.AdminNotFoundException;
import com.lti.filpfit.exception.CustomerNotFoundException;

@Service
/**
 * GymFlipFitAdminServiceImpl
 *
 * This class implements administrative operations for the FlipFit Gym application.
 * It provides functionality to manage customers and slots, including creation, update,
 * retrieval, and deletion.
 *
 * Responsibilities:
 * - Manage gym customers (CRUD operations).
 * - Manage gym slots (CRUD operations).
 *
 * Dependencies:
 * - GymFlipFitSlotService for slot creation and management.
 *
 * Author: [Your Name]
 * Date: [Date]
 */
public class GymFlipFitAdminServiceImpl implements GymFlipFitAdminService {

	@Autowired
	private GymFlipFitSlotService slotService;

	@Autowired
	private GymFlipFitCustomerService customerService;

	private final List<GymFlipFitAdmin> admins = new ArrayList<>();

	private final AtomicLong counter = new AtomicLong();


	// Customer methods implementations 
	@Override
	/**
	 * findAllCustomers
	 *
	 * Purpose: Implements findAllCustomers functionality.
	 * @return List<GymFlipFitCustomer>
	 */
	public List<GymFlipFitCustomer> findAllCustomers() {
		return customerService.findAllCustomers(); // Return a copy to prevent external modification
	}

	@Override
	/**
	 * findCustomerById
	 *
	 * Purpose: Implements findCustomerById functionality.
	 * @param id Long
	 * @return Optional<GymFlipFitCustomer>
	 */
	public Optional<GymFlipFitCustomer> findCustomerById(Long id) {
		return customerService.findCustomerById(id);
	}

	@Override
	/**
	 * updateCustomer
	 *
	 * Purpose: Implements updateCustomer functionality.
	 * @param id Long
	 * @param customerDetails GymFlipFitCustomer
	 * @return GymFlipFitCustomer
	 */
	public GymFlipFitCustomer updateCustomer(Long id, GymFlipFitCustomer customerDetails) {
		return customerService.updateCustomer(id, customerDetails);
	}

	@Override
	public Optional<GymFlipFitAdmin> findAdminById(Long id) {
		return Optional.ofNullable(admins.stream().filter(c -> c.getId().equals(id)).findFirst().orElseThrow(() -> new AdminNotFoundException(String.format("Admin not found for the given id: %s", id))));
	}

	@Override
	public GymFlipFitAdmin createAdmin(GymFlipFitAdmin admin) {
		admin.setId(counter.incrementAndGet());
		admins.add(admin);
		return admin;
	}

	@Override
	public GymFlipFitAdmin updateAdmin(Long id, GymFlipFitAdmin admin) {
		return findAdminById(id).map(ad -> {
			ad.setFirstName(admin.getFirstName());
			ad.setLastName(admin.getLastName());
			ad.setEmail(admin.getEmail());
			return ad;
		}).orElse(null);
	}

	@Override
	public List<GymFlipFitAdmin> findAllAdmins() {
		return new ArrayList<>(admins);
	}

	@Override
	public void deleteAdmin(Long id) {
		admins.removeIf(c -> c.getId().equals(id));

	}

	/**
	 * updateSlot
	 *
	 * Purpose: Implements updateSlot functionality.
	 * @param id Long
	 * @param slotDetails GymFlipFitSlot
	 * @return GymFlipFitSlot
	 */
	/*
	 * @Override public GymFlipFitSlot updateSlot(Long id, GymFlipFitSlot
	 * slotDetails) { slotService }
	 */

	/**
	 * deleteSlot
	 *
	 * Purpose: Implements deleteSlot functionality.
	 * @param id Long
	 */
	/*
	 * @Override public void deleteSlot(Long id) { slots.removeIf(c ->
	 * c.getId().equals(id)); }
	 */

}
