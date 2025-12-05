package com.lti.flipfit.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * 
 */
public class UserNameNotFoundException extends RuntimeException{

	public UserNameNotFoundException(String message) {

        super(message);

	}

}
