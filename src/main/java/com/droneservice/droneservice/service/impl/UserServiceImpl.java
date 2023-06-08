/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.droneservice.droneservice.service.impl;

import com.droneservice.droneservice.dto.UserRequest;
import com.droneservice.droneservice.entity.Role;
import com.droneservice.droneservice.entity.User;
import com.droneservice.droneservice.repository.RoleRepository;
import com.droneservice.droneservice.repository.UserRepository;
import com.droneservice.droneservice.response.MessageResponse;
import com.droneservice.droneservice.service.UserService;
import com.droneservice.droneservice.utils.UserUtil;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Hp
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserUtil userUtil;
    @Autowired
    private final RoleRepository roleRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Override
    public User getUserByUsername(String username) {
        return userUtil.getUserByUsername(username);
    }

    @Override
    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }

    /*
    this method registers users account with encrypted password to the database
        after it must have validated all the details
     */
    @Override
    public ResponseEntity<?> create(UserRequest request) {
        User newUser;
        Long userRole = 0L;
        try {
            if (request.getRole() == null) {
                userRole = 2L;
            } else {
                if (request.getRole() == 0L) {
                    userRole = 2L;
                } else {
                    userRole = request.getRole();
                }
            }
            //request.getRole()
            Role role = roleRepository.findById(userRole).orElse(null);
            System.out.println("role " + role.getRoleDescription());
            newUser = new User();
            newUser.setUserName(request.getUsername());
            newUser.setUserFirstName(request.getFirstName());
            newUser.setUserLastName(request.getLastName());
            newUser.setUserEmail(request.getEmail());
            newUser.setUserPassword(getEncodedPassword(request.getPassword()));
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            newUser.setRole(roles);
            newUser = userRepository.save(newUser);

        } catch (DataIntegrityViolationException ex) {
            System.out.println("ex " + ex.getMessage());
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username or email is already taken!", HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.ok(new MessageResponse("User registered successfully!", HttpStatus.CREATED.value(), newUser));

    }

}
