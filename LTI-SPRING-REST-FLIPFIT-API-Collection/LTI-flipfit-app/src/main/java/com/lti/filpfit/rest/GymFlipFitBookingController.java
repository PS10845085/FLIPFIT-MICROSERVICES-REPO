package com.lti.filpfit.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lti.filpfit.beans.GymFlipFitBooking;
import com.lti.filpfit.services.GymFlipFitBookingService;

import jakarta.ws.rs.core.MediaType;


/**
 * REST controller for managing Booking, slots related operations in the GymFlipFit application.
 *
 * <p>This controller provides endpoints to retrieve customer information and other
 * administrative functionalities. All responses are returned in JSON format.</p>
 *
 * <h2>Responsibilities:</h2>
 * <ul>
 *     <li>Handle HTTP requests for Booking data</li>
 *     <li>Delegate business logic to {@code BookingService}</li>
 * </ul>
 *
 * <h2>Endpoints:</h2>
 * <ul>
 *     <li><b>GET /booking/{id}</b> - Fetch Booking details by ID</li>
 * </ul>
**/
@RestController
//@RequestMapping("/")
public class GymFlipFitBookingController {


	@Autowired
	public GymFlipFitBookingService bookingService;

	/*
	 * need to create BookingRequest DTO for request body it includes userId,
	 * slotId, centerId
	 */

	/*
	 * @Method: getAllBooking
	 * @Description: listing all the booking
	 * @MethodParameters: No Args
	 * @Exception
	 */
	@RequestMapping(value = "/bookings", produces = MediaType.APPLICATION_JSON, method = RequestMethod.GET)
	public List<GymFlipFitBooking> getAllBookings() {
		return bookingService.findAllBookings();
	}

	/*
	 * @RequestMapping(value = "/bookings/{id}", produces =
	 * MediaType.APPLICATION_JSON, method = RequestMethod.GET) public
	 * ResponseEntity<Booking> getBookingById(@PathVariable Long id) { return
	 * bookingService.findBookingById(id).map(ResponseEntity::ok).orElse(
	 * ResponseEntity.notFound().build()); }
	 */
	
	/*
	 * @Method: CreateBooking
	 * @Description: Create new booking
	 * @MethodParameters: Request Body of reference
	 * @Exception
	 */
	@RequestMapping(value = "/createBooking", produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST)
	public ResponseEntity<GymFlipFitBooking> createBooking(@RequestBody GymFlipFitBooking booking) {
		GymFlipFitBooking savedBooking = bookingService.createBooking(booking);
		return new ResponseEntity<>(savedBooking, HttpStatus.CREATED);
	}
	
	/*
	 * @Method: updateBooking
	 * @Description: Booking update 
	 * @MethodParameters: Booking Id and passing attributes which needs to update
	 * @Exception
	 */
	@RequestMapping(value = "/updateBooking/{id}", produces = MediaType.APPLICATION_JSON, method = RequestMethod.PUT)
	public ResponseEntity<GymFlipFitBooking> updateBooking(@PathVariable Long id, @RequestBody GymFlipFitBooking bookingDetails) {
		GymFlipFitBooking updatedBooking = bookingService.updateBooking(id, bookingDetails);
		if (updatedBooking != null) {
			return ResponseEntity.ok(updatedBooking);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	/*
	 * @Method: getAllCustomer
	 * @Description: Delete the Booking
	 * @MethodParameters: Booking Id
	 * @Exception
	 */
	@RequestMapping(value = "/deleteBooking/{id}", produces = MediaType.APPLICATION_JSON, method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
		bookingService.deleteBooking(id);
		return ResponseEntity.noContent().build();
	}
}
