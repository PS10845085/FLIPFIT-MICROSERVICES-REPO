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

import com.lti.flipfit.constants.JpqlGymUser;
import com.lti.flipfit.entity.GymCustomer;

/**
 * 
 */
@Repository
public interface GymCustomerRepository extends JpaRepository<GymCustomer, Long> {


	@Query(JpqlGymUser.FIND_CUSTOMER_DETAIL_BY_USERNAME_AND_STATUS)
    Optional<GymCustomer> findCustomerWithUserAndAddress(@Param("username") String username,
                                                         @Param("status") String status);




	@Query(JpqlGymUser.FIND_CUSTOMER_BY_USER_ID_AND_STATUS_WITH_ADDRESS)
	Optional<GymCustomer> findByUserIdAndStatusWithAddress(@Param("userId") Long userId,
	                                                       @Param("status") String status);
	

	@Query(JpqlGymUser.FIND_BY_ROLE_ID_WITH_ADDRESS)
	Optional<List<GymCustomer>> findByRoleIdWithAddress(@Param("roleid") Long roleid);
	

	
}

