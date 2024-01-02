package com.practiseservices.servicespractise.Exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptions {
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> UserExceptionHandler(RuntimeException ue,WebRequest request){
        
        ErrorDetails error = new ErrorDetails();

        error.setMessage(ue.getMessage());
        error.setError(request.getDescription(false));
        error.setTimeStamp(LocalDateTime.now());

        return new ResponseEntity<ErrorDetails>(error, HttpStatus.BAD_REQUEST);
    }

}
