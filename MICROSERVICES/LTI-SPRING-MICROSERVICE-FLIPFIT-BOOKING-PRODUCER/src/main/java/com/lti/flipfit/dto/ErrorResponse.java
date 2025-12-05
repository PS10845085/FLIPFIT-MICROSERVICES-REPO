
/**
 * ErrorResponse is a Data Transfer Object (DTO) used to represent
 * structured error details in API responses

 * This class is typically returned by global exception handlers to provide
 * consistent error information to clients.
 */
package com.lti.flipfit.dto;

import java.time.LocalDateTime;

public class ErrorResponse {

    private String message;
    private String details;
    private LocalDateTime timestamp;

    /**
     * Constructs an ErrorResponse with the specified message, details, and timestamp.
     *
     * @param message   a short description of the error
     * @param details   additional context or request details
     * @param timestamp the time when the error occurred
     */
    public ErrorResponse(String message, String details, LocalDateTime timestamp) {
        this.message = message;
        this.details = details;
        this.timestamp = timestamp;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
}
