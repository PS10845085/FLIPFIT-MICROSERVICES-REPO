package com.lti.filpfit.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.lti.filpfit.beans.GymFlipFitPayment;


/**
 * Service implementation for handling payment operations in the GymFlipFit application.
 *
 * <p>This service provides methods to process payments and generate invoices for customers.</p>
**/
@Service
public class GymFlipFitPaymentServiceImpl implements GymFlipFitPaymentService {
	
	private final List<GymFlipFitPayment> payments = new ArrayList<>();
		private final AtomicLong counter = new AtomicLong();


    /**
     * Processes a payment based on the specified payment method and amount.
     *
     * <p>Supported payment methods include:
     * <ul>
     *     <li>creditcard</li>
     *     <li>upi</li>
     *     <li>cash</li>
     * </ul>
     * If the payment method is invalid, the method returns false.</p>
     *
     * @param paymentMethod the payment method (e.g., "creditcard", "upi", "cash")
     * @param amount        the amount to be paid
     * @return {@code true} if the payment is processed successfully, {@code false} otherwise
     */
    public GymFlipFitPayment processPayment(GymFlipFitPayment payment) {
    	GymFlipFitPayment gymPayment = new GymFlipFitPayment();
    	gymPayment.setId(counter.incrementAndGet());
    	gymPayment.setMethod(payment.getMethod());
    	gymPayment.setAmount(payment.getAmount());
    	gymPayment.setPaidAt(new Date());
    	gymPayment.setStatus("PAID");
    	gymPayment.setBookingId(payment.getBookingId());
    	payments.add(gymPayment);
    	return gymPayment;
    	
    	
		/*switch (payment.getMethod().toLowerCase()) {
		case "creditcard":
			System.out.println("Processing Credit Card payment of ₹" + amount);
			return true;
		case "upi":
			System.out.println("Processing UPI payment of ₹" + amount);
			return true;
		case "cash":
			System.out.println("Processing Cash payment of ₹" + amount);
			return true;
		default:
			System.out.println("Invalid payment method!");
			return false;
		}*/
	}


    /**
     * Generates an invoice for a customer after payment.
     *
     * <p>The invoice includes customer name, amount paid, and the current date.
     * Additional details like plan name and duration can be included if available.</p>
     *
     * @param customerName the name of the customer
     * @param plan         the {@link GymFlipFitPayment} object representing the payment plan
     * @param amountPaid   the amount paid by the customer
     */
    public void generateInvoice(String customerName, GymFlipFitPayment plan, double amountPaid) {
		System.out.println("----- Gym Invoice -----");
		System.out.println("Customer: " + customerName);
		// System.out.println("Plan: " + plan.getPlanName());
		// System.out.println("Duration: " + plan.getDurationInMonths() + " months");
		System.out.println("Amount Paid: ₹" + amountPaid);
		System.out.println("Date: " + LocalDate.now());
		System.out.println("-----------------------");
	}

}
