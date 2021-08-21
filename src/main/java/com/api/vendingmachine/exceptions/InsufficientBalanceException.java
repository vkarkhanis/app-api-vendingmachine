package com.api.vendingmachine.exceptions;

public class InsufficientBalanceException extends Exception {
    
    public InsufficientBalanceException(String ex){
        super(ex);
    }

    public InsufficientBalanceException(String ex, Exception e){
        super(ex, e);
    }
}
