package com.lti.flipfit.entity;

import java.util.Date;

import com.lti.flipfit.constants.BookingStatus;

public class GymFlipFitBooking {
	
	private Long id;
	private String status;
	private Date bookedAt;
	private GymFlipFitScheduler schedule;
	private Long customerId;
	//private Long centerId;
	//private Long slotId;
	private GymFlipFitPayment payment;
	
	public GymFlipFitPayment getPayment() {
		return payment;
	}
	public void setPayment(GymFlipFitPayment payment) {
		this.payment = payment;
	}
	/*public Long getSlotId() {
		return slotId;
	}
	public void setSlotId(Long slotId) {
		this.slotId = slotId;
	}*/
	/*public Long getCenterId() {
		return centerId;
	}
	public void setCenterId(Long centerId) {
		this.centerId = centerId;
	}*/
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getBookedAt() {
		return bookedAt;
	}
	public void setBookedAt(Date bookedAt) {
		this.bookedAt = bookedAt;
	}
	public GymFlipFitScheduler getSchedule() {
		return schedule;
	}
	public void setSchedule(GymFlipFitScheduler schedule) {
		this.schedule = schedule;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	
	

}
