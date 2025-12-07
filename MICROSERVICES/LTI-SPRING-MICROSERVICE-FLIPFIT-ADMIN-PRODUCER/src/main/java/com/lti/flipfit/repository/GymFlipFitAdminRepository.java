package com.lti.flipfit.repository;

import java.util.*; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lti.flipfit.entity.GymAdmin;

@Repository
public interface GymFlipFitAdminRepository extends JpaRepository<GymAdmin, Long> {
	Optional<GymAdmin> findById(long id);
}