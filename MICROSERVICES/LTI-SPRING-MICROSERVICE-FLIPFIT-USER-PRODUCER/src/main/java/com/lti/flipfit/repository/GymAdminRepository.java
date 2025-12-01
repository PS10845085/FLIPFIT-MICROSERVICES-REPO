/**
 * 
 */
package com.lti.flipfit.repository;

import java.util.Optional; 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lti.flipfit.constants.JpqlGymAdmin;
import com.lti.flipfit.entity.GymAdmin;

/**
 * 
 */
@Repository
public interface GymAdminRepository extends JpaRepository<GymAdmin, Long> {


	@Query(JpqlGymAdmin.FIND_ADMIN_DETAIL_BY_USERNAME_AND_STATUS)
    Optional<GymAdmin> findAdminWithUserAndAddress(@Param("username") String username,
                                                         @Param("status") String status);


	
}

