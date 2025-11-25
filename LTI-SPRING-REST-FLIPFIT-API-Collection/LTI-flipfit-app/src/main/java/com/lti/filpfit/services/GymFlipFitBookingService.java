package com.lti.filpfit.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.lti.filpfit.beans.GymFlipFitBooking;
import com.lti.filpfit.beans.GymFlipFitPayment;
import com.lti.filpfit.beans.GymFlipFitSlot;

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
