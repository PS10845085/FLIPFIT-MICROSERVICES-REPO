package com.lti.flipfit.services;


import java.util.List;

import org.springframework.stereotype.Service;

import com.lti.flipfit.entity.GymOwner;

@Service
public interface GymFlipFitOwnerService {

	public List<GymOwner> getAllOwnerList();
	
}
