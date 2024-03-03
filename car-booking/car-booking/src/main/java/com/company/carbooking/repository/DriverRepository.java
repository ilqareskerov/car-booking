package com.company.carbooking.repository;

import com.company.carbooking.model.Driver;
import com.company.carbooking.model.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface DriverRepository extends JpaRepository<Driver, Long> {
    public Driver findByEmail(String email);
    @Query("select r from Ride r where r.driver.id=:id and r.status='REQUESTED'")
    public List<Ride> getAllocatedRides(@Param("id") Long id);
    @Query("select r from Ride r where r.driver.id=:id and r.status='COMPLETED'")
    public List<Ride> getCompletedRides(@Param("id") Long id);
}
