package com.lti.flipfit.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lti.flipfit.constants.ConstantLists;
import com.lti.flipfit.entity.GymCustomer;
import com.lti.flipfit.entity.GymOwner;
import com.lti.flipfit.entity.GymUser;
import com.lti.flipfit.repository.GymOwnerRepository;
import com.lti.flipfit.repository.GymUserRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class GymFlipFitOwnerServiceImpl implements GymFlipFitOwnerService {

	@Autowired
	private GymOwnerRepository ownerRepository;
	
	public GymFlipFitOwnerServiceImpl(GymOwnerRepository ownerRepository) {
		this.ownerRepository = ownerRepository;
	}

	
	
	 @Transactional()
	    public List<GymOwner> getAllOwnerList() {
		 
		 List<GymOwner> owner = ownerRepository.findByRoleIdWithAddress(ConstantLists.OWNER_ROLE_ID)
	                .orElseThrow(() -> new IllegalArgumentException("User not found for RoleId: " + 3));
	        
		    // Return success response
		    return owner;
		    
	    }


	
}
