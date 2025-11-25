/**
 * 
 */
package com.lti.filpfit.advice;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.lti.filpfit.beans.ErrorResponse;
import com.lti.filpfit.exception.BookingConflictException;
import com.lti.filpfit.exception.CustomerNotFoundException;
import com.lti.filpfit.exception.PaymentFailureException;
import com.lti.filpfit.exception.ScheduleNotFoundException;
import com.lti.filpfit.exception.SlotNotFoundException;

/**
 * 
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleCustomerNotFoundException(CustomerNotFoundException ex, WebRequest request){

		ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));

		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);

	}
	
	@ExceptionHandler(SlotNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleSlotNotFoundException(SlotNotFoundException ex, WebRequest request){

		ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));

		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);

	}
	
	@ExceptionHandler(BookingConflictException.class)
	public ResponseEntity<ErrorResponse> handleBookinConflictException(BookingConflictException ex, WebRequest request){

		ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));

		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.CONFLICT);

	}
	
	@ExceptionHandler(PaymentFailureException.class)
	public ResponseEntity<ErrorResponse> handlePaymentFailureException(PaymentFailureException ex, WebRequest request){

		ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));

		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.CONFLICT);

	}
	
	@ExceptionHandler(ScheduleNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleScheduleNotFoundException(ScheduleNotFoundException ex, WebRequest request){

		ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));

		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.CONFLICT);

	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse(
				LocalDateTime.now(),
				"An unexpected error occurred",
				request.getDescription(false)
				);
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
