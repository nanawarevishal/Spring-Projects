package com.bookcab.cabbooking.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookcab.cabbooking.Response.MessageResponse;

@RestController

public class HomeController {
    
    @GetMapping("/")
    public ResponseEntity<MessageResponse> homeHandler(){

        MessageResponse response = new MessageResponse("Welcome ola backend System...!");

        return new ResponseEntity<MessageResponse>(response,HttpStatus.OK);
    }
}
