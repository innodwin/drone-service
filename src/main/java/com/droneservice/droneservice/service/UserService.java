/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.droneservice.droneservice.service;

import com.droneservice.droneservice.dto.UserRequest;
import com.droneservice.droneservice.entity.User;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Hp
 */
public interface UserService {
    User getUserByUsername(String username);
    String getEncodedPassword(String password);
    ResponseEntity<?> create(UserRequest request);
}
