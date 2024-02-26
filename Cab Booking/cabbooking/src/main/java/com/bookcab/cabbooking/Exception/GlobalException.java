package com.bookcab.cabbooking.Exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalException {
    
    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorDetail> userExceptionHandler(UserException e,WebRequest request){

        ErrorDetail error = new ErrorDetail();
        error.setError(e.getMessage());
        error.setDetails(request.getDescription(false));
        error.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<ErrorDetail>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DriverException.class)
    public ResponseEntity<ErrorDetail> driverExceptionHandler(DriverException e,WebRequest request){
        ErrorDetail error = new ErrorDetail();
        error.setError(e.getMessage());
        error.setDetails(request.getDescription(false));
        error.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<ErrorDetail>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RideException.class)
    public ResponseEntity<ErrorDetail> rideExceptionHandler(RideException e,WebRequest request){
        ErrorDetail error = new ErrorDetail();
        error.setError(e.getMessage());
        error.setDetails(request.getDescription(false));
        error.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<ErrorDetail>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDetail> handleValidationExceptionHandler(ConstraintViolationException ex){

        StringBuilder errorMsg = new StringBuilder();

        for(ConstraintViolation<?>violation :ex.getConstraintViolations()){
            errorMsg.append(violation.getMessage()+"\n");
        }

        ErrorDetail error = new ErrorDetail();
        error.setError(errorMsg.toString());
        error.setDetails("Validation error");
        error.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<ErrorDetail>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetail> methodAurgumentNotValidExceptionHandler(MethodArgumentNotValidException ex){

        ErrorDetail error = new ErrorDetail();
        error.setError(ex.getBindingResult().getFieldError().getDefaultMessage());
        error.setDetails("Validation error");
        error.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<ErrorDetail>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetail> otherExceptionHandler(Exception ex,WebRequest request){

        ErrorDetail error = new ErrorDetail();
        error.setError(ex.getMessage());
        error.setDetails(request.getDescription(false));
        error.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<ErrorDetail>(error, HttpStatus.BAD_REQUEST);
    }
}
