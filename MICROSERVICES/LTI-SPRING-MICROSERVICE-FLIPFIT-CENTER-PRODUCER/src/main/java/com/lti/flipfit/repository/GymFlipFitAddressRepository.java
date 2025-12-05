package com.lti.flipfit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lti.flipfit.entity.GymFlipFitAddress;

@Repository
public interface GymFlipFitAddressRepository extends JpaRepository<GymFlipFitAddress, Integer> {

}
