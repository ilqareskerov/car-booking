package com.company.carbooking.dtos.mapper;

import com.company.carbooking.dtos.DriverDto;
import com.company.carbooking.dtos.RideDto;
import com.company.carbooking.dtos.UserDto;
import com.company.carbooking.model.Driver;
import com.company.carbooking.model.Ride;
import com.company.carbooking.model.User;

public class DtoMapper {
    public static DriverDto toDriverDto(Driver driver) {
        DriverDto driverDto = new DriverDto();
        driverDto.setId(driver.getId());
        driverDto.setName(driver.getName());
        driverDto.setEmail(driver.getEmail());
        driverDto.setMobile(driver.getMobile());
        driverDto.setRating(driver.getRating());
        driverDto.setLatitude(driver.getLatitude());
        driverDto.setLongitude(driver.getLongitude());
        driverDto.setRole(driver.getRole());
        driverDto.setVehicle(driver.getVehicle());
        return driverDto;
    }
    public static UserDto toUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getFullName());
        userDto.setEmail(user.getEmail());
        userDto.setMobile(user.getMobile());
        return userDto;
    }
    public static RideDto toRideDto(Ride ride) {
        RideDto rideDto = new RideDto();
        rideDto.setId(ride.getId());
        rideDto.setDestinationLatitude(ride.getDestinationLatitude());
        rideDto.setDestinationLongitude(ride.getDestinationLongitude());
        rideDto.setDestinationArea(ride.getDestinationArea());
        rideDto.setDistance(ride.getDistance());
        rideDto.setDriver(DtoMapper.toDriverDto(ride.getDriver()));
        rideDto.setFare(ride.getFare());
        rideDto.setOtp(ride.getOtp());
        rideDto.setPaymentDetails(ride.getPaymentDetails());
        rideDto.setPickupArea(ride.getPickupArea());
        rideDto.setUser(DtoMapper.toUserDto(ride.getUser()));
        rideDto.setStatus(ride.getStatus());
        rideDto.setStartTime(ride.getStartTime());
        rideDto.setEndTime(ride.getEndTime());
        return rideDto;
    }
}
