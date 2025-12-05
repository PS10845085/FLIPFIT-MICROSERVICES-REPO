package com.lti.flipfit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lti.flipfit.entity.GymFlipFitSlot;

@Repository
public interface GymFlipFitSlotRepository extends JpaRepository<GymFlipFitSlot, Long>{

}
