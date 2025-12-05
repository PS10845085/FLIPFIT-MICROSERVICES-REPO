/**
 * 
 */
package com.lti.flipfit.advice;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.lti.flipfit.dto.ErrorResponse;
import com.lti.flipfit.exception.BookingConflictException;
import com.lti.flipfit.exception.BookingNotFoundException;
import com.lti.flipfit.exception.ScheduleNotFoundException;
import com.lti.flipfit.exception.SlotFullException;
import com.lti.flipfit.exception.SlotNotFoundException;
import com.lti.flipfit.exception.UserNotFoundException;

/**
 * 
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorResponse> userNotFoundException(UserNotFoundException ex, WebRequest request) {

		ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), request.getDescription(false),
				LocalDateTime.now());

		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler(SlotNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleSlotNotFoundException(SlotNotFoundException ex, WebRequest request) {

		ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), request.getDescription(false),
				LocalDateTime.now());

		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler(BookingConflictException.class)
	public ResponseEntity<ErrorResponse> handleBookinConflictException(BookingConflictException ex,
			WebRequest request) {

		ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), request.getDescription(false),
				LocalDateTime.now());

		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.CONFLICT);

	}

	@ExceptionHandler(ScheduleNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleScheduleNotFoundException(ScheduleNotFoundException ex,
			WebRequest request) {

		ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), request.getDescription(false),
				LocalDateTime.now());
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.CONFLICT);

	}

	@ExceptionHandler(SlotFullException.class)
	public ResponseEntity<ErrorResponse> handleSlotExceptions(SlotFullException ex, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), request.getDescription(false),
				LocalDateTime.now());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(BookingNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleBookingNotFoundExceptions(BookingNotFoundException ex,
			WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), request.getDescription(false),
				LocalDateTime.now());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	// default Exception handler
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), request.getDescription(false),
				LocalDateTime.now());
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
