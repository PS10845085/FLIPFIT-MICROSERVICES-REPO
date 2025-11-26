/**
 * 
 */
package com.lti.flipfit.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lti.flipfit.beans.User;
import com.lti.flipfit.entity.GymUser;

/**
 * 
 */
@Repository
public interface GymUserRepository extends JpaRepository<GymUser, Long> {
    Optional<GymUser> findByUsername(String username);
    Optional<GymUser> findByUsernameAndUserstatus(String username, String userstatus);
}

