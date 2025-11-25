package com.lti.flipfit.entity;

import java.math.BigDecimal;

import java.util.Date;

import com.lti.flipfit.constants.PaymentStatus;

import jakarta.persistence.*;

@Entity
@Table(name = "gympayment")
public class GymFlipFitPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paymentmodeid", referencedColumnName = "paymentmodeid")
    private GymPaymentMode paymentMode;

    @Column(name = "status", length = 45)
    private String status;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "paidAt", length = 45)
    private String paidAt; // Ideally should be LocalDateTime

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookingid", referencedColumnName = "id")
    private GymBooking booking;

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public GymPaymentMode getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(GymPaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(String paidAt) {
        this.paidAt = paidAt;
    }

    public GymBooking getBooking() {
        return booking;
    }

    public void setBooking(GymBooking booking) {
        this.booking = booking;
    }
}
