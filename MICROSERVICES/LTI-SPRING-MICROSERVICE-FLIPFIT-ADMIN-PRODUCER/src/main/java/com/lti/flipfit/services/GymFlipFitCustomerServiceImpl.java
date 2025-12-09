package com.lti.flipfit.services;

import java.util.List;  
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lti.flipfit.entity.GymCustomer;
import com.lti.flipfit.exceptions.CustomerNotFoundException;
import com.lti.flipfit.repository.GymCustomerRepository;
import com.lti.flipfit.constants.ConstantLists;
import jakarta.transaction.Transactional;

/**
 * Service implementation for customer-related operations.
 * <p>
 * Provides CRUD operations, soft/hard delete behaviors, and role-filtered
 * fetch utilities for {@link GymCustomer}. This service interacts with the
 * persistence layer through {@link GymCustomerRepository} and ensures
 * transactional consistency for write operations.
 * </p>
 *
 * <h2>Responsibilities</h2>
 * <ul>
 *   <li>Find customer by id and map to a DTO representation.</li>
 *   <li>List customers (all or filtered by role).</li>
 *   <li>Create, update, delete (hard/soft) customer records.</li>
 *   <li>Provide idempotent delete behavior for robustness.</li>
 * </ul>
 *
 * <h2>Notes</h2>
 * <ul>
 *   <li>Mapping methods (<code>toDto</code>, <code>toEntity</code>) currently copy a subset of fields—
 *       expand as your domain requires or introduce a mapper (e.g., MapStruct).</li>
 *   <li>Use domain-specific exceptions (e.g., {@link CustomerNotFoundException}) for clearer error semantics.</li>
 *   <li>Prefer DTOs with validation annotations for create/update flows.</li>
 * </ul>
 *
 * @author FlipFit
 * @since 1.0
 */

@Service
@Transactional
public class GymFlipFitCustomerServiceImpl implements GymFlipFitCustomerService {

	@Autowired
	private GymCustomerRepository customerRepository;

    /**
     * Constructs the {@code GymFlipFitCustomerServiceImpl} with repository dependency.
     *
     * @param customerRepository the repository providing access to {@link GymCustomer} entities
     */

	public GymFlipFitCustomerServiceImpl(GymCustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}


/**
     * Finds a customer by their unique id and maps the entity into a DTO-like object.
     * <p>
     * Returns an {@link Optional} which will be empty when the id does not exist. Callers
     * can decide whether to return 404 or other behavior. Mapping is handled by {@link #toDto(GymCustomer)}.
     * </p>
     *
     * @param id the unique customer id to look up
     * @return an {@link Optional} containing the mapped customer DTO if found; empty otherwise
     */

	@Override
	public Optional findCustomerById(Long id) {
		return customerRepository.findById(id).map(this::toDto);
	}

    /**
     * Retrieves all customers from the repository.
     * <p>
     * The stream/collector is used defensively in case further processing/mapping is added later.
     * </p>
     *
     * @return a non-null list of {@link GymCustomer}; may be empty
     */

	@Override
	public List<GymCustomer> findAllCustomers() {
		return customerRepository.findAll().stream().collect(Collectors.toList());
	}

    /**
     * Updates an existing customer with the provided details.
     * <p>
     * Loads the entity, applies incoming fields, persists, and returns the updated DTO.
     * Returns an {@link Optional} which will be empty if the id is not found.
     * </p>
     *
     * <h3>Fields updated</h3>
     * <ul>
     *   <li>id, firstname, lastname, email, mobile, address, updatedAt</li>
     * </ul>
     *
     * @param id              the customer id to update
     * @param customerDetails payload containing fields to update
     * @return an {@link Optional} containing the updated customer DTO if found
     */

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

/**
     * Deletes a customer by id (hard delete).
     * <p>
     * This method will silently succeed even if the id does not exist; consider using
     * {@link #deleteById(Long)} for idempotent delete with not-found signaling.
     * </p>
     *
     * @param id the id of the customer to delete
     */

	@Override
	public void deleteCustomer(Long id) {
		customerRepository.deleteById(id);
	}


	 /**
	     * Retrieves all customers filtered by role id (CUSTOMER) and includes address data.
	     * <p>
	     * Delegates to the repository method which likely performs a join/entity-graph load.
	     * Throws {@link IllegalArgumentException} if no data is found (consider domain exception).
	     * </p>
	     *
	     * @return list of customers having role id {@link ConstantLists#CUSTOMER_ROLE_ID}
	     * @throws IllegalArgumentException if no customers are found for the role id
	     */

	 @Transactional()
	    public List<GymCustomer> getAllCustomerList() {
		 
		 List<GymCustomer> customer = customerRepository.findByRoleIdWithAddress(ConstantLists.CUSTOMER_ROLE_ID)
	                .orElseThrow(() -> new IllegalArgumentException("User not found for RoleId: " + 3));
	        
		    // Return success response
		    return customer;
		    
	    }

	



	
	// ------------ Mapping helpers ------------

	 /**
	     * Maps a domain entity to a DTO-like object (currently the same type,
	     * but selecting fields to expose/serialize).
	     *
	     * @param e the entity to map
	     * @return a copied {@link GymCustomer} with selected fields
	     */

	
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


/**
     * Maps a DTO-like object back to a domain entity.
     * <p>
     * This is useful for create/update flows where only a subset of fields are provided.
     * </p>
     *
     * @param dto the DTO-like object to convert
     * @return a new {@link GymCustomer} entity built from the DTO fields
     */

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

    /**
     * Creates a new customer.
     * <p>
     * Converts the provided payload to an entity, persists, and returns the saved DTO.
     * Typically, primary keys are not set on create if the DB auto-generates them.
     * </p>
     *
     * @param customer the payload containing fields to create a new {@link GymCustomer}
     * @return the saved customer in DTO form
     */

	@Override
	public GymCustomer createCustomer(GymCustomer customer) {

		GymCustomer entity = toEntity(customer);

		// Typically, you do NOT set PK on create if DB auto-generates.
		// If your PK is not auto-generated, ensure DTO carries a unique customerId.

		GymCustomer saved = customerRepository.save(entity);
		return toDto(saved);
	}


	 /**
	     * Idempotent hard delete: throws {@link CustomerNotFoundException} if the id does not exist.
	     * <p>
	     * The method is safe to call multiple times; it signals not-found to callers via exception.
	     * </p>
	     *
	     * @param id the id of the customer to delete
	     * @throws CustomerNotFoundException when the id does not exist
	     */

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


/**
     * Alternative idempotent hard delete that silently succeeds if the id does not exist.
     *
     * @param id the id of the customer to delete
     */

	@Transactional
	public void deleteByIdSilent(Long id) {
		if (customerRepository.existsById(id)) {
			customerRepository.deleteById(id);
		}
	}


	 /**
	     * Soft delete: mark a customer as logically deleted (e.g., set a flag).
	     * <p>
	     * Currently the flag is commented out; enable and persist as per your domain model.
	     * </p>
	     *
	     * @param id the id of the customer to soft delete
	     * @throws CustomerNotFoundException if the id does not exist
	     */

	@Transactional
	public void softDeleteById(Long id) {
		Optional<GymCustomer> c = Optional
				.of(customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id)));

		// c.setDeleted(true);
		// when using JPA, changes are flushed automatically on transaction commit
	}

}
