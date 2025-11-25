package com.lti.filpfit.services;

import java.util.List;
import java.util.Optional;

import com.lti.filpfit.beans.GymFlipFitOwner;
import com.lti.filpfit.beans.GymFlipFitSlot;

public interface GymFlipFitOwnerService {

	public Optional<GymFlipFitOwner> findOwnerById(Long id);

	public List<GymFlipFitOwner> findAllOwners();

	public GymFlipFitOwner saveOwner(GymFlipFitOwner owner);

	public GymFlipFitOwner updateOwner(Long id, GymFlipFitOwner ownerDetails);

	public void deleteOwner(Long id);
	
	public GymFlipFitSlot saveSlot(GymFlipFitSlot slot);
	public GymFlipFitSlot updateSlot(GymFlipFitSlot slot);

	}
