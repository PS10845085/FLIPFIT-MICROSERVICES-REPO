package com.lti.flipfit.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * 
 */
public class UserNameAlreadyExistException extends RuntimeException{

	public UserNameAlreadyExistException(String message) {

        super(message);

	}

}
