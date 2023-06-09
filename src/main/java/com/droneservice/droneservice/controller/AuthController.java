/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.droneservice.droneservice.controller;

import com.droneservice.droneservice.dto.UserRequest;
import com.droneservice.droneservice.dto.request.JwtRequest;
import com.droneservice.droneservice.dto.request.RefreshTokenRequest;
import com.droneservice.droneservice.dto.response.JwtResponse;
import com.droneservice.droneservice.service.AuthService;
import com.droneservice.droneservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Hp
 */
@RestController
@CrossOrigin
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication Service Controller  ", description = "Auth Controller Exposes REST APIs for Account registration and authentication service")
@ApiResponse(responseCode = "201",
        description = "HTTP Status 201 Created")
public class AuthController {

    @Autowired
    private final AuthService authService;
    @Autowired
    private final UserService userService;

    @PostMapping({"/register"})
    public ResponseEntity<?> accountRegistration(@RequestBody @Valid UserRequest request) {
        return userService.create(request);
    }

    @PostMapping({"/login"})
    public JwtResponse accountAuthentication(@Valid @RequestBody JwtRequest jwtRequest) {
        return authService.createJwtToken(jwtRequest);
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody RefreshTokenRequest request) {
        return authService.refreshToken(request);
    }

}
