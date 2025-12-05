
package com.lti.flipfit.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lti.flipfit.entity.GymFlipFitBooking;
import com.lti.flipfit.entity.GymFlipFitScheduler;
import com.lti.flipfit.entity.GymFlipFitSlot;
import com.lti.flipfit.entity.GymFlipFitUser;
import com.lti.flipfit.exception.BookingNotFoundException;
import com.lti.flipfit.exception.ScheduleNotFoundException;
import com.lti.flipfit.exception.SlotFullException;
import com.lti.flipfit.exception.UserNotFoundException;
import com.lti.flipfit.feign.SchedulerClient;
import com.lti.flipfit.repository.GymFlipFitBookingRepository;
import com.lti.flipfit.repository.GymFlipFitSchedulerRepository;
import com.lti.flipfit.repository.GymFlipFitSlotRepository;
import com.lti.flipfit.repository.GymFlipFitUserRepository;

@Service
public class GymFlipFitBookingServiceImpl implements GymFlipFitBookingService {

	private static final Logger logger = LoggerFactory.getLogger(GymFlipFitBookingServiceImpl.class);

	private final GymFlipFitBookingRepository bookingRepo;
	private final GymFlipFitSlotRepository slotRepo;
	private final GymFlipFitSchedulerRepository scheduleRepo;
	private final GymFlipFitUserRepository userRepo;

	// calling scheduler service through feign client
	//private final SchedulerClient schedulerClient;

	public GymFlipFitBookingServiceImpl(GymFlipFitBookingRepository bookingRepo, GymFlipFitSlotRepository slotRepo,
			GymFlipFitSchedulerRepository scheduleRepo, GymFlipFitUserRepository userRepo,
			SchedulerClient schedulerClient) {
		this.bookingRepo = bookingRepo;
		this.slotRepo = slotRepo;
		this.scheduleRepo = scheduleRepo;
		this.userRepo = userRepo;
		//this.schedulerClient = schedulerClient;
	}

	/** Create a new booking */
	@Override
	public GymFlipFitBooking createBooking(Long userId, Long scheduleId) {
		logger.info("Creating booking for userId={} and scheduleId={}", userId, scheduleId);

		GymFlipFitScheduler scheduler = scheduleRepo.findById(scheduleId)
				.orElseThrow(() -> new ScheduleNotFoundException("Schedule not found with ID: " + scheduleId));

		// Fetch slot from scheduler
		GymFlipFitSlot slot = scheduler.getSlot();
		if (slot.getBookedCount() >= slot.getCapacity()) {
			logger.warn("Slot is full for scheduleId={}", scheduleId);
			throw new SlotFullException("Slot is full. Booking cannot be confirmed.");
		}

		// Fetch user
		GymFlipFitUser user = userRepo.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

		// Create booking
		GymFlipFitBooking booking = new GymFlipFitBooking();
		booking.setSchedule(scheduler);
		booking.setUser(user);
		booking.setStatus("CONFIRMED");
		booking.setBookedAt(new Date());

		// Update slot booking count
		slot.setBookedCount(slot.getBookedCount() + 1);
		slotRepo.save(slot);
		ObjectMapper obj = new ObjectMapper();
		try {
			String res = obj.writeValueAsString(booking);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Save booking
		GymFlipFitBooking savedBooking = bookingRepo.save(booking);
		logger.info("Booking created successfully with ID={}", savedBooking.getId());
		return savedBooking;
	}

	/** Reschedule an existing booking */
	@Override
	public GymFlipFitBooking rescheduleBooking(Long bookingId, Long newScheduleId) {
		logger.info("Rescheduling bookingId={} to newScheduleId={}", bookingId, newScheduleId);

		// Fetch existing booking
		GymFlipFitBooking booking = bookingRepo.findById(bookingId)
				.orElseThrow(() -> new BookingNotFoundException("Booking not found with ID: " + bookingId));

		// Fetch new schedule
		GymFlipFitScheduler newSchedule = scheduleRepo.findById(newScheduleId)
				.orElseThrow(() -> new ScheduleNotFoundException("New schedule not found with ID: " + newScheduleId));

		GymFlipFitSlot newSlot = newSchedule.getSlot();
		if (newSlot.getBookedCount() >= newSlot.getCapacity()) {
			logger.warn("Cannot reschedule: New slot is full for scheduleId={}", newScheduleId);
			throw new SlotFullException("Cannot reschedule: New slot is full.");
		}

		// Reduce old slot count
		GymFlipFitSlot oldSlot = booking.getSchedule().getSlot();
		oldSlot.setBookedCount(oldSlot.getBookedCount() - 1);
		slotRepo.save(oldSlot);

		// Update booking details
		booking.setSchedule(newSchedule);
		booking.setBookedAt(new Date());
		booking.setStatus("RESCHEDULED");

		// Increase new slot count
		newSlot.setBookedCount(newSlot.getBookedCount() + 1);
		slotRepo.save(newSlot);

		GymFlipFitBooking updatedBooking = bookingRepo.save(booking);
		logger.info("Booking rescheduled successfully with ID={}", updatedBooking.getId());
		return updatedBooking;
	}

	/** Cancel booking */
	@Override
	public String cancelBooking(Long bookingId) {
		logger.info("Cancelling booking with ID={}", bookingId);

		GymFlipFitBooking booking = bookingRepo.findById(bookingId)
				.orElseThrow(() -> new BookingNotFoundException("Booking not found with ID: " + bookingId));

		booking.setStatus("CANCELLED");
		bookingRepo.save(booking);

		// Reduce slot count
		GymFlipFitSlot slot = booking.getSchedule().getSlot();
		slot.setBookedCount(slot.getBookedCount() - 1);
		slotRepo.save(slot);

		logger.info("Booking cancelled successfully with ID={}", bookingId);
		return "Booking cancelled successfully with ID: " + bookingId;
	}

	/** Get all bookings for a specific user */
	@Override
	public List<GymFlipFitBooking> findBookingsByUserId(Long userId) {
		logger.info("Fetching bookings for userId={}", userId);

		if (userId == null || userId <= 0) {
			throw new IllegalArgumentException("Invalid userId: " + userId);
		}

		GymFlipFitUser user = userRepo.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

		List<GymFlipFitBooking> bookings = bookingRepo.findByUserId(userId);
		if (bookings.isEmpty()) {
			logger.warn("No bookings found for userId={}", userId);
			throw new BookingNotFoundException("No bookings found for user ID: " + userId);
		}

		logger.info("Found {} bookings for userId={}", bookings.size(), userId);
		return bookings;
	}
}
