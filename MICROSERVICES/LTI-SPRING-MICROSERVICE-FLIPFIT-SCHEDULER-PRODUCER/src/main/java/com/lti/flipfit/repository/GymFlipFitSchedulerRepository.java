package com.lti.flipfit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lti.flipfit.entity.GymFlipFitScheduler;


@Repository
public interface GymFlipFitSchedulerRepository extends JpaRepository<GymFlipFitScheduler, Long>{

}
