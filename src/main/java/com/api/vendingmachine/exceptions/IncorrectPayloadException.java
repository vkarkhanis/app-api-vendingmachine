package com.api.vendingmachine.exceptions;

public class IncorrectPayloadException extends Exception {
    public IncorrectPayloadException(String ex){
        super(ex);
    }

    public IncorrectPayloadException(String ex, Exception e){
        super(ex, e);
    }
    
}
