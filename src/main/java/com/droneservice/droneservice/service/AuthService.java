/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.droneservice.droneservice.service;

import com.droneservice.droneservice.dto.request.JwtRequest;
import com.droneservice.droneservice.dto.request.RefreshTokenRequest;
import com.droneservice.droneservice.dto.response.JwtResponse;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Hp
 */
public interface AuthService {
    JwtResponse createJwtToken(JwtRequest jwtRequest);
    void authenticate(String userName, String userPassword);
    ResponseEntity<?> refreshToken(RefreshTokenRequest request);
}
