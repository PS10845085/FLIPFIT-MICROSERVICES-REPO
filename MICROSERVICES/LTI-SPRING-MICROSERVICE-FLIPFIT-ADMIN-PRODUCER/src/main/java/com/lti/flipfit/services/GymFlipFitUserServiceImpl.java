package com.lti.flipfit.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lti.flipfit.constants.ConstantLists;
import com.lti.flipfit.entity.GymCustomer;
import com.lti.flipfit.entity.GymUser;
import com.lti.flipfit.repository.GymUserRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class GymFlipFitUserServiceImpl implements GymFlipFitUserService {

	@Autowired
	private GymUserRepository userRepository;
	
	public GymFlipFitUserServiceImpl(GymUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	
	
@Transactional
    public GymUser updateUserStatusById(Long id, String status) {
        int updated = userRepository.updateStatusById(id, status);
        if (updated == 0) {
            throw new IllegalArgumentException("No GymUser found with id=" + id);
        }
        // Load fresh entity (so you return the latest state)
        return userRepository.findById(id).orElseThrow(() ->
            new IllegalStateException("Updated user not found: id=" + id));
    }

 


	
}
