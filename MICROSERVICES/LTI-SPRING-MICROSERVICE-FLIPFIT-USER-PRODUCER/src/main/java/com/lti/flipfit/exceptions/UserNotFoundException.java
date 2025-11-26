package com.lti.flipfit.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * 
 */
public class UserNotFoundException extends RuntimeException{

	public UserNotFoundException(String message) {

        super(message);

	}

}
