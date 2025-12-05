package com.lti.flipfit.dto;

import com.lti.flipfit.entity.GymFlipFitAddress;

public class CreateCenterRequest {

	private Long id;
	private String name;
	private String emailId;
	private String phoneNo;
	private GymFlipFitAddress address;
	private Long ownerId;

	public CreateCenterRequest() {
		super();
	}

	public CreateCenterRequest(Long id, String name, String emailId, String phoneNo, GymFlipFitAddress address,
			Long ownerId) {
		super();
		this.id = id;
		this.name = name;
		this.emailId = emailId;
		this.phoneNo = phoneNo;
		this.address = address;
		this.ownerId = ownerId;
	}

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

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	@Override
	public String toString() {
		return "CreateCenterRequest [id=" + id + ", name=" + name + ", emailId=" + emailId + ", phoneNo=" + phoneNo
				+ ", address=" + address + ", ownerId=" + ownerId + "]";
	}

}
