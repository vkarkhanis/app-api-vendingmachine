package com.api.vendingmachine.exceptions;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

enum ERROR_CODES {
    ORDER_NOT_FOUND, ORDER_CREATION_FAILURE, INCORRECT_PAYLOAD, GENERIC_FAILURE, INSUFFICIENT_BALANCE
}

@ControllerAdvice
public class APIExceptionHandler extends ResponseEntityExceptionHandler {

   @ExceptionHandler({OrderNotFoundException.class})
   protected ResponseEntity<APIError> handleEntityNotFoundException(OrderNotFoundException ex) {
       APIError apiError = new APIError(ERROR_CODES.ORDER_NOT_FOUND, ex.getMessage(), new ArrayList<String>());
       return new ResponseEntity<APIError>(apiError, HttpStatus.NOT_FOUND);
   }

   @ExceptionHandler({OrderCreationException.class})
   protected ResponseEntity<APIError> handleOrderCreationException(OrderCreationException ex) {
       APIError apiError = new APIError(ERROR_CODES.ORDER_CREATION_FAILURE, ex.getMessage(), new ArrayList<String>());
       return new ResponseEntity<APIError>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
   }

   @ExceptionHandler({IncorrectPayloadException.class})
   protected ResponseEntity<APIError> handleIncorrectPayloadException(IncorrectPayloadException ex) {
       APIError apiError = new APIError(ERROR_CODES.INCORRECT_PAYLOAD, ex.getMessage(), new ArrayList<String>());
       return new ResponseEntity<APIError>(apiError, HttpStatus.BAD_REQUEST);
   }

   @ExceptionHandler({GenericException.class})
   protected ResponseEntity<APIError> handleGenericException(GenericException ex) {
       APIError apiError = new APIError(ERROR_CODES.GENERIC_FAILURE, ex.getMessage(), new ArrayList<String>());
       return new ResponseEntity<APIError>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
   }

   @ExceptionHandler({InsufficientBalanceException.class})
   protected ResponseEntity<APIError> handleInsufficientBalanceException(InsufficientBalanceException ex) {
       APIError apiError = new APIError(ERROR_CODES.INSUFFICIENT_BALANCE, ex.getMessage(), new ArrayList<String>());
       return new ResponseEntity<APIError>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
   }

}