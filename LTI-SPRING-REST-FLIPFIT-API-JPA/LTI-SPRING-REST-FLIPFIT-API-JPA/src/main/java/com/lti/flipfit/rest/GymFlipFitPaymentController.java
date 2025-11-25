package com.lti.flipfit.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lti.flipfit.entity.GymFlipFitPayment;
import com.lti.flipfit.services.GymFlipFitPaymentService;

import jakarta.ws.rs.core.MediaType;


/**
 * REST controller for handling payment operations in the GymFlipFit application.
 *
 * <p>This controller provides an endpoint to process gym payments using different
 * payment methods. It delegates the payment processing logic to the service layer
 * and returns the result in JSON format.</p>
 *
 * <h2>Responsibilities:</h2>
 * <ul>
 *     <li>Expose REST endpoint for payment processing</li>
 *     <li>Handle HTTP requests and delegate business logic to {@code GymFlipFitPaymentService}</li>
 * </ul>
 *
 * <h2>Endpoints:</h2>
 * <ul>
 *     <li><b>POST /payment</b> - Process a payment with specified method and amount</li>
 * </ul>
 *
 * @author Team Bravo
 * @version v1.0.0
 * @see GymFlipFitPaymentService
 */


@RestController
public class GymFlipFitPaymentController {
	
	@Autowired 
	GymFlipFitPaymentService paymentService ;
	 
	@RequestMapping(value = "/payment", produces = MediaType.APPLICATION_JSON, method = RequestMethod.POST)
	    public ResponseEntity<GymFlipFitPayment> processGymPayment(@RequestBody GymFlipFitPayment payment) {
		GymFlipFitPayment processedPayment =  paymentService.processPayment(payment);
		return new ResponseEntity<>(processedPayment, HttpStatus.ACCEPTED);
	    }
}
