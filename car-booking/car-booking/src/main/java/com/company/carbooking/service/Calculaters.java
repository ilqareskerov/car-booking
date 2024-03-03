package com.company.carbooking.service;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class Calculaters {
    private static final double EARTH_RADIUS = 6371.0;
    public double calculateDistance(double sourceLat, double sourceLng, double desLat, double desLng) {
        double latDistance = Math.toRadians(desLat - sourceLat);
        double lngDistance = Math.toRadians(desLng - sourceLng);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(sourceLat)) * Math.cos(Math.toRadians(desLat))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * c;
    }
    public double calculateDuration(LocalDateTime startTime, LocalDateTime endTime) {
        Duration duration = Duration.between(startTime, endTime);
        return duration.getSeconds();
    }
    public double calculateFare(double distance){
        double baseFare = 11;
        double totalFare = baseFare + distance;
        return totalFare;
    }
}
