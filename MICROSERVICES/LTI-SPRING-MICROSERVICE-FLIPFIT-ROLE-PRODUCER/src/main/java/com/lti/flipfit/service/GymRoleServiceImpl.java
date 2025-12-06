
package com.lti.flipfit.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lti.flipfit.entity.GymRole;
import com.lti.flipfit.repository.GymRoleRepository;
import com.lti.flipfit.service.GymRoleService;

@Service
public class GymRoleServiceImpl implements GymRoleService {

    private final GymRoleRepository gymRoleRepository;

    public GymRoleServiceImpl(GymRoleRepository gymRoleRepository) {
        this.gymRoleRepository = gymRoleRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<GymRole> getAllRoles() {
        // Option 1: JPA derived method with ordering
        return gymRoleRepository.findAllByOrderByRoleNameAsc();

        // Option 2 (alternative): Native SQL
        // return gymRoleRepository.findAllNative();
    }
}
