package com.lti.flipfit.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lti.flipfit.entity.GymFlipFitCustomer;

@Repository
public interface GymFlipFitCustomerRepository extends JpaRepository<GymFlipFitCustomer, Long>{
	
	public Optional<GymFlipFitCustomer> findById(Long id);

}
