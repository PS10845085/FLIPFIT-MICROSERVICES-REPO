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
