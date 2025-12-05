package com.lti.flipfit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lti.flipfit.entity.GymFlipFitUser;

@Repository
public interface GymFlipFitUserRepository extends JpaRepository<GymFlipFitUser, Long> {

}
