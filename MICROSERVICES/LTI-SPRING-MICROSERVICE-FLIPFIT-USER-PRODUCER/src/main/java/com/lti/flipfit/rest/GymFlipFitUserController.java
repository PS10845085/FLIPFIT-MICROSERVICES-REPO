package com.lti.flipfit.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lti.flipfit.services.*;
import com.lti.flipfit.dao.LoginDto;
import com.lti.flipfit.dao.UserDto;

/*
 * @Author: Team Bravo
 * @Version: v1.0.0
 * @Description: Defines USer operations like register, update and delete
 */
@RestController
@RequestMapping("/api/auth")
public class GymFlipFitUserController {
	
	@Autowired
	private UserService userService;
	

	 @PostMapping("/register")
	    public ResponseEntity<String> register(@RequestBody UserDto userDto) {
	        return ResponseEntity.ok(userService.register(userDto));
	    }

	    @PostMapping("/login")
	    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
	        return ResponseEntity.ok(userService.login(loginDto));
	    }


}
