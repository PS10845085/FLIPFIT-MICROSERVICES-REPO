/**
 * 
 */
package com.lti.flipfit.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
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

    // ✅ Constructors
    public GymUser() {}

    public GymUser(String username, String password, LocalDateTime createdAt, Integer roleid) {
        this.username = username;
        this.password = password;
        this.createdAt = createdAt;
        this.roleid = roleid;
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


	public void setCreatedAt(LocalDateTime createdAt) {
	    this.createdAt = createdAt;
	}


    public Integer getRole() {
        return roleid;
    }

    public void setRole(Integer roleid) {
        this.roleid = roleid;
    }
}
