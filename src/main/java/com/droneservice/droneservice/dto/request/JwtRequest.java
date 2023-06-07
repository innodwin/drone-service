/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.droneservice.droneservice.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author HP
 */
@Setter
@Getter
public class JwtRequest {
    @NotBlank
   private String userName;
    @NotBlank
    private String userPassword;

   
}
