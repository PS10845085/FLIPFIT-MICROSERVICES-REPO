package com.lti.flipfit.entity;

/*
 * public class GymFlipFitCustomer extends GymFlipFitUser{
 * 
 * }
 */


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "gymcustomer")
@NoArgsConstructor
@AllArgsConstructor
public class GymFlipFitCustomer implements Serializable {

    @Id
    @Column(name = "customerid", nullable = false)
    private Integer customerId;

    @Column(name = "firstname", nullable = false, length = 45)
    private String customerFirstName;

    @Column(name = "lasttname", length = 45)
    private String customerLastName;

    @Column(name = "email", length = 45)
    private String email;

    @Column(name = "phoneno", length = 45)
    private String phoneNo;

    @Column(name = "status", length = 45)
    private String status;

    @OneToOne
    @JoinColumn(name = "addressid", referencedColumnName = "id")
    private GymFlipFitAddress address;  // Assuming GymAddress is another entity

    @Column(name = "createdat", length = 45)
    private String createdAt;

    @Column(name = "updatedat", length = 45)
    private String updatedAt;

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getCustomerFirstName() {
		return customerFirstName;
	}

	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}

	public String getCustomerLastName() {
		return customerLastName;
	}

	public void setCustomerLastName(String customerLastName) {
		this.customerLastName = customerLastName;
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

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

    
    
}
