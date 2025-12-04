
package com.lti.flipfit.exceptions;

import com.lti.flipfit.response.RestApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNameAlreadyExistException.class)
    public ResponseEntity<RestApiResponse> handleUserNameAlreadyExist(UserNameAlreadyExistException ex) {
        RestApiResponse response = new RestApiResponse("FAILURE", ex.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestApiResponse> handleGenericException(Exception ex) {
        RestApiResponse response = new RestApiResponse("ERROR", ex.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
