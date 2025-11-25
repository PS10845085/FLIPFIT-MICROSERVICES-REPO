package com.lti.flipfit.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lti.flipfit.bean.GymFlipFitNotification;
import com.lti.flipfit.service.GymFlipFitNotificationService;



@RestController
@RequestMapping("/api/notification")
public class GymFlifitNotificationController {
	
	 @Autowired
	 private GymFlipFitNotificationService notificationService;
	 
	 
	 @PostMapping
	    public ResponseEntity<GymFlipFitNotification> sendNotification(@RequestBody GymFlipFitNotification notificationRequest) {
		 //GymFlipFitNotification notification = notificationService.sendNotification(notificationRequest);
		 GymFlipFitNotification gymFlipFitNotification = new GymFlipFitNotification();
		 gymFlipFitNotification.setId(null);
		 gymFlipFitNotification.setTittle("Testing");
		 gymFlipFitNotification.setMessage("Hello");
		 gymFlipFitNotification.setNotificationChannel(" ");
		 return new ResponseEntity<>(gymFlipFitNotification, HttpStatus.OK);
	    }

}
