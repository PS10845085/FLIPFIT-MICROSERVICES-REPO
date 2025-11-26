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
    private Integer roleid;

    private String userstatus;
    
    private Integer centerid;
    
    public Integer getCenterid() {
		return centerid;
	}

	public void setCenterid(Integer centerid) {
		this.centerid = centerid;
	}

	// ✅ Constructors
    public GymUser() {}

    public GymUser(String username, String password, LocalDateTime createdAt, Integer roleid, String userstatus) {
        this.username = username;
        this.password = password;
        this.createdAt = createdAt;
        this.roleid = roleid;
        this.userstatus = userstatus;
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

    public Integer getRoleid() {
		return roleid;
	}

	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}

	
}
