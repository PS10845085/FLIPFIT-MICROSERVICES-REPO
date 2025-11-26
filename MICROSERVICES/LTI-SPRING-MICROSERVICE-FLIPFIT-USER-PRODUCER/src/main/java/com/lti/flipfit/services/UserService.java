package com.lti.flipfit.services;

import com.lti.flipfit.dao.LoginDto;
import com.lti.flipfit.dao.UserDto;


public interface UserService {
	

    String register(UserDto userDto);
    String login(LoginDto loginDto);

}
