/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.droneservice.droneservice.dto.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

/**
 *
 * @author HP
 */
@Setter
@Getter
public class BatteryUpdateRequest {
    @NotNull(message = "Drone Id cannot be null")
    private Long droneId;
    @NotNull(message = "Battery Capacity cannot be null")
     @Min(0)
    @Max(100)
    @Range(min = 0, max = 100)
    private Long batteryCapacity;
    
}
