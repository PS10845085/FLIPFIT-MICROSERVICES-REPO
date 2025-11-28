/**
 * 
 */
package com.lti.flipfit.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lti.flipfit.constants.JpqlGymUser;
import com.lti.flipfit.entity.GymCustomer;
import com.lti.flipfit.entity.GymUser;

/**
 * 
 */
@Repository
public interface GymCustomerRepository extends JpaRepository<GymCustomer, Long> {


	@Query(JpqlGymUser.FIND_CUSTOMER_DETAIL_BY_USERNAME_AND_STATUS)
    Optional<GymCustomer> findCustomerWithUserAndAddress(@Param("username") String username,
                                                         @Param("status") String status);


	
}

