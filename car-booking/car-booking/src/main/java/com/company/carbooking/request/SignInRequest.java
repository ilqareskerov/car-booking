package com.company.carbooking.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class SignInRequest {
    @NotBlank(message = "Email is Required")
    @Email(message = "Email is not valid")
    private String email;
    private String password;

    public SignInRequest() {
    }

    public SignInRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
