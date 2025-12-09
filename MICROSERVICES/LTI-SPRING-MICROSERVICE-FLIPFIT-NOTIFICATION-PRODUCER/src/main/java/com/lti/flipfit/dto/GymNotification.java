/**
 * 
 */
package com.lti.flipfit.dto;

/**
 * 
 */
public class GymNotification {
		
		private Integer id;
		
		private String notificationChannel ;
		
		private  String title ;
		
		private  String message;
		
		private  String userName;
		
		private  String email;
		
		private String read;
		
		private String sentAt;
		
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getNotificationChannel() {
			return notificationChannel;
		}
		public void setNotificationChannel(String notificationChannel) {
			this.notificationChannel = notificationChannel;
		}
		public String getTittle() {
			return title;
		}
		public void setTittle(String tittle) {
			title = tittle;
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
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		
}
