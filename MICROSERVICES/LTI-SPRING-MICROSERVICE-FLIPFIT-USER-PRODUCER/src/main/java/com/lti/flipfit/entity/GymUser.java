/**
 * 
 */
package com.lti.flipfit.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.lti.flipfit.constants.UserStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "gymuser")
public class GymUser {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "userid")
	private Long userId;

	@Column(name = "username", length = 45)
	private String username;

	@Column(name = "password", length = 100)
	private String password;

	@Column(name = "createdat")
	private LocalDateTime createdAt;

	/*
	 * @ManyToOne
	 * 
	 * @JoinColumn(name = "roleid", nullable = false)
	 */
	private Long roleid;

	private String userstatus;

	private Long centerid;

	

	/**
     * Optional address reference.
     * FK: gymuser.addressid -> gymaddress.id
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_gymuser_address"))
    private GymAddress address;

    


    public GymAddress getAddress() {
    	return address; 
    }
    
    public void setAddress(GymAddress address) {
    	this.address = address; 
    }


	@Column(name = "email", length = 100)
	private String email;

	@Column(name = "mobile", length = 10)
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

	public Long getCenterid() {
		return centerid;
	}

	public void setCenterid(Long centerid) {
		this.centerid = centerid;
	}

	// ✅ Getters and Setters
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
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

	public String getUserstatus() {
		return userstatus;
	}

	public void setUserstatus(String userstatus) {
		this.userstatus = userstatus;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Long getRoleid() {
		return roleid;
	}

	public void setRoleid(Long roleid) {
		this.roleid = roleid;
	}
	
	// ✅ Constructors
		public GymUser() {
		}

	public GymUser(String username, String password, LocalDateTime createdAt, Long roleid, String userstatus,
			Long centerid, GymAddress address, String email, Long mobile) {
		super();
		this.username = username;
		this.password = password;
		this.createdAt = createdAt;
		this.roleid = roleid;
		this.userstatus = userstatus;
		this.centerid = centerid;
		this.address = address;
		this.email = email;
		this.mobile = mobile;
	}

		

}
