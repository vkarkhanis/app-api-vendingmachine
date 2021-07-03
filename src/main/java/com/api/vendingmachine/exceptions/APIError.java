package com.api.vendingmachine.exceptions;

import java.util.Arrays;
import java.util.List;

public class APIError {

    private ERROR_CODES status;
    private String message;
    private List<String> errors;

    public APIError(ERROR_CODES status, String message, List<String> errors) {
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public APIError(ERROR_CODES status, String message, String error) {
        super();
        this.status = status;
        this.message = message;
        errors = Arrays.asList(error);
    }
    
    public ERROR_CODES getStatus() {
        return status;
    }

    public void setStatus(ERROR_CODES status) {
        this.status = status;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
}
