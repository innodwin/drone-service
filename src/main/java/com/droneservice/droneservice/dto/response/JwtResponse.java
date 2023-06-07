/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.droneservice.droneservice.dto.response;

import com.droneservice.droneservice.entity.User;



/**
 *
 * @author HP
 */
public class JwtResponse {
     private User user;
    private String jwtToken;
    private String refreshToken;

    public JwtResponse(User user, String jwtToken, String refreshToken) {
        this.user = user;
        this.jwtToken = jwtToken;
        this.refreshToken = refreshToken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
