/**
 * 
 */
package com.lti.flipfit.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * 
 */
public class UserDto {

	@NotBlank(message = "Username is required")
	private String username;
	
	@Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
	
	@NotNull(message = "Role is required")
    private Long roleid;
	
	private String userstatus;
	
	private Long centerid;
	
	private String email;
	
	private Long mobile;
	
	
	private String line1;
	private String line2;
	private String city;
	private String state;
	private String postalcode;
	private String country;
	
	

	public String getLine1() {
		return line1;
	}
	public void setLine1(String line1) {
		this.line1 = line1;
	}
	public String getLine2() {
		return line2;
	}
	public void setLine2(String line2) {
		this.line2 = line2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPostalcode() {
		return postalcode;
	}
	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getMobile() {
		return mobile;
	}
	public void setMobile(Long mobile) {
		this.mobile = mobile;
	}
	public String getUserstatus() {
		return userstatus;
	}
	public void setUserstatus(String userstatus) {
		this.userstatus = userstatus;
	}
	public Long getCenterid() {
		return centerid;
	}
	public void setCenterid(Long centerid) {
		this.centerid = centerid;
	}
	public String getUserStatus() {
		return userstatus;
	}
	public void setUserStatus(String userstatus) {
		this.userstatus = userstatus;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Long getRoleid() {
		return roleid;
	}
	public void setRoleid(Long roleid) {
		this.roleid = roleid;
	}
    

}
