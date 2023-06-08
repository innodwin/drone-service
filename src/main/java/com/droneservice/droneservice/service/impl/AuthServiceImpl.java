/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.droneservice.droneservice.service.impl;

import com.droneservice.droneservice.dto.request.JwtRequest;
import com.droneservice.droneservice.dto.request.RefreshTokenRequest;
import com.droneservice.droneservice.dto.response.JwtResponse;
import com.droneservice.droneservice.dto.response.TokenRefreshResponse;
import com.droneservice.droneservice.entity.RefreshToken;
import com.droneservice.droneservice.exception.TokenRefreshException;
import com.droneservice.droneservice.service.AuthService;
import com.droneservice.droneservice.service.CustomUserDetailsService;
import com.droneservice.droneservice.service.RefreshTokenService;
import com.droneservice.droneservice.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 *
 * @author Hp
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;
    private final CustomUserDetailsService customUserDetailsService;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtResponse createJwtToken(JwtRequest jwtRequest) {
        String userName = jwtRequest.getUserName();
        String userPassword = jwtRequest.getUserPassword();
        authenticate(userName, userPassword);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userName);
        String newGeneratedToken = jwtUtil.generateToken(userDetails);

        return new JwtResponse(refreshToken.getUser(), newGeneratedToken, refreshToken.getToken());
    }

    @Override
    public void authenticate(String userName, String userPassword) {
        Authentication authentication = null;

        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userName, userPassword)
            );
        } catch (DisabledException e) {
            throw new DisabledException("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("INVALID_CREDENTIALS", e);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public ResponseEntity<?> refreshToken(RefreshTokenRequest request) {
        String requestRefreshToken = request.getRefreshToken();
        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtil.generateToken(customUserDetailsService.loadUserByUsername(user.getUserName()));
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                "Refresh token is not in database!"));
    }

}
