package com.lti.flipfit.repository;

import java.util.List; 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lti.flipfit.constant.Status;
import com.lti.flipfit.constant.JpqlGymCenter;
import com.lti.flipfit.entity.GymFlipFitCenter;

import jakarta.transaction.Transactional;

@Repository
public interface GymFlipFitCenterRepository extends JpaRepository<GymFlipFitCenter, Long>{


List<GymFlipFitCenter> findByStatus(Status status);

// --- JPQL UPDATE for status by id ---
@Modifying(clearAutomatically = true, flushAutomatically = true)
@Transactional
@Query(JpqlGymCenter.UPDATE_STATUS_BY_ID)
int updateStatusById(@Param("id") Long id, @Param("status") Status status);

}
