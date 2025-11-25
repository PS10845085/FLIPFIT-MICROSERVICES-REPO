package com.lti.filpfit.services;

import java.util.List;
import java.util.Optional;

import com.lti.filpfit.beans.GymFlipFitCenter;

public interface GymFlipFitCenterService {

	List<GymFlipFitCenter> findAllCenters();
	 
	Optional<GymFlipFitCenter> findCenterById(Long id);
 
	GymFlipFitCenter saveCenter(GymFlipFitCenter gymFlipFitCenter);
 
	GymFlipFitCenter updateCenter(Long id, GymFlipFitCenter gymFlipFitCenter);
 
	void deleteCenter(Long id);
}
