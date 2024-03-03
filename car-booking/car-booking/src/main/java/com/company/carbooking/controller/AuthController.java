package com.company.carbooking.controller;

import com.company.carbooking.config.JwtUti;
import com.company.carbooking.domain.UserRole;
import com.company.carbooking.exception.UserException;
import com.company.carbooking.model.Driver;
import com.company.carbooking.model.User;
import com.company.carbooking.repository.DriverRepository;
import com.company.carbooking.repository.UserRepository;
import com.company.carbooking.request.DriverSignUpRequest;
import com.company.carbooking.request.SignInRequest;
import com.company.carbooking.request.SignUpRequest;
import com.company.carbooking.response.JwtResponse;
import com.company.carbooking.service.CustomUserDetailsService;
import com.company.carbooking.service.DriverService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    private final  UserRepository userRepository;
    private final  DriverRepository driverRepository;
    private final PasswordEncoder  passwordEncoder;
    private final JwtUti jwtUti;
    private final CustomUserDetailsService userDetailsService;
    private final DriverService driverService;

    public AuthController(UserRepository userRepository, DriverRepository driverRepository, PasswordEncoder passwordEncoder, JwtUti jwtUti, CustomUserDetailsService userDetailsService, DriverService driverService) {
        this.userRepository = userRepository;
        this.driverRepository = driverRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUti = jwtUti;
        this.userDetailsService = userDetailsService;
        this.driverService = driverService;
    }
    @PostMapping("/user/signup")
    public ResponseEntity<JwtResponse> signupHandler(@RequestBody SignUpRequest signUpRequest){
        String email = signUpRequest.getEmail();
        String password = signUpRequest.getPassword();
        String mobile = signUpRequest.getMobile();
        String fullName = signUpRequest.getFullName();
        User user = userRepository.findByEmail(email);
        if(user != null){
            throw new UserException("Email already exists");
        }
        String encodedPassword = passwordEncoder.encode(password);
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setFullName(fullName);
        newUser.setMobile(mobile);
        newUser.setPassword(encodedPassword);
        newUser.setRole(UserRole.USER);
        User savedUser = userRepository.save(newUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUti.generateToken(authentication);
        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setJwt(jwt);
        jwtResponse.setAuthenticated(true);
        jwtResponse.setRole(savedUser.getRole());
        jwtResponse.setError(false);
        jwtResponse.setMessage("User created successfully");
        jwtResponse.setErrorDetails(null);
        return ResponseEntity.ok(jwtResponse);
    }
    @PostMapping("/driver/signup")
    public ResponseEntity<JwtResponse> driverSignup(@RequestBody DriverSignUpRequest driverSignUpRequest){
       User user = userRepository.findByEmail(driverSignUpRequest.getEmail());
       Driver driver = driverRepository.findByEmail(driverSignUpRequest.getEmail());
       JwtResponse jwtResponse = new JwtResponse();
       if(driver != null || user != null){
                jwtResponse.setAuthenticated(false);
                jwtResponse.setError(true);
                jwtResponse.setMessage("Email already exists");
                return ResponseEntity.badRequest().body(jwtResponse);
       }
         Driver newDriver = driverService.registerDriver(driverSignUpRequest);
       Authentication authentication = authenticate(newDriver.getEmail(), driverSignUpRequest.getPassword());
       SecurityContextHolder.getContext().setAuthentication(authentication);
         String jwt = jwtUti.generateToken(authentication);
            jwtResponse.setJwt(jwt);
            jwtResponse.setAuthenticated(true);
            jwtResponse.setRole(UserRole.DRIVER);
            jwtResponse.setError(false);
            jwtResponse.setMessage("Driver created successfully");
            jwtResponse.setErrorDetails(null);
            return ResponseEntity.ok(jwtResponse);

    }
    @PostMapping("/user/signin")
    public ResponseEntity<JwtResponse> signinHandler(@RequestBody SignInRequest signUpRequest){
       String email = signUpRequest.getEmail();
       String password = signUpRequest.getPassword();
       Authentication authentication = authenticate(email, password);
         SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUti.generateToken(authentication);
            JwtResponse jwtResponse = new JwtResponse();
            jwtResponse.setJwt(jwt);
            jwtResponse.setAuthenticated(true);
            jwtResponse.setRole(UserRole.USER);
            jwtResponse.setError(false);
            jwtResponse.setMessage("User login successfully");
            jwtResponse.setErrorDetails(null);
            return ResponseEntity.ok(jwtResponse);
    }
    private Authentication authenticate(String email, String password){
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        if (userDetails != null){
            if (passwordEncoder.matches(password, userDetails.getPassword())){
                return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
            }
        }
        throw new BadCredentialsException("Invalid username or password");
    }
}
