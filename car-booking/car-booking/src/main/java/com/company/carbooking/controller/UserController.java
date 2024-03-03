package com.company.carbooking.controller;

import com.company.carbooking.dtos.RideDto;
import com.company.carbooking.dtos.UserDto;
import com.company.carbooking.dtos.mapper.DtoMapper;
import com.company.carbooking.model.Ride;
import com.company.carbooking.model.User;
import com.company.carbooking.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> findUserById(@PathVariable("userId") Long userId){
        User user = userService.findUserById(userId);
        UserDto userDto = DtoMapper.toUserDto(user);
        return ResponseEntity.accepted().body(userDto);
    }
    @GetMapping("/profile")
    public ResponseEntity<UserDto> getReqUserProfile(@RequestHeader("Authorization") String token){
        User user = userService.getReqUserProfile(token);
        UserDto userDto = DtoMapper.toUserDto(user);
        return ResponseEntity.accepted().body(userDto);
    }
    @GetMapping("/rides/completed")
    public ResponseEntity<List<RideDto>> findCompletedRides(@RequestHeader("Authorization") String token){
        User user = userService.getReqUserProfile(token);
        List<Ride> ride = userService.completedRides(user.getId());
        List<RideDto> rideDto = ride.stream().map(DtoMapper::toRideDto).toList();
        return ResponseEntity.accepted().body(rideDto);
    }
}
