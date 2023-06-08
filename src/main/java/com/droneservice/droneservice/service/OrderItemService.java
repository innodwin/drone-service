/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.droneservice.droneservice.service;

import org.springframework.http.ResponseEntity;

/**
 *
 * @author Hp
 */
public interface OrderItemService {
    ResponseEntity<?> changeProductDeliveryStatus(Long orderId, Long productId);
}
