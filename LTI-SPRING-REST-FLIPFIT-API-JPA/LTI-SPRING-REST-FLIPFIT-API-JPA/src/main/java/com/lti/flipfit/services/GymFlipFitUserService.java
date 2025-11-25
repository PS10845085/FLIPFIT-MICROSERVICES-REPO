package com.lti.flipfit.services;

import com.lti.flipfit.entity.GymFlipFitUser;

public interface GymFlipFitUserService {
	
	public GymFlipFitUser getUser(int id);

	public GymFlipFitUser registerUser(GymFlipFitUser user);
	
	public GymFlipFitUser loginUser(GymFlipFitUser user);
	
	public GymFlipFitUser updateUser(GymFlipFitUser user);
	
	public GymFlipFitUser deleteUser(int id);
}
