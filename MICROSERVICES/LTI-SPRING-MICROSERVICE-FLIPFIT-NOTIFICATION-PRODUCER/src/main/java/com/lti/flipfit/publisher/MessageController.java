/**
 * 
 */
package com.lti.flipfit.publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lti.flipfit.dto.GymNotification;

import tools.jackson.databind.ObjectMapper;

/**
 * 
 */
@RestController
@RequestMapping("/publish")
public class MessageController {
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	private static final String TOPIC = "gym-notification-topic";

	@PostMapping
	public String publishMessage(@RequestBody GymNotification gymNotification) {

		ObjectMapper mapper = new ObjectMapper();
		String notificationJson = mapper.writeValueAsString(gymNotification);

		kafkaTemplate.send(TOPIC, notificationJson);
		return "Message sent: " + gymNotification.getMessage();

	}
}
