package com.lti.flipfit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lti.flipfit.entity.GymRole;

@Repository
public interface GymRoleRepository extends JpaRepository<GymRole, Long> {

}
