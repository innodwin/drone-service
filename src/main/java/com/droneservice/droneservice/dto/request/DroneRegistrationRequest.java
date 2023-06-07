/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.droneservice.droneservice.dto.request;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author HP
 */
@Setter
@Getter
public class DroneRegistrationRequest {

    private String description;
    @NotNull(message = "Serial Number cannot be null")
    @Valid
    private Long serialNumber;
    
    @NotNull(message = "Drone Weight cannot be null")
    @Valid
    private Long weight;
    @NotNull(message = "Battery capacity cannot be null")
    @Valid
    private Long batteryCapacity;
    
    @NotNull(message = "Model Id cannot be null")
    @Valid
    private Long modelId;

}
