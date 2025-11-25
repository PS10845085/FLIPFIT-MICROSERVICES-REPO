package com.lti.flipfit.entity;

import java.util.Date;

import com.lti.flipfit.constants.UserStatus;

public class GymFlipFitUser {

	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String phoneNo;
	private String status;
	private GymFlipFitAddress address;
	private GymFlipFitRole role;
	private Date createdAt;
	private Date updatedAt;
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public GymFlipFitAddress getAddress() {
		return address;
	}
	public void setAddress(GymFlipFitAddress address) {
		this.address = address;
	}
	public GymFlipFitRole getRole() {
		return role;
	}
	public void setRole(GymFlipFitRole role) {
		this.role = role;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	
}
