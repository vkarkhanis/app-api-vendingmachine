package com.api.vendingmachine.exceptions;

public class OrderCreationException extends Exception {

    public OrderCreationException(String ex){
        super(ex);
    }

    public OrderCreationException(String message, Exception e){
        super(message, e);
    }
    
}
