package com.lti.flipfit.services;

import com.lti.flipfit.entity.GymCustomer;

public interface UserProfileService {
	GymCustomer  getUserProfileById(Long id);
}

