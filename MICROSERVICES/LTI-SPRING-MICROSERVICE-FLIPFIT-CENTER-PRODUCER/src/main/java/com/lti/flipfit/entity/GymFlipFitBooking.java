package com.lti.flipfit.entity;

import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "gymbooking")
public class GymFlipFitBooking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 20) // CONFIRMED, CANCELLED, etc.
	private String status;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "booked_at", nullable = false)
	private Date bookedAt;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id", referencedColumnName = "id")
	private GymFlipFitAddress address;

	@ManyToOne
	@JoinColumn(name = "schedule_id", nullable = false)
	private GymFlipFitScheduler schedule;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private GymFlipFitUser user;

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

	public GymFlipFitAddress getAddress() {
		return address;
	}

	public void setAddress(GymFlipFitAddress address) {
		this.address = address;
	}

	public GymFlipFitScheduler getSchedule() {
		return schedule;
	}

	public void setSchedule(GymFlipFitScheduler schedule) {
		this.schedule = schedule;
	}

	public GymFlipFitUser getUser() {
		return user;
	}

	public void setUser(GymFlipFitUser user) {
		this.user = user;
	}

}
