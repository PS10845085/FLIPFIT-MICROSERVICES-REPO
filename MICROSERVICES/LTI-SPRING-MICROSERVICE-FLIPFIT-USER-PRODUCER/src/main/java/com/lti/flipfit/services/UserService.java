package com.lti.flipfit.services;

import org.springframework.http.ResponseEntity;

import com.lti.flipfit.dto.LoginDto;
import com.lti.flipfit.dto.UserDto;
import com.lti.flipfit.entity.GymUser;
import com.lti.flipfit.response.RestApiResponse;


public interface UserService {
	

	ResponseEntity<RestApiResponse> register(UserDto userDto);
	ResponseEntity<RestApiResponse> login(LoginDto loginDto);
	GymUser getUserByUserName(String username);

}
