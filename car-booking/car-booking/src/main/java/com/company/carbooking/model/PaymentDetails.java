package com.company.carbooking.model;

import com.company.carbooking.domain.PaymentStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

public class PaymentDetails {
     private PaymentStatus paymentStatus;
     private String paymentId;
     private String paymentLinkId;
     private String paymentLinkReferenceId;
     private String paymentLinkStatus;

    public PaymentDetails() {
    }

    public PaymentDetails(PaymentStatus paymentStatus, String paymentId, String paymentLinkId, String paymentLinkReferenceId, String paymentLinkStatus) {
        this.paymentStatus = paymentStatus;
        this.paymentId = paymentId;
        this.paymentLinkId = paymentLinkId;
        this.paymentLinkReferenceId = paymentLinkReferenceId;
        this.paymentLinkStatus = paymentLinkStatus;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentLinkId() {
        return paymentLinkId;
    }

    public void setPaymentLinkId(String paymentLinkId) {
        this.paymentLinkId = paymentLinkId;
    }

    public String getPaymentLinkReferenceId() {
        return paymentLinkReferenceId;
    }

    public void setPaymentLinkReferenceId(String paymentLinkReferenceId) {
        this.paymentLinkReferenceId = paymentLinkReferenceId;
    }

    public String getPaymentLinkStatus() {
        return paymentLinkStatus;
    }

    public void setPaymentLinkStatus(String paymentLinkStatus) {
        this.paymentLinkStatus = paymentLinkStatus;
    }
}
