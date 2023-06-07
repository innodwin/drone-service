/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.droneservice.droneservice.dto.request;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author HP
 */
@Getter
@Setter
public class OrderDataItemStateRequest {
     private Long orderId;
      private Long productId;

    public OrderDataItemStateRequest(Long orderId, Long productId) {
        this.orderId = orderId;
        this.productId = productId;
    }
      
}
