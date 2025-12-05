package com.lti.flipfit.service;

import java.util.List;

import com.lti.flipfit.entity.GymFlipFitBooking;

public interface GymFlipFitBookingService {
	public GymFlipFitBooking createBooking(Long userId, Long scheduleId);
	public String cancelBooking(Long id);
	public List<GymFlipFitBooking> findBookingsByUserId(Long userId);
	public GymFlipFitBooking rescheduleBooking(Long bookingId, Long newScheduleId);
}
	