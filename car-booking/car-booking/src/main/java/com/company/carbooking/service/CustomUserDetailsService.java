package com.company.carbooking.service;

import com.company.carbooking.model.Driver;
import com.company.carbooking.model.User;
import com.company.carbooking.repository.DriverRepository;
import com.company.carbooking.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class CustomUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService{
    private final DriverRepository driverRepository;
    private final UserRepository userRepository;
    public CustomUserDetailsService(DriverRepository driverRepository, UserRepository userRepository) {
        this.driverRepository = driverRepository;
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<GrantedAuthority> authorities = new ArrayList<>();
        User user = userRepository.findByEmail(username);
        if(user != null) {
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
        }
        Driver driver = driverRepository.findByEmail(username);
        if(driver != null) {
            return new org.springframework.security.core.userdetails.User(driver.getEmail(), driver.getPassword(), authorities);
        }
        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
