package com.lti.flipfit.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lti.flipfit.entity.GymFlipFitCustomer;
import com.lti.flipfit.exceptions.CustomerNotFoundException;
import com.lti.flipfit.repository.GymFlipFitCustomerRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class GymFlipFitCustomerServiceImpl implements GymFlipFitCustomerService {

	@Autowired
	private GymFlipFitCustomerRepository customerRepository;

	public GymFlipFitCustomerServiceImpl(GymFlipFitCustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@Override
	public Optional findCustomerById(Long id) {
		return customerRepository.findById(id).map(this::toDto);
	}

	@Override
	public List<com.lti.flipfit.entity.GymFlipFitCustomer> findAllCustomers() {
		return customerRepository.findAll().stream().collect(Collectors.toList());
	}

	@Override
	public Optional<GymFlipFitCustomer> updateCustomer(Long id, GymFlipFitCustomer customerDetails) {
		return customerRepository.findById(id).map(entity -> {
			// Apply incoming DTO changes to entity
			entity.setCustomerId(customerDetails.getCustomerId());
			entity.setCustomerFirstName(customerDetails.getCustomerFirstName());
			entity.setCustomerLastName(customerDetails.getCustomerLastName());
			entity.setEmail(customerDetails.getEmail());
			entity.setPhoneNo(customerDetails.getEmail());
			entity.setStatus(customerDetails.getStatus());
			entity.setUpdatedAt(customerDetails.getUpdatedAt());
			entity.setAddress(customerDetails.getAddress());

			// ... set any other fields you have

			// Persist and return DTO
			com.lti.flipfit.entity.GymFlipFitCustomer saved = customerRepository.save(entity);
			return toDto(saved);
		});
	}

	@Override
	public void deleteCustomer(Long id) {
		customerRepository.deleteById(id);
	}

	// ------------ Mapping helpers ------------

	private GymFlipFitCustomer toDto(com.lti.flipfit.entity.GymFlipFitCustomer e) {
		GymFlipFitCustomer dto = new GymFlipFitCustomer();
		dto.setCustomerId(e.getCustomerId());
		dto.setCustomerFirstName(e.getCustomerFirstName());
		dto.setCustomerLastName(e.getCustomerLastName());
		dto.setStatus(e.getStatus());
		dto.setCreatedAt(e.getCreatedAt());
		dto.setEmail(e.getEmail());
		dto.setPhoneNo(e.getPhoneNo());
		dto.setUpdatedAt(e.getUpdatedAt());
		dto.setAddress(e.getAddress());
		// ... copy remaining fields
		return dto;
	}

	@SuppressWarnings("unused")
	private com.lti.flipfit.entity.GymFlipFitCustomer toEntity(GymFlipFitCustomer dto) {
		com.lti.flipfit.entity.GymFlipFitCustomer e = new com.lti.flipfit.entity.GymFlipFitCustomer();
		e.setCustomerId(dto.getCustomerId());
		e.setCustomerFirstName(dto.getCustomerFirstName());
		e.setCustomerLastName(dto.getCustomerLastName());
		e.setEmail(dto.getEmail());
		e.setPhoneNo(dto.getPhoneNo());
		e.setStatus(dto.getStatus());
		e.setUpdatedAt(dto.getUpdatedAt());
		e.setCreatedAt(dto.getCreatedAt());
		e.setAddress(dto.getAddress());

		// ... copy remaining fields
		return e;
	}

	@Override
	public GymFlipFitCustomer createCustomer(GymFlipFitCustomer customer) {

		GymFlipFitCustomer entity = toEntity(customer);

		// Typically, you do NOT set PK on create if DB auto-generates.
		// If your PK is not auto-generated, ensure DTO carries a unique customerId.

		GymFlipFitCustomer saved = customerRepository.save(entity);
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
		Optional<GymFlipFitCustomer> c = Optional
				.of(customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id)));

		// c.setDeleted(true);
		// when using JPA, changes are flushed automatically on transaction commit
	}

}
