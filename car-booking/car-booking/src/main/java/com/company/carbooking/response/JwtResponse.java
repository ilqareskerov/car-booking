package com.company.carbooking.response;

import com.company.carbooking.domain.UserRole;

public class JwtResponse {
    private String jwt;
    private String message;
    private  boolean isAuthenticated;
    private boolean isError;
    private String errorDetails;
    private UserRole role;

    public JwtResponse() {
    }

    public JwtResponse(String jwt, String message, boolean isAuthenticated, boolean isError, String errorDetails, UserRole role) {
        this.jwt = jwt;
        this.message = message;
        this.isAuthenticated = isAuthenticated;
        this.isError = isError;
        this.errorDetails = errorDetails;
        this.role = role;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        isAuthenticated = authenticated;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    public String getErrorDetails() {
        return errorDetails;
    }

    public void setErrorDetails(String errorDetails) {
        this.errorDetails = errorDetails;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
