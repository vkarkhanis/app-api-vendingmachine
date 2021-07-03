package com.api.vendingmachine.exceptions;

public class OrderNotFoundException extends Exception {

    public OrderNotFoundException(String ex) {
        super(ex);
    }

    public OrderNotFoundException(String message, Exception e){
        super(message, e);
    }
}
