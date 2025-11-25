/**
 * 
 */
package com.lti.filpfit.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * 
 */
public class CustomerNotFoundException extends RuntimeException{

	public CustomerNotFoundException(String message) {
		super(message);
	}

}
