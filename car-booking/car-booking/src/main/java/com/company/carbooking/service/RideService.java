package com.company.carbooking.service;

import com.company.carbooking.domain.RideStatus;
import com.company.carbooking.model.Driver;
import com.company.carbooking.model.Ride;
import com.company.carbooking.model.User;
import com.company.carbooking.repository.DriverRepository;
import com.company.carbooking.repository.RideRepository;
import com.company.carbooking.request.RideRequest;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class RideService {
    private final RideRepository rideRepository;
    private final DriverService driverService;
    private final Calculaters calculaters;
    private final DriverRepository driverRepository;

    public RideService(RideRepository rideRepository, DriverService driverService, Calculaters calculaters,
                       DriverRepository driverRepository) {
        this.rideRepository = rideRepository;
        this.driverService = driverService;
        this.calculaters = calculaters;
        this.driverRepository = driverRepository;
    }

    public Ride requestRide(RideRequest rideRequest , User user) {
        double pickupLatitude = rideRequest.getPickupLatitude();
        double pickupLongitude = rideRequest.getPickupLongitude();
        double destinationLatitude = rideRequest.getDestinationLatitude();
        double destinationLongitude = rideRequest.getDestinationLongitude();
        String pickupArea = rideRequest.getPickupArea();
        String destinationArea = rideRequest.getDestinationArea();
        Ride existingRide = new Ride();
        List<Driver> avaibleDrivers = driverService.getAvailableDrivers(pickupLatitude,pickupLongitude, existingRide);
        Driver nearestDriver = driverService.findNearestDriver(pickupLatitude,pickupLongitude,avaibleDrivers);
        if(nearestDriver == null ){
            throw new RuntimeException();
        }
        Ride ride = createRideRequest(user , nearestDriver , pickupLatitude , pickupLongitude , destinationLatitude , destinationLongitude , pickupArea ,destinationArea);
        return ride;
    }
    public Ride createRideRequest(User user , Driver nearestDriver , double pickupLatitude , double pickupLongitude , double destinationLatitude , double destinationLongitude, String pickupArea , String destinationArea){
        Ride ride  = new Ride();
        ride.setUser(user);
        ride.setDriver(nearestDriver);
        ride.setDestinationArea(destinationArea);
        ride.setPickupArea(pickupArea);
        ride.setPickupLatitude(pickupLatitude);
        ride.setPickupLongitude(pickupLongitude);
        ride.setDestinationLatitude(destinationLatitude);
        ride.setDestinationLongitude(destinationLongitude);
        ride.setStatus(RideStatus.REQUESTED);
        return rideRepository.save(ride);
    }
    public void acceptRide(Long rideId){
        Ride ride = findRideById(rideId);
        ride.setStatus(RideStatus.ACCEPTED);
        Driver driver = ride.getDriver();
        driver.setCurrentRide(ride);
        Random random = new Random();
        int otp = random.nextInt(9000) +1000;
        ride.setOtp(otp);
        driverRepository.save(driver);
        rideRepository.save(ride);
    }
    public void declineRide(Long rideId , Long driverId){
    Ride ride = findRideById(rideId);
    ride.getDeclinedDrivers().add(driverId);
    List<Driver> avaibleDrivers = driverService.getAvailableDrivers(ride.getPickupLatitude(),ride.getPickupLongitude(),ride);
    Driver nearestDriver = driverService.findNearestDriver(ride.getPickupLatitude(),ride.getPickupLongitude(),avaibleDrivers);
    ride.setDriver(nearestDriver);
    rideRepository.save(ride);
    }
    public void startRide(Long rideId , long opt){
        Ride ride = findRideById(rideId);
        if(ride.getOtp() != opt){
            throw new RuntimeException();
        }
        ride.setStatus(RideStatus.STARTED);
        ride.setStartTime(LocalDateTime.now());
        rideRepository.save(ride);
    }
    public void completeRide(Long rideId){
        Ride ride = findRideById(rideId);
        ride.setStatus(RideStatus.COMPLETED);
        ride.setEndTime(LocalDateTime.now());
        double distance = calculaters.calculateDistance(ride.getPickupLatitude(),ride.getPickupLongitude(),ride.getDestinationLatitude(),ride.getDestinationLongitude());
        LocalDateTime startTime = ride.getStartTime();
        LocalDateTime endTime = ride.getEndTime();
        Duration duration = Duration.between(startTime,endTime);
        long milliSeconds = duration.toMillis();
        double fare = calculaters.calculateFare(distance);
        ride.setDistance(Math.round(distance*100.0)/100.0);
        ride.setFare(Math.round(fare));
        ride.setDuration(milliSeconds);
        Driver driver = ride.getDriver();
        driver.getRides().add(ride);
        driver.setCurrentRide(null);
        Integer driversRevenue = (int) (driver.getTotalRevenue() + Math.round(fare* 0.8));
        driver.setTotalRevenue(driversRevenue);
        driverRepository.save(driver);
        rideRepository.save(ride);
    }
    public void cancleRide(Long rideId){
        Ride ride = findRideById(rideId);
        ride.setStatus(RideStatus.CANCELLED);
        rideRepository.save(ride);
    }
    public Ride findRideById(Long rideId){
        Optional<Ride> ride = rideRepository.findById(rideId);
        if(ride.isPresent()){
            return ride.get();
        }
        else{
            throw new RuntimeException();
        }
    }
}
