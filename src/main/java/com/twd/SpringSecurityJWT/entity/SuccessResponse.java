package com.twd.SpringSecurityJWT.entity;



public class SuccessResponse {
    private String message;

    public SuccessResponse(String message) {
        this.message = message;
    }

    // Getter and setter
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
