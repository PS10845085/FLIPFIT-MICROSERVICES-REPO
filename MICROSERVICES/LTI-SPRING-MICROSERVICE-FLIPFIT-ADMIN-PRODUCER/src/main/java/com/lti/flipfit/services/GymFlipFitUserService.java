package com.lti.flipfit.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lti.flipfit.entity.GymUser;

@Service
public interface GymFlipFitUserService {

	public GymUser updateUserStatusById(Long id, String status);
	
}
