package com.lti.flipfit.repository;

import java.util.Optional; 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lti.flipfit.entity.GymCustomer;

@Repository
public interface GymFlipFitCustomerRepository extends JpaRepository<GymCustomer, Long>{
	
	public Optional<GymCustomer> findById(Long id);

}
