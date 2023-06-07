/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.droneservice.droneservice.dto.response;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author HP
 */
@Getter
@Setter
public class BatteryResponse {

    public BatteryResponse(Long battery) {
        this.battery = battery;
    }
    private Long battery;
    
}
