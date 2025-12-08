/**
 * 
 */
package com.lti.flipfit.repository;

import java.util.List;
import java.util.Optional; 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lti.flipfit.constants.JpqlGymOwner;
import com.lti.flipfit.constants.JpqlGymUser;
import com.lti.flipfit.entity.GymCustomer;
import com.lti.flipfit.entity.GymOwner;

/**
 * 
 */
@Repository
public interface GymOwnerRepository extends JpaRepository<GymOwner, Long> {


	@Query(JpqlGymOwner.FIND_OWNER_DETAIL_BY_USERNAME_AND_STATUS)
    Optional<GymOwner> findOwnerWithUserAndAddress(@Param("username") String username,
                                                         @Param("status") String status);

	@Query(JpqlGymOwner.FIND_BY_ROLE_ID_WITH_ADDRESS)
	Optional<List<GymOwner>> findByRoleIdWithAddress(@Param("roleid") Long roleid);
	
}

