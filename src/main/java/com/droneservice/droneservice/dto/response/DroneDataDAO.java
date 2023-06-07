/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.droneservice.droneservice.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author HP
 */
@Getter
@Setter
public class DroneDataDAO {

    public DroneDataDAO(String droneid, String description, String orderId) {
        this.droneid = droneid;
        this.description = description;
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "DroneDataDAO{" + "droneid=" + droneid + ", description=" + description + ", orderId=" + orderId + '}';
    }

    

    private String droneid;
    private String description;
    private String orderId;

}
