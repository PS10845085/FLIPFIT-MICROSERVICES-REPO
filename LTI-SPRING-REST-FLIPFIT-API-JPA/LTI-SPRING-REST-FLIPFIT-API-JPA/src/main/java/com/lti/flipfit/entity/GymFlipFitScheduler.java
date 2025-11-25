package com.lti.flipfit.entity;

import java.util.Date;

public class GymFlipFitScheduler {
	
	private Long id;
	 //private String resourceRule;
	 private Date validFrom;
	 private Date validTill;
	 private Long slotId;
	 
	 public Long getId() {
		 return id;
	 }
	 public void setId(Long id) {
		 this.id = id;
	 }
	 /*public String getResourceRule() {
		 return resourceRule;
	 }
	 public void setResourceRule(String resourceRule) {
		 this.resourceRule = resourceRule;
	 }*/
	 public Date getValidFrom() {
		 return validFrom;
	 }
	 public void setValidFrom(Date validFrom) {
		 this.validFrom = validFrom;
	 }
	 public Date getValidTill() {
		 return validTill;
	 }
	 public void setValidTill(Date validTill) {
		 this.validTill = validTill;
	 }
	 public Long getSlotId() {
		 return slotId;
	 }
	 public void setSlotId(Long slotId) {
		 this.slotId = slotId;
	 }

}
