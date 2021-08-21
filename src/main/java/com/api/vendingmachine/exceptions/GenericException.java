package com.api.vendingmachine.exceptions;

public class GenericException extends Exception {

    public GenericException(String ex){
        super(ex);
    }

    public GenericException(String ex, Exception e){
        super(ex, e);
    }
    
}
