/**
 * 
 */
package com.lti.flipfit.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * 
 */
public class CustomerNotFoundException extends RuntimeException{

	public CustomerNotFoundException(Long id) {
		super();
	}

	

}
