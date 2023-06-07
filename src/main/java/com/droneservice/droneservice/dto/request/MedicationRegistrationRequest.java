/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.droneservice.droneservice.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author HP
 */
@Getter
@Setter
public class MedicationRegistrationRequest  {
    @NotNull(message = "Medication name is required")
    private String description;
    @NotEmpty(message = "Medication Code is required")
    private String code;
    
    private String file;
    @NotNull(message = "Weight must not be empty")
    private Long weight;
}
