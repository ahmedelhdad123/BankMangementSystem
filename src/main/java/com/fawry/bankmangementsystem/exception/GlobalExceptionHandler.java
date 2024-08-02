package com.fawry.bankmangementsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccountException.class)
    public ResponseEntity<ApiException> handleAccountNotFound(AccountException ex) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        ApiException apiException=new ApiException(
                ex.getMessage(),
                status,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiException, status);
    }

    @ExceptionHandler(OperationException.class)
    public ResponseEntity<ApiException> handleOperationNotAllowed(OperationException ex) {

        HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;
        ApiException apiException=new ApiException(
                ex.getMessage(),
                status,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiException, status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiException> handleGenericException(Exception ex) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ApiException apiException=new ApiException(
                ex.getMessage(),
                status,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiException, status);
    }
}