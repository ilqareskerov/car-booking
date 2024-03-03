package com.company.carbooking.controller;

import com.company.carbooking.response.MessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/")
    public ResponseEntity<MessageResponse> home(){
        MessageResponse messageResponse = new MessageResponse("Welcome to Car Booking");
        return ResponseEntity.accepted().body(messageResponse);
    }
}
