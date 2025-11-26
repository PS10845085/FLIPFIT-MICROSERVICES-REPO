package com.lti.flipfit.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lti.flipfit.beans.*;
import com.lti.flipfit.entity.*;
import com.lti.flipfit.entity.GymUser;
import com.lti.flipfit.dao.LoginDto;
import com.lti.flipfit.dao.UserDto;
import com.lti.flipfit.repository.GymUserRepository;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private GymUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String register(UserDto userDto) {
        if(userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        System.out.println("****************************userDto.getUsername()" +userDto.getUsername() );
        System.out.println("****************************userDto.getPassword()" +userDto.getPassword() );
        System.out.println("****************************userDto.getRole()" +userDto.getRoleid() );
        System.out.println("****************************userDto.getUsername()" +userDto.getUsername() );
        
        GymUser gymUser = new GymUser();
        gymUser.setUsername(userDto.getUsername());
        gymUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        gymUser.setRole(userDto.getRoleid());
        gymUser.setCreatedAt(LocalDateTime.now());
        GymUser user = userRepository.save(gymUser);
        return "User registered successfully";
    }

    @Override
    public String login(LoginDto loginDto) {
    	GymUser gymUser = userRepository.findByUsername(loginDto.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if(passwordEncoder.matches(loginDto.getPassword(), gymUser.getPassword())) {
            return "Login successful"; // Replace with JWT token generation
        }
        throw new RuntimeException("Invalid credentials");
    }



}
