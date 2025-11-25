package com.lti.filpfit.services;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.lti.filpfit.beans.GymFlipFitBooking;
import com.lti.filpfit.beans.GymFlipFitNotification;
import com.lti.filpfit.beans.GymFlipFitPayment;
import com.lti.filpfit.beans.GymFlipFitScheduler;
import com.lti.filpfit.beans.GymFlipFitSlot;
import com.lti.filpfit.exception.BookingConflictException;
import com.lti.filpfit.exception.PaymentFailureException;
import com.lti.filpfit.exception.SlotNotFoundException;

@Service
/**
 * GymFlipFitBookingServiceImpl
 *
 * This class implements booking operations for the FlipFit Gym application.
 * It manages the lifecycle of bookings including creation, update, cancellation,
 * and retrieval, while ensuring slot availability and payment processing.
 *
 * Responsibilities:
 * - Create and update bookings with validation of slot and user conflicts.
 * - Handle cancellations and promote users from the waitlist.
 * - Process payments and manage slot capacity dynamically.
 *
 * Dependencies:
 * - GymFlipFitSlotService, GymFlipFitWaitlistService, GymFlipFitNotificationService,
 *   GymFlipFitPaymentService.
 *
 * Author: [Your Name]
 * Date: [Date]
 */
public class GymFlipFitBookingServiceImpl implements GymFlipFitBookingService{
	
	private final GymFlipFitSlotService slotService;
    private final GymFlipFitWaitlistService waitlistService;
    private final GymFlipFitNotificationService notificationService;
    private final GymFlipFitPaymentService paymentService;
    private final GymFlipFitSchedulerService schedulerService;

    public GymFlipFitBookingServiceImpl(GymFlipFitSlotService slotService, GymFlipFitWaitlistService waitlistService,
            GymFlipFitNotificationService notificationService, GymFlipFitPaymentService paymentService,
            GymFlipFitSchedulerService schedulerService) {
        this.slotService = slotService;
        this.waitlistService = waitlistService;
        this.notificationService = notificationService;
        this.paymentService = paymentService;
        this.schedulerService = schedulerService;
    }

	//private final List<Booking> bookings = new ArrayList<>();
    private final Map<Long, GymFlipFitBooking> bookings = new ConcurrentHashMap<>();
	private final AtomicLong counter = new AtomicLong();
	
	/**
	 * createBooking
	 *
	 * Purpose: Implements createBooking functionality.
	 * @param userId Long
	 * @param centerId Long
	 * @param slotId Long
	 * @param payment GymFlipFitPayment
	 * @return GymFlipFitBooking
	 */
	public GymFlipFitBooking createBooking(GymFlipFitBooking bookingDetail) {
        GymFlipFitSlot slot = slotService.getSlotById(bookingDetail.getSchedule().getSlotId())
                .orElseThrow(() -> new SlotNotFoundException(String.format("Booking failed. As Slot with id: %s not found.", bookingDetail.getSchedule().getSlotId())));
        Optional<GymFlipFitBooking> conflict = findActiveByUserAndDateTime(bookingDetail.getCustomerId(), slot.getSlotDate(), slot.getStartTime());
        if (conflict.isPresent())
            throw new BookingConflictException("You already have a booking in this hour");
        if (slot.getBookedCount() >= slot.getCapacity() || slot.getStatus().equals("FULL"))
            throw new SlotNotFoundException(String.format("Booking failed. As Slot with id: %s is full.", bookingDetail.getSchedule().getSlotId()));
        bookingDetail.getPayment().setBookingId(bookingDetail.getId());
        GymFlipFitPayment payment = paymentService.processPayment(bookingDetail.getPayment());
        if(!payment.getStatus().equals("SUCCESS")) {
        	throw new PaymentFailureException("Payment Failed!");
        }
        GymFlipFitScheduler scheduler = schedulerService.scheduleSlot(bookingDetail.getSchedule());
        GymFlipFitBooking b = new GymFlipFitBooking();
        b.setId(counter.incrementAndGet());
        b.setCustomerId(bookingDetail.getCustomerId());
        //b.setCenterId(centerId);
        //b.setSlotId(slot.getId());
        b.setStatus("CONFIRMED");
        b.setBookedAt(new Date());
        b.setPayment(payment);
        b.setSchedule(scheduler);
        bookings.put(b.getId(), b);
        slotService.updateSlot(slot);
        return b;
    }
	
