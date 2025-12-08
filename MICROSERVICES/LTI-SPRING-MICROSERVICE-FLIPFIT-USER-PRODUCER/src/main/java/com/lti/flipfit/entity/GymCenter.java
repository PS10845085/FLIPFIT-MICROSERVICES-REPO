package com.lti.flipfit.entity;

import java.util.List;  

import com.lti.flipfit.constants.Status;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "gymcenter")
public class GymCenter {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Long ownerId;

	@Column(length = 100, nullable = false)
	private String name;

	@Column(length = 100)
	private String emailId;

	@Column(length = 15)
	private String phoneNo;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id", referencedColumnName = "id")
	private GymAddress address;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "slot_id") // Foreign key in gymscheduler table
	private List<GymSlot> slots;

	@Enumerated(EnumType.STRING)
	@Column(length = 20, nullable = false)
	private Status status = Status.INACTIVE;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public GymAddress getAddress() {
		return address;
	}

	public void setAddress(GymAddress address) {
		this.address = address;
	}

	public List<GymSlot> getSlots() {
		return slots;
	}

	public void setSlots(List<GymSlot> slots) {
		this.slots = slots;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	
}
