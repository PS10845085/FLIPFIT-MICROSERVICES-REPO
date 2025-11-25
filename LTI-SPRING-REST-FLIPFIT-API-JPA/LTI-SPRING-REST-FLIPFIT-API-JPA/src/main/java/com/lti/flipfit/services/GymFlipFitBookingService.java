package com.lti.flipfit.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.lti.flipfit.entity.GymFlipFitBooking;
import com.lti.flipfit.entity.GymFlipFitPayment;
import com.lti.flipfit.entity.GymFlipFitSlot;

public interface GymFlipFitBookingService {
	public List<GymFlipFitBooking> findAllBookings();
	//public Optional<Booking> findBookingById(Long id);
	public GymFlipFitBooking createBooking(GymFlipFitBooking bookingDetails);
	public void deleteBooking(Long id);
	public GymFlipFitBooking updateBooking(Long id, GymFlipFitBooking bookingDetails);
	public List<GymFlipFitBooking> getUserBookingsForDate(Long userId, Date date);
    public Optional<GymFlipFitSlot> findNearestAvailableSlot(Long userId, Long centerId, Date date,
    		Date preferredStart);
}
