package com.company.carbooking.repository;

import com.company.carbooking.model.Ride;
import com.company.carbooking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
     User findByEmail(String email);
     @Query("select r from Ride r where r.user.id = :id and r.status = 'COMPLETED'")
     List<Ride> getCompletedRides(@Param("id") Long id);
}
