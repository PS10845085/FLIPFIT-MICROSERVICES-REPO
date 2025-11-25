package com.lti.flipfit.services;

import com.lti.flipfit.entity.GymFlipFitPayment;

public interface GymFlipFitPaymentService {
	public GymFlipFitPayment processPayment(GymFlipFitPayment payment);
	 public void generateInvoice(String customerName, GymFlipFitPayment plan, double amountPaid);
}
