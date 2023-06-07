/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.droneservice.droneservice.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 *
 * @author HP
 */
@Setter
@Getter
@Service
public class MessageResponse {
    private String message;
    private int code;
    private Object data;

    public MessageResponse() {
    }

    public MessageResponse(String message, int code, Object data) {
        this.message = message;
        this.code = code;
        this.data = data;
    }

    public MessageResponse(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    public MessageResponse(String message) {
        this.message = message;
    }

	
}
