package com.api.vendingmachine.exceptions;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class APIExceptionHandler extends ResponseEntityExceptionHandler {

   @ExceptionHandler({OrderNotFound.class})
   protected ResponseEntity<APIError> handleEntityNotFound(OrderNotFound ex) {
       APIError apiError = new APIError(HttpStatus.NOT_FOUND, ex.getMessage(), new ArrayList<String>());
       return new ResponseEntity<APIError>(apiError, HttpStatus.NOT_FOUND);
   }

   @ExceptionHandler({OrderCreationException.class})
   protected ResponseEntity<APIError> handleEntityNotFound(OrderCreationException ex) {
       APIError apiError = new APIError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), new ArrayList<String>());
       return new ResponseEntity<APIError>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
   }

   @ExceptionHandler({IncorrectPayloadException.class})
   protected ResponseEntity<APIError> handleEntityNotFound(IncorrectPayloadException ex) {
       APIError apiError = new APIError(HttpStatus.BAD_REQUEST, ex.getMessage(), new ArrayList<String>());
       return new ResponseEntity<APIError>(apiError, HttpStatus.BAD_REQUEST);
   }

}