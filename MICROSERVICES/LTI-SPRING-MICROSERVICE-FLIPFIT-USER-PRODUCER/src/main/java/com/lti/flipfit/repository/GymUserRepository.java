/**
 * 
 */
package com.lti.flipfit.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lti.flipfit.constants.JpqlGymUser;
import com.lti.flipfit.entity.GymUser;

/**
 * 
 */
@Repository
public interface GymUserRepository extends JpaRepository<GymUser, Long> {
	
	@Query(JpqlGymUser.FIND_BY_USERNAME)
    Optional<GymUser> findByUsername(@Param("username") String username);
	
	// If you want address and user eagerly in one go:
	@EntityGraph(attributePaths = {"customers", "customers.address"})
    @Query(JpqlGymUser.FIND_CUSTOMER_DETAIL_BY_USERNAME_AND_STATUS)
    Optional<GymUser> findDetailedByUserUsernameAndStatus(@Param("username") String username,
                                                              @Param("status") String status);

}

