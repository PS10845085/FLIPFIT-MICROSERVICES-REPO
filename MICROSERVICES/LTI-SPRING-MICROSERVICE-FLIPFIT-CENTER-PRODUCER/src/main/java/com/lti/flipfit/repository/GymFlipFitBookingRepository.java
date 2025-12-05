package com.lti.flipfit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lti.flipfit.entity.GymFlipFitBooking;

@Repository
public interface GymFlipFitBookingRepository extends JpaRepository<GymFlipFitBooking, Long> {
	List<GymFlipFitBooking> findByUserId(Long userId);
}
