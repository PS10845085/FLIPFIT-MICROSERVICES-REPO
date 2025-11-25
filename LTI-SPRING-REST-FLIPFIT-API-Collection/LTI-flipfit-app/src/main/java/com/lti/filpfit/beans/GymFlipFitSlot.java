package com.lti.filpfit.beans;

import java.time.LocalTime;
import java.util.Date;

public class GymFlipFitSlot {
	
	private Long id;
	private LocalTime startTime;
	private LocalTime endTime;
	private int	capacity;
	private int bookedCount ;
	//private boolean isAvailble ;
	private Date slotDate;
	//private int reserve ;
	//private int releasedBookId;
	private Long centerId;
	//private int customerId;
	private String status;
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getSlotDate() {
		return slotDate;
	}
	public void setSlotDate(Date slotDate) {
		this.slotDate = slotDate;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	/*
	 * public int getCustomerId() { return customerId; } public void
	 * setCustomerId(int customerId) { this.customerId = customerId; }
	 */
	public Long getCenterId() {
		return centerId;
	}
	public void setCenterId(Long centerId) {
		this.centerId = centerId;
	}
	public LocalTime getStartTime() {
		return startTime;
	}
	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}
	public LocalTime getEndTime() {
		return endTime;
	}
	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public int getBookedCount() {
		return bookedCount;
	}
	public void setBookedCount(int bookedCount) {
		this.bookedCount = bookedCount;
	}
	/*
	 * public boolean isAvailble() { return isAvailble; } public void
	 * setAvailble(boolean isAvailble) { this.isAvailble = isAvailble; } public int
	 * getReserve() { return reserve; } public void setReserve(int reserve) {
	 * this.reserve = reserve; } public int getReleasedBookId() { return
	 * releasedBookId; } public void setReleasedBookId(int releasedBookId) {
	 * this.releasedBookId = releasedBookId; }
	 */
	
	

}
