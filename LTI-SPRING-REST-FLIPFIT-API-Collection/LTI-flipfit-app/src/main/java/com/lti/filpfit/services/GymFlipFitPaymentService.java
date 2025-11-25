package com.lti.filpfit.services;

import com.lti.filpfit.beans.GymFlipFitPayment;

public interface GymFlipFitPaymentService {
	public GymFlipFitPayment processPayment(GymFlipFitPayment payment);
	 public void generateInvoice(String customerName, GymFlipFitPayment plan, double amountPaid);
}
