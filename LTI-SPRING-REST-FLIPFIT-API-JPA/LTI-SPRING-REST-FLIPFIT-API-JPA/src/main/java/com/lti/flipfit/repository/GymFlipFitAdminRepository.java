/**
 * 
 */
package com.lti.flipfit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lti.flipfit.entity.GymFlipFitAdmin;

/**
 * 
 */
@Repository
public interface GymFlipFitAdminRepository extends JpaRepository<GymFlipFitAdmin, Integer> {
	

}
