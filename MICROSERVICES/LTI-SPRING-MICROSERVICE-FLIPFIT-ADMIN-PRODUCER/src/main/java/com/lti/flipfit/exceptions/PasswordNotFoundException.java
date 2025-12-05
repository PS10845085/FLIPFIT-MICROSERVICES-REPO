package com.lti.flipfit.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * 
 */
public class PasswordNotFoundException extends RuntimeException{

	public PasswordNotFoundException(String message) {

        super(message);

	}

}
