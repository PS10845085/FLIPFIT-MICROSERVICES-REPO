package com.lti.flipfit.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lti.flipfit.entity.GymFlipFitOwner;

@Repository


public interface GymFlipFitOwnerRepository extends JpaRepository<GymFlipFitOwner, Integer> {

    // Custom query methods can be added here if needed
    // Example:
    // List<GymOwner> findByStatus(String status);
    // Optional<GymOwner> findByEmail(String email);
}

