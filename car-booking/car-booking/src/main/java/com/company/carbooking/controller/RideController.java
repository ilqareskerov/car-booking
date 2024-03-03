package com.company.carbooking.controller;

import com.company.carbooking.dtos.RideDto;
import com.company.carbooking.dtos.UserDto;
import com.company.carbooking.dtos.mapper.DtoMapper;
import com.company.carbooking.model.Driver;
import com.company.carbooking.model.Ride;
import com.company.carbooking.model.User;
import com.company.carbooking.request.RideRequest;
import com.company.carbooking.request.StartRideRequest;
import com.company.carbooking.response.MessageResponse;
import com.company.carbooking.service.DriverService;
import com.company.carbooking.service.RideService;
import com.company.carbooking.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ride")
public class RideController {
    private final RideService rideService;
    private final DriverService driverService;
    private final UserService userService;

    public RideController(RideService rideService, DriverService driverService, UserService userService) {
        this.rideService = rideService;
        this.driverService = driverService;
        this.userService = userService;
    }
    @GetMapping("/{rideId}")
    public ResponseEntity<RideDto> findRideById(@PathVariable("rideId") Long rideId){
        Ride ride = rideService.findRideById(rideId);
        RideDto rideDto = DtoMapper.toRideDto(ride);
        return ResponseEntity.accepted().body(rideDto);
    }
    @GetMapping("/request")
    public ResponseEntity<RideDto> userRequestRide(@RequestBody RideRequest rideRequest , @RequestHeader("Authorization") String token){
        User user = userService.getReqUserProfile(token);
        Ride ride = rideService.requestRide(rideRequest,user);
        RideDto rideDto = DtoMapper.toRideDto(ride);
        return ResponseEntity.accepted().body(rideDto);
    }
    @PutMapping("/accept/{rideId}")
    public ResponseEntity<MessageResponse> driverAcceptRide(@PathVariable("rideId") Long rideId ){
        rideService.acceptRide(rideId);
        MessageResponse messageResponse = new MessageResponse("Ride Accepted");
        return ResponseEntity.accepted().body(messageResponse);
    }
    @PutMapping("/decline/{rideId}")
    public ResponseEntity<MessageResponse> driverDeclineRide(@PathVariable("rideId") Long rideId , @RequestHeader("Authorization") String token){
        Driver driver = driverService.getReqDriverProfile(token);
        rideService.declineRide(rideId,driver.getId());
        MessageResponse messageResponse = new MessageResponse("Ride Declined");
        return ResponseEntity.accepted().body(messageResponse);
    }
    @PutMapping("/start/{rideId}")
    public ResponseEntity<MessageResponse> startRide(@PathVariable("rideId") Long rideId , @RequestBody StartRideRequest startRideRequest){
        rideService.startRide(rideId,startRideRequest.getOtp());
        MessageResponse messageResponse = new MessageResponse("Ride Started");
        return ResponseEntity.accepted().body(messageResponse);
    }


}
