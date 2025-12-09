package com.lti.flipfit.service;

import java.util.List; 
import java.util.Optional;

import com.lti.flipfit.constant.Status;
import com.lti.flipfit.dto.CreateCenterRequest;
import com.lti.flipfit.entity.GymFlipFitCenter;

public interface GymFlipFitCenterService {

	List<GymFlipFitCenter> findAllCenters();
	 
	Optional<GymFlipFitCenter> findCenterById(Long id);
 
	GymFlipFitCenter updateCenter(Long id, GymFlipFitCenter gymFlipFitCenter);
 
	String deleteCenter(Long id);

	GymFlipFitCenter saveCenter(CreateCenterRequest createCenterRequest);

	List<GymFlipFitCenter> findAllActiveCenters();
	
	public GymFlipFitCenter updateCenterStatusById(Long id, Status status);
}
