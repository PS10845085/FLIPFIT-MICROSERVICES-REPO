package com.lti.flipfit.entity;

import java.time.LocalDateTime; 

import jakarta.persistence.*;

@Entity
@Table(name = "gymadmin")
public class GymAdmin {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @Column(name="firstname", nullable=false)
    private String firstname;

    @Column(name="lastname")
    private String lastname;

    @Column(name="email")
    private String email;

    @Column(name="mobile")
    private Long mobile;
    
    @Column(name="centerid")
	private Long centerid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_gymuser_address"))
    private GymAddress address;

    @Column(name="createdat")
    private LocalDateTime createdAt;

    @Column(name="updatedat")
    private LocalDateTime updatedAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid", referencedColumnName = "id")
    private GymUser user;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
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

	public Long getCenterid() {
		return centerid;
	}

	public void setCenterid(Long centerid) {
		this.centerid = centerid;
	}

	public GymAddress getAddress() {
		return address;
	}

	public void setAddress(GymAddress address) {
		this.address = address;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public GymUser getUser() {
		return user;
	}

	public void setUser(GymUser user) {
		this.user = user;
	}

    // getters/setters
    
}

