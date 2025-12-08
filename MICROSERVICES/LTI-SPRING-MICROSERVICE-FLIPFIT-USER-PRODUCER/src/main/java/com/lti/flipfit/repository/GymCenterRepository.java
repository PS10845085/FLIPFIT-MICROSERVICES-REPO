package com.lti.flipfit.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lti.flipfit.entity.GymCenter;

@Repository
public interface GymCenterRepository extends JpaRepository<GymCenter, Long>{


}
