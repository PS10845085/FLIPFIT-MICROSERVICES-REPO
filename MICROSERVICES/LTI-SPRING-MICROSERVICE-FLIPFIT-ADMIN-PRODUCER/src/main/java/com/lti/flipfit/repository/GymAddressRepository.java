
package com.lti.flipfit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lti.flipfit.entity.GymFlipFitAddress;

public interface GymAddressRepository extends JpaRepository<GymFlipFitAddress, Integer> {
}
