
package com.lti.flipfit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lti.flipfit.entity.GymRole;

public interface GymRoleRepository extends JpaRepository<GymRole, Long> {

    // Derived method: returns all roles ordered by name
    List<GymRole> findAllByOrderByRoleNameAsc();

    // Optional: Native SQL if you prefer hitting the table directly
    @Query(value = "SELECT roleid, rolename, description FROM gymrole ORDER BY rolename ASC", nativeQuery = true)
    List<GymRole> findAllNative();
}
