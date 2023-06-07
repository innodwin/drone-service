/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.droneservice.droneservice.dto.request;

import java.util.Set;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author HP
 */
@Setter
@Getter
public class OrderDataRequest {
     private Long droneId;
     private Set<OrderItemRequest> orderItem;
    
}
