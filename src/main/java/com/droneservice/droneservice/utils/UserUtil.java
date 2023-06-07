/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.droneservice.droneservice.utils;


import com.droneservice.droneservice.entity.User;
import com.droneservice.droneservice.exception.UserNotFoundException;
import com.droneservice.droneservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author HP
 */
@Service
@RequiredArgsConstructor
public class UserUtil {
    private final UserRepository userRepository;
    public User getLoggedInUser() throws UserNotFoundException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        String loggedInUserUserName = userDetails.getUsername();
        User userDetail = getUserByUsername(loggedInUserUserName);
        if(userDetail == null)
          new BadCredentialsException("INVALID_CREDENTIALS");
        return userDetail;
    }
   
    public User getUserByUsername(String username) {
        return userRepository.findByUserName(username)
                .orElseThrow(()->  new BadCredentialsException("INVALID_CREDENTIALS"));
    }
}