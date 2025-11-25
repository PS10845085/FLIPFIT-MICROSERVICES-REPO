package com.lti.flipfit.entity;

import java.util.Date;

public class GymFlipFitWaitList {
	
	private Long id;
	private Long userId;
	private Long centerId;
	private Long slotId;
	private int position;
	private String status;
	private Date createdAt;
	private Date promotedAt;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getCenterId() {
		return centerId;
	}
	public void setCenterId(Long centerId) {
		this.centerId = centerId;
	}
	public Long getSlotId() {
		return slotId;
	}
	public void setSlotId(Long slotId) {
		this.slotId = slotId;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getPromotedAt() {
		return promotedAt;
	}
	public void setPromotedAt(Date promotedAt) {
		this.promotedAt = promotedAt;
	}
	

}
