package com.company.carbooking.controller;

import com.company.carbooking.model.Driver;
import com.company.carbooking.model.Ride;
import com.company.carbooking.service.DriverService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/driver")
public class DriverController {
    private final DriverService driverService;
    public DriverController( DriverService driverService) {
        this.driverService = driverService;
    }
    @GetMapping("/profile")
    public ResponseEntity<Driver> getReqDriverProfileHandler(@RequestHeader("Authorization") String token){
        Driver driver = driverService.getReqDriverProfile(token);
        return ResponseEntity.ok(driver);
    }
    @GetMapping("/{driverId}/current_ride")
    public ResponseEntity<Ride> getDriversCurrentRideHandler(@PathVariable("driverId") Long driverId){
        Ride ride = driverService.getDriversCurrentRide(driverId);
        return ResponseEntity.accepted().body(ride);
    }
    @GetMapping("/{driverId}/allocated_rides")
    public ResponseEntity<List<Ride> > getAllocatedRidesHandler(@PathVariable("driverId") Long driverId){
        List<Ride> ride = driverService.getAllocatedRides(driverId);
        return ResponseEntity.accepted().body(ride);
    }
    @GetMapping("rides/completed")
    public ResponseEntity<List<Ride> > getCompletedRidesHandler(@RequestHeader("Authorization") String token){
        Driver driver = driverService.getReqDriverProfile(token);
        List<Ride> ride = driverService.completedRides(driver.getId());
        return ResponseEntity.accepted().body(ride);
    }
}
