package com.lti.filpfit.rest;

import java.net.http.HttpResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lti.filpfit.beans.GymFlipFitNotification;
import com.lti.filpfit.services.GymFlipFitNotificationService;


/**
 * REST controller for managing notifications in the GymFlipFit application.
 *
 * <p>This controller provides an endpoint to send notifications to users. It delegates
 * the notification processing logic to {@code GymFlipFitNotificationService} and returns
 * the result in JSON format.</p>
 *
 * <h2>Responsibilities:</h2>
 * <ul>
 *     <li>Expose REST endpoint for sending notifications</li>
 *     <li>Handle HTTP requests and delegate business logic to the service layer</li>
 * </ul>
 *
 * <h2>Endpoints:</h2>
 * <ul>
 *     <li><b>POST /notification</b> - Send a notification to a user</li>
 * </ul>
 *
 * @author Team Bravo
 * @version v1.0.0
 * @see GymFlipFitNotification
 * @see GymFlipFitNotificationService
 */


@RestController
@RequestMapping("notification")
public class GymFlipFitNotificationController {
	
	@Autowired
	GymFlipFitNotificationService notificationService;

	@PostMapping
	public ResponseEntity<GymFlipFitNotification> sendNotification(@RequestBody GymFlipFitNotification notificationRequest) {
		GymFlipFitNotification notification = notificationService.sendNotification(notificationRequest);
		return new ResponseEntity(notification, HttpStatus.OK);
	}
}
