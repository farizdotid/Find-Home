package com.app.findhome.util.apiservice;

public class APIError {
    private boolean status;
    private String message;

    public APIError() {
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
