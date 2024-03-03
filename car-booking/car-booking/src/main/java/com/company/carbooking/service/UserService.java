package com.company.carbooking.service;

import com.company.carbooking.config.JwtUti;
import com.company.carbooking.model.Ride;
import com.company.carbooking.model.User;
import com.company.carbooking.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final JwtUti jwtUti;

    public UserService(UserRepository userRepository, JwtUti jwtUti) {
        this.userRepository = userRepository;
        this.jwtUti = jwtUti;
    }

    public User createUser(User user) {
        User user1 = userRepository.findByEmail(user.getEmail());
        if (user1 != null) {
            throw new RuntimeException();
        }
        return userRepository.save(user);
    }
    public User getReqUserProfile(String token) {
        String email = jwtUti.getEmailFromToken(token);
        User user  = userRepository.findByEmail(email);
        if(user == null){
            throw new RuntimeException();
        }
        return user;
    }
    public User findUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(RuntimeException::new);
        if (user == null) {
            throw new RuntimeException();
        }
        return user;
    }
    public User findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException();
        }
        return user;
    }
    public User findUserByToken(String token) {
        String email = jwtUti.getEmailFromToken(token);
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException();
        }
        return user;
    }
    public List<Ride> completedRides(Long userId) {
        List<Ride> rides = userRepository.getCompletedRides(userId);
        return rides;
    }
}
