package com.lti.flipfit.services;

import java.util.List;  
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lti.flipfit.entity.GymCustomer;
import com.lti.flipfit.entity.GymUser;
import com.lti.flipfit.exceptions.CustomerNotFoundException;
import com.lti.flipfit.repository.GymCustomerRepository;
import com.lti.flipfit.repository.GymFlipFitCustomerRepository;
import com.lti.flipfit.constants.ConstantLists;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class GymFlipFitCustomerServiceImpl implements GymFlipFitCustomerService {

	@Autowired
	private GymCustomerRepository customerRepository;

	public GymFlipFitCustomerServiceImpl(GymCustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	
	@Override
	public Optional findCustomerById(Long id) {
		return customerRepository.findById(id).map(this::toDto);
	}

	@Override
	public List<GymCustomer> findAllCustomers() {
		return customerRepository.findAll().stream().collect(Collectors.toList());
	}

	@Override
	public Optional<GymCustomer> updateCustomer(Long id, GymCustomer customerDetails) {
		return customerRepository.findById(id).map(entity -> {
			// Apply incoming DTO changes to entity
			
			entity.setId(customerDetails.getId());
			entity.setFirstname(customerDetails.getFirstname());
			entity.setLastname(customerDetails.getLastname());
			entity.setEmail(customerDetails.getEmail());
			entity.setMobile(customerDetails.getMobile());
			entity.setAddress(customerDetails.getAddress());
			entity.setUpdatedAt(customerDetails.getUpdatedAt());
			
			
			// ... set any other fields you have

			// Persist and return DTO
			GymCustomer saved = customerRepository.save(entity);
			return toDto(saved);
		});
	}

	@Override
	public void deleteCustomer(Long id) {
		customerRepository.deleteById(id);
	}

	
	 @Transactional()
	    public List<GymCustomer> getAllCustomerList() {
		 
		 List<GymCustomer> customer = customerRepository.findByRoleIdWithAddress(ConstantLists.CUSTOMER_ROLE_ID)
	                .orElseThrow(() -> new IllegalArgumentException("User not found for RoleId: " + 3));
	        
		    // Return success response
		    return customer;
		    
	    }

	
	
	// ------------ Mapping helpers ------------

	
	private GymCustomer toDto(GymCustomer e) {
		GymCustomer dto = new GymCustomer();
		
		dto.setId(e.getId());
		dto.setFirstname(e.getFirstname());
		dto.setLastname(e.getLastname());
		dto.setEmail(e.getEmail());
		dto.setMobile(e.getMobile());
		dto.setAddress(e.getAddress());
		dto.setUpdatedAt(e.getUpdatedAt());
		
		
		return dto;
	}

	@SuppressWarnings("unused")
	private GymCustomer toEntity(GymCustomer dto) {
		GymCustomer e = new GymCustomer();
		
		e.setId(dto.getId());
		e.setFirstname(dto.getFirstname());
		e.setLastname(dto.getLastname());
		e.setEmail(dto.getEmail());
		e.setMobile(dto.getMobile());
		e.setAddress(dto.getAddress());
		e.setUpdatedAt(dto.getUpdatedAt());
		// ... copy remaining fields
		return e;
	}

	@Override
	public GymCustomer createCustomer(GymCustomer customer) {

		GymCustomer entity = toEntity(customer);

		// Typically, you do NOT set PK on create if DB auto-generates.
		// If your PK is not auto-generated, ensure DTO carries a unique customerId.

		GymCustomer saved = customerRepository.save(entity);
		return toDto(saved);
	}

	/** Idempotent hard delete: safe to call multiple times. */
	@Transactional
	public void deleteById(Long id) {
		if (!customerRepository.existsById(id)) {
			// choose your behavior:
			// 1) silently return (idempotent), OR
			// 2) throw NotFound to return 404
			throw new CustomerNotFoundException(id);
		}
		customerRepository.deleteById(id);
	}

	/*
	 * Un used methods * / /** Alternative: silent (idempotent) hard delete
	 */
	@Transactional
	public void deleteByIdSilent(Long id) {
		if (customerRepository.existsById(id)) {
			customerRepository.deleteById(id);
		}
	}

	/** Soft delete: mark as deleted = true */
	@Transactional
	public void softDeleteById(Long id) {
		Optional<GymCustomer> c = Optional
				.of(customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id)));

		// c.setDeleted(true);
		// when using JPA, changes are flushed automatically on transaction commit
	}

}
