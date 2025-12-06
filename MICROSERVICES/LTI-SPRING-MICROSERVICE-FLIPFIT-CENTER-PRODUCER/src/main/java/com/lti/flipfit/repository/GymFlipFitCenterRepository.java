package com.lti.flipfit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lti.flipfit.constant.Status;
import com.lti.flipfit.entity.GymFlipFitCenter;

@Repository
public interface GymFlipFitCenterRepository extends JpaRepository<GymFlipFitCenter, Long>{


List<GymFlipFitCenter> findByStatus(Status status);


}
