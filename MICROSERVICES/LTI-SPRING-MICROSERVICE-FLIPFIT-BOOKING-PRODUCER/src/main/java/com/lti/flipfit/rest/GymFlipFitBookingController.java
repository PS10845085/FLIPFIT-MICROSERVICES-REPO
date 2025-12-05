
package com.lti.flipfit.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lti.flipfit.entity.GymFlipFitBooking;
import com.lti.flipfit.service.GymFlipFitBookingServiceImpl;

@RestController
@RequestMapping("/api/bookings")
public class GymFlipFitBookingController {

	private final GymFlipFitBookingServiceImpl bookingService;

	// Constructor-based dependency injection
	public GymFlipFitBookingController(GymFlipFitBookingServiceImpl bookingService) {
		this.bookingService = bookingService;
	}

	/**
	 * Create a new booking for a user and schedule
	 * 
	 * @param userId     ID of the user making the booking
	 * @param scheduleId ID of the schedule to book
	 * @return Created booking details
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<GymFlipFitBooking> createBooking(@RequestParam Long userId, @RequestParam Long scheduleId) {
		GymFlipFitBooking booking = bookingService.createBooking(userId, scheduleId);
		return new ResponseEntity<>(booking, HttpStatus.CREATED);
	}

	/**
	 * Reschedule an existing booking to a new schedule
	 * 
	 * @param bookingId     ID of the booking to reschedule
	 * @param newScheduleId ID of the new schedule
	 * @return Updated booking details
	 */
	@RequestMapping(value = "/{bookingId}/reschedule", method = RequestMethod.PUT)
	public ResponseEntity<GymFlipFitBooking> rescheduleBooking(@PathVariable Long bookingId,
			@RequestParam Long newScheduleId) {
		GymFlipFitBooking updatedBooking = bookingService.rescheduleBooking(bookingId, newScheduleId);
		return ResponseEntity.ok(updatedBooking);
	}

	/**
	 * Cancel a booking by its ID
	 * 
	 * @param bookingId ID of the booking to cancel
	 * @return Confirmation message
	 */
	@RequestMapping(value = "/{bookingId}/cancel", method = RequestMethod.DELETE)
	public ResponseEntity<String> cancelBooking(@PathVariable Long bookingId) {
		String message = bookingService.cancelBooking(bookingId);
		return ResponseEntity.ok(message);
	}

	/**
	 * Get all bookings for a specific user
	 * 
	 * @param userId ID of the user
	 * @return List of bookings for the user
	 */
	@RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
	public ResponseEntity<List<GymFlipFitBooking>> getBookingsByUser(@PathVariable Long userId) {
		List<GymFlipFitBooking> bookings = bookingService.findBookingsByUserId(userId);
		return ResponseEntity.ok(bookings);
	}
}
