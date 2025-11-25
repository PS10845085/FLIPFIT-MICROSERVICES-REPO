package com.lti.filpfit.beans;

public class GymFlipFitNotification {
	
	private Long id;
	private String notificationChannel ;
	private  String Tittle ;
	private  String message;
	private String read;
	private String sentAt;
	private String markRead;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNotificationChannel() {
		return notificationChannel;
	}
	public void setNotificationChannel(String notificationChannel) {
		this.notificationChannel = notificationChannel;
	}
	public String getTittle() {
		return Tittle;
	}
	public void setTittle(String tittle) {
		Tittle = tittle;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getRead() {
		return read;
	}
	public void setRead(String read) {
		this.read = read;
	}
	public String getSentAt() {
		return sentAt;
	}
	public void setSentAt(String sentAt) {
		this.sentAt = sentAt;
	}
	public String getMarkRead() {
		return markRead;
	}
	public void setMarkRead(String markRead) {
		this.markRead = markRead;
	}

}
