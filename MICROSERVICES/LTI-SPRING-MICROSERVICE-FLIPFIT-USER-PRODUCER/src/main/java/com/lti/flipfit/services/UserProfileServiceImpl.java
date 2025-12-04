package com.lti.flipfit.services;


import com.lti.flipfit.entity.GymCustomer;
import com.lti.flipfit.repository.GymCustomerRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    private final GymCustomerRepository gymCustomerRepository;

    public UserProfileServiceImpl(GymCustomerRepository gymCustomerRepository) {
        this.gymCustomerRepository = gymCustomerRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public GymCustomer getUserProfileById(Long userId) {
    	
        GymCustomer customer = gymCustomerRepository.findByUserIdAndStatusWithAddress(userId, "ACTIVE")
                .orElseThrow(() -> new IllegalArgumentException("User not found for userId: " + userId));
        
	    // Return success response
	    return customer;
	    
    }

}
