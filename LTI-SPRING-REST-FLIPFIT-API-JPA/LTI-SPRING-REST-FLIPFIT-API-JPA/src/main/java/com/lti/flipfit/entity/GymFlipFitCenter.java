package com.lti.flipfit.entity;

import java.util.List;

import com.lti.flipfit.constants.CenterStatus;

public class GymFlipFitCenter {
	
	private Long id;
	private String name;
	private String emailId;
	private String phoneNo;
	private GymFlipFitAddress address;
	private String status;
	private Long ownerId;
	private Long adminId;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public GymFlipFitAddress getAddress() {
		return address;
	}
	public void setAddress(GymFlipFitAddress address) {
		this.address = address;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getOwnerId() {
		return ownerId;
	}
	public void setOwner(Long ownerId) {
		this.ownerId = ownerId;
	}
	public Long getAdminId() {
		return adminId;
	}
	public void setAdmin(Long adminId) {
		this.adminId = adminId;
	}
	
	

}
