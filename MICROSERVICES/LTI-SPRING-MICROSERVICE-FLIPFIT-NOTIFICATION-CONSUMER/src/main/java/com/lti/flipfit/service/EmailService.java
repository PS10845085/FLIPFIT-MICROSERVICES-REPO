/**
 * 
 */
package com.lti.flipfit.service;

import java.util.Properties;

import org.springframework.stereotype.Service;

import com.lti.flipfit.dto.GymNotification;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

/**
 * 
 */
@Service
public class EmailService {

	public static void sendEmail(GymNotification notification) {
		//String to = "customer@example.com"; // Replace with recipient
		String from = "noreply@flipfit.com";
		String host = "smtp.yourmailserver.com";

		/*
		 * Properties properties = System.getProperties();
		 * properties.put("mail.smtp.host", host); properties.put("mail.smtp.port",
		 * "587"); properties.put("mail.smtp.auth", "true");
		 * 
		 * Session session = Session.getDefaultInstance(properties);
		 */
		Properties properties = System.getProperties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");


		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("biki.sa07@gmail.com", "eylektcoxzizamoo");
			}
		});




		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(notification.getEmail()));
			message.setSubject(notification.getTittle());
			message.setContent(buildEmailTemplate(notification), "text/html");

			Transport.send(message);
			System.out.println("Email sent successfully!");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}

	private static String buildEmailTemplate(GymNotification notification) {
		return "<html><body>" +
				"<h2 style='color:#2563eb;'>"+ notification.getTittle() +"</h2>" +
				"<p> Dear "+ notification.getUserName() +",</p>" +
				"</br>" +
				"<p>"+ notification.getMessage() +"</p>" +
				"<p><strong>Sent At:</strong> "+ notification.getSentAt() +"</p>" +
				"<hr><p style='font-size:12px;color:#888;'>FlipFit Gym Notification</p>" +
				"</body></html>";
	}

}
