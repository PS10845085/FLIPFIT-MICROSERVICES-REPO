package com.lti.flipfit.repository;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.lti.flipfit.entity.GymFlipFitAdmin;
import com.lti.flipfit.entity.GymFlipFitCustomer;

@Repository
public interface GymFlipFitAdminRepository extends JpaRepository<GymFlipFitAdmin, Long> {
	Optional<GymFlipFitAdmin> findById(long id);
}