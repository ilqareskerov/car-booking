package com.company.carbooking.service;

import com.company.carbooking.config.JwtUti;
import com.company.carbooking.domain.RideStatus;
import com.company.carbooking.domain.UserRole;
import com.company.carbooking.model.Driver;
import com.company.carbooking.model.License;
import com.company.carbooking.model.Ride;
import com.company.carbooking.model.Vehicle;
import com.company.carbooking.repository.DriverRepository;
import com.company.carbooking.repository.LicenseRepository;
import com.company.carbooking.repository.RideRepository;
import com.company.carbooking.repository.VehicleRepository;
import com.company.carbooking.request.DriverSignUpRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DriverService {
    private final DriverRepository driverRepository;
    private Calculaters calculaters;
    private final PasswordEncoder passwordEncoder;
    private final JwtUti  jwtUti;
    private final VehicleRepository vehicleRepository;
    private final LicenseRepository licenseRepository;
    private final RideRepository rideRepository;
    public DriverService(DriverRepository driverRepository, Calculaters calculaters, PasswordEncoder passwordEncoder, JwtUti jwtUti, VehicleRepository vehicleRepository, LicenseRepository licenseRepository, RideRepository rideRepository) {
        this.driverRepository = driverRepository;
        this.calculaters = calculaters;
        this.passwordEncoder = passwordEncoder;
        this.jwtUti = jwtUti;
        this.vehicleRepository = vehicleRepository;
        this.licenseRepository = licenseRepository;
        this.rideRepository = rideRepository;
    }
    public Driver registerDriver(DriverSignUpRequest driverSignUpRequest){
        License license = driverSignUpRequest.getLicense();
        Vehicle vehicle = driverSignUpRequest.getVehicle();
        License createdLicense =new License();
        createdLicense.setLicenseNumber(license.getLicenseNumber());
        createdLicense.setLicenseState(license.getLicenseState());
        createdLicense.setLicenseExpiryDate(license.getLicenseExpiryDate());
        createdLicense.setId(license.getId());
        License savedLicense = licenseRepository.save(createdLicense);
        Vehicle createdVehicle = new Vehicle();
        createdVehicle.setCapacity(vehicle.getCapacity());
        createdVehicle.setMake(vehicle.getMake());
        createdVehicle.setModel(vehicle.getModel());
        createdVehicle.setYear(vehicle.getYear());
        createdVehicle.setColor(vehicle.getColor());
        createdVehicle.setLicensePlate(vehicle.getLicensePlate());
        createdVehicle.setId(vehicle.getId());
        Vehicle savedVehicle = vehicleRepository.save(createdVehicle);
        Driver driver = new Driver();
        String encodedPassword = passwordEncoder.encode(driverSignUpRequest.getPassword());
        driver.setPassword(encodedPassword);
        driver.setName(driverSignUpRequest.getName());
        driver.setEmail(driverSignUpRequest.getEmail());
        driver.setMobile(driverSignUpRequest.getMobile());
        driver.setLicense(savedLicense);
        driver.setVehicle(savedVehicle);
        driver.setRole(UserRole.DRIVER);
        driver.setLatitude(driverSignUpRequest.getLatitude());
        driver.setLongitude(driverSignUpRequest.getLongitude());
        Driver savedDriver = driverRepository.save(driver);
        savedLicense.setDriver(savedDriver);
        savedVehicle.setDriver(savedDriver);
        licenseRepository.save(savedLicense);
        vehicleRepository.save(savedVehicle);
        return savedDriver;
    }
    public List<Driver> getAvailableDrivers(double pickupLatitude, double pickupLongitude, Ride ride){
        List<Driver> drivers = driverRepository.findAll();
        drivers.stream().forEach(driver -> System.out.println(driver.getEmail()));
        List<Driver> availableDrivers = new ArrayList<>();
        double radius = 5.0;
        for (Driver driver: drivers) {
            if (driver.getCurrentRide()!=null && driver.getCurrentRide().getStatus()!= RideStatus.COMPLETED){
                continue;
            }
            if (ride.getDeclinedDrivers().contains(driver.getId())){
                System.out.println("declined driver");
                continue;
            }
            double distance = calculaters.calculateDistance(pickupLatitude,pickupLongitude,driver.getLatitude(),driver.getLongitude());
            if (distance <= radius){
                availableDrivers.add(driver);
            }
        }
        return availableDrivers;
    }
    public Driver findNearestDriver(double pickupLatitude, double pickupLongitude, List<Driver> drivers){
        double minDistance = Double.MAX_VALUE;
        Driver nearestDriver = null;
        for (Driver driver: drivers) {
            double distance = calculaters.calculateDistance(pickupLatitude,pickupLongitude,driver.getLatitude(),driver.getLongitude());
            if (distance < minDistance){
                minDistance = distance;
                nearestDriver = driver;
            }
        }
        return nearestDriver;
    }
    public Driver getReqDriverProfile(String token){
        String email = jwtUti.getEmailFromToken(token);
        if (email == null){
            throw new RuntimeException("Invalid token");
        }
        return driverRepository.findByEmail(email);
    }
    public Ride getDriversCurrentRide(Long driverId){
        Driver driver = driverRepository.findById(driverId).orElseThrow(()->new RuntimeException("Driver not found"));
        return driver.getCurrentRide();
    }
    public List<Ride> getAllocatedRides(Long driverId){
        List<Ride> allocatedRides = driverRepository.getAllocatedRides(driverId);
        return allocatedRides;
    }
    public Driver findDriverById(Long driverId){
        return driverRepository.findById(driverId).orElseThrow(()->new RuntimeException("Driver not found"));
    }
    public List<Ride> completedRides(Long driverId){
        return driverRepository.getCompletedRides(driverId);
    }
}
