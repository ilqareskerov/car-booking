package com.company.carbooking.request;

public class StartRideRequest {
    private Long otp;

    public StartRideRequest() {
    }

    public StartRideRequest(Long otp) {
        this.otp = otp;
    }

    public Long getOtp() {
        return otp;
    }

    public void setOtp(Long otp) {
        this.otp = otp;
    }
}
