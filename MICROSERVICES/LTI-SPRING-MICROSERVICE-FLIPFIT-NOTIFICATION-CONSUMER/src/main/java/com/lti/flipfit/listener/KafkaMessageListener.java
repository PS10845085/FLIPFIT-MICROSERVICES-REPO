/**
 * 
 */
package com.lti.flipfit.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.lti.flipfit.dto.GymNotification;
import com.lti.flipfit.service.EmailService;

import tools.jackson.databind.ObjectMapper;

/**
 * 
 */
@Service
public class KafkaMessageListener {
	
	@Autowired
	EmailService emailService;
	
	@KafkaListener(topics = "gym-notification-topic", groupId = "notification-group")
    public void listen(ConsumerRecord<String, String> consumerRecord) {
        System.out.println("Received message: " + consumerRecord);
        ObjectMapper mapper = new ObjectMapper();
        GymNotification notification = mapper.readValue(consumerRecord.value(), GymNotification.class);
        emailService.sendEmail(notification);
    }

}
