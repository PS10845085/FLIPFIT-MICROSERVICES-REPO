package com.lti.flipfit.services;

import org.springframework.http.ResponseEntity;

import com.lti.flipfit.dto.LoginDto;
import com.lti.flipfit.dto.UserDto;
import com.lti.flipfit.response.ApiResponse;


public interface UserService {
	

	ResponseEntity<ApiResponse> register(UserDto userDto);
	ResponseEntity<ApiResponse> login(LoginDto loginDto);

}
