package com.lti.filpfit.services;

import com.lti.filpfit.beans.GymFlipFitUser;

public interface GymFlipFitUserService {
	
	public GymFlipFitUser getUser(int id);

	public GymFlipFitUser registerUser(GymFlipFitUser user);
	
	public GymFlipFitUser loginUser(GymFlipFitUser user);
	
	public GymFlipFitUser updateUser(GymFlipFitUser user);
	
	public GymFlipFitUser deleteUser(int id);
}