	/**
	 * updateBooking
	 *
	 * Purpose: Implements updateBooking functionality.
	 * @param bookingId Long
	 * @param updateBookingDetails GymFlipFitBooking
	 * @return GymFlipFitBooking
	 */
	public GymFlipFitBooking updateBooking(Long bookingId, GymFlipFitBooking updateBookingDetails) {
		GymFlipFitBooking booking = bookings.get(bookingId);
        GymFlipFitSlot slot = slotService.getSlotById(updateBookingDetails.getSchedule().getSlotId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Slot not found"));
        //if (!updateBookingDetails.getCenterId().equals(slot.getCenterId()))
            //throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Slot does not belong to center");
        Optional<GymFlipFitBooking> conflict = findActiveByUserAndDateTime(updateBookingDetails.getCustomerId(), slot.getSlotDate(), slot.getStartTime());
        if (conflict.isPresent())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already has a booking in this hour");
        if (slot.getBookedCount() >= slot.getCapacity() || slot.getStatus().equals("FULL"))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Slot is full");
        //Booking b = new Booking();
        //booking.setId(counter.incrementAndGet());
        booking.setCustomerId(updateBookingDetails.getCustomerId());
        //booking.setCenterId(updateBookingDetails.getCenterId());
        //booking.setSlotId(updateBookingDetails.getSchedule().getSlotId());
        booking.setStatus("CONFIRMED");
        booking.setBookedAt(new Date());
        bookings.put(booking.getId(), booking);
        slot.setBookedCount(slot.getBookedCount() + 1);
        if (slot.getBookedCount() >= slot.getCapacity())
            slot.setStatus("FULL");
        return booking;
    } 

    /*public Booking bookReplacingConflicts(Long userId, Long centerId, Long slotId) {
        Slot slot = slotService.getSlotById(slotId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Slot not found"));
        if (!centerId.equals(slot.getCenterId()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Slot does not belong to center");
        findActiveByUserAndDateTime(userId, slot.getSlotDate(), slot.getStartTime())
                .ifPresent(b -> cancel(b.getId(), "Auto-cancel due to new booking in same hour"));
        if (slot.getBookedCount() >= slot.getCapacity() || slot.getStatus() == SlotStatus.FULL) {
            WaitlistEntry e = waitlistService.join(userId, centerId, slotId);
            notificationService.notifyUser(userId, "Waitlisted", "You are waitlisted at position " + e.getPosition());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Slot full; waitlisted position " + e.getPosition());
        }
        return book(userId, centerId, slotId);
    }*/
	/**
	 * deleteBooking
	 *
	 * Purpose: Implements deleteBooking functionality.
	 * @param bookingId/* Long
	 */
	public void deleteBooking(Long bookingId/* , String reason */) {
        GymFlipFitBooking b = bookings.get(bookingId);
        if (b == null)
            return;
        if (b.getStatus().equals("CANCELLED"))
            return;
        b.setStatus("CANCELLED");
        //b.setCancelledAt(new Date());
        //b.setCancellationReason(reason);
        GymFlipFitSlot slot = slotService.getSlotById(b.getSchedule().getSlotId()).orElse(null);
        if (slot != null) {
            slot.setBookedCount(Math.max(0, slot.getBookedCount() - 1));
            if (slot.getBookedCount() < slot.getCapacity())
                slot.setStatus("OPEN");
            waitlistService.promoteNext(slot.getCenterId(), b.getSchedule().getSlotId()).ifPresent(e -> {
                try {
                	GymFlipFitBooking booking = new GymFlipFitBooking();
                	booking.setCustomerId(b.getCustomerId());
                	//booking.setSlotId(e.getSlotId());
                	createBooking(booking);
                	GymFlipFitNotification notification = new GymFlipFitNotification();
                	notification.setMessage("Booking Cancelled");
                    notificationService.sendNotification(notification);
                } catch (ResponseStatusException ex) {
                }
            });
        }
    }

	/**
	 * getUserBookingsForDate
	 *
	 * Purpose: Implements getUserBookingsForDate functionality.
	 * @param userId Long
	 * @param date Date
	 * @return List<GymFlipFitBooking>
	 */
    public List<GymFlipFitBooking> getUserBookingsForDate(Long userId, Date date) {
        return bookings.values().stream().filter(x -> x.getCustomerId().equals(userId)).filter(x -> {
            GymFlipFitSlot s = slotService.getSlotById(x.getSchedule().getSlotId()).orElse(null);
            return s != null && s.getSlotDate().equals(date);
        }).collect(Collectors.toList());
    }

    public Optional<GymFlipFitSlot> findNearestAvailableSlot(Long userId, Long centerId,
            Date date, LocalTime preferred) {
        java.util.List<GymFlipFitSlot> slots = slotService.getSlotsForDate(centerId, date);
        java.util.List<GymFlipFitSlot> candidates = slots.stream().filter(s -> s.getBookedCount() < s.getCapacity())
                .filter(s -> !findActiveByUserAndDateTime(userId, s.getSlotDate(), s.getStartTime()).isPresent())
                .sorted(java.util.Comparator.comparing(GymFlipFitSlot::getStartTime)).collect(Collectors.toList());
        return candidates.stream().filter(s -> !s.getStartTime().isBefore(preferred)).findFirst()
                .or(() -> candidates.stream().findFirst());
    }
    
    private Optional<GymFlipFitBooking> findActiveByUserAndDateTime(Long userId, Date date, LocalTime start) {
        return bookings.values().stream()
                .filter(b -> b.getCustomerId().equals(userId) && b.getStatus().equals("CONFIRMED")).filter(b -> {
                    GymFlipFitSlot s = slotService.getSlotById(b.getSchedule().getSlotId()).orElse(null);
                    return s != null && s.getSlotDate().equals(date) && s.getStartTime().getHour() == start.getHour();
                }).findFirst();
    }
    
    /**
     * findAllBookings
     *
     * Purpose: Implements findAllBookings functionality.
     * @return List<GymFlipFitBooking>
     */
    @Override
	public List<GymFlipFitBooking> findAllBookings() {
		return bookings.values().stream().toList();
	}
/* 
	@Override
	public List<Booking> findAllBookings() {
		return new ArrayList<>(bookings);
	}
 
	@Override
	public Optional<Booking> findBookingById(Long id) {
		return bookings.stream().filter(b -> b.getId().equals(id)).findFirst();
 
	}
 
	@Override
	public Booking createBooking(Booking booking) {
		booking.setId(counter.incrementAndGet());
		bookings.add(booking);
		return booking;
	}
 
	@Override
	public Booking updateBooking(Long id, Booking bookingDetails) {
		return findBookingById(id).map(booking -> {
			booking.setBookedAt(bookingDetails.getBookedAt());
			booking.setCenterId(bookingDetails.getCenterId());
			booking.setCustomerId(bookingDetails.getCustomerId());
			booking.setId(bookingDetails.getId());
			booking.setSchedule(bookingDetails.getSchedule());
			booking.setStatus(bookingDetails.getStatus());
 
			return booking;
		}).orElse(null);
	}
 
	@Override
	public void deleteBooking(Long id) {
		bookings.removeIf(b -> b.getId().equals(id));
	}
*/

	@Override
	public Optional<GymFlipFitSlot> findNearestAvailableSlot(Long userId, Long centerId, Date date,
			Date preferredStart) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	
}
