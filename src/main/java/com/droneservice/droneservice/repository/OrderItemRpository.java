/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.droneservice.droneservice.repository;

import com.droneservice.droneservice.entity.DroneState;
import com.droneservice.droneservice.entity.OrderData;
import com.droneservice.droneservice.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Hp
 */
public interface OrderItemRpository extends  JpaRepository<OrderItem, Long> {
  @Query(value = "SELECT COALESCE(SUM(md.weight),0) FROM order_item item join medication_data md"
        + " on item.medication_id = md.medication_id WHERE item.orderid = ?1" , nativeQuery = true)
    long getSumOfCurrentWeight(Long orderId);
    
    long countByOrderData(OrderData orderData);
    long countByOrderDataAndDroneState(OrderData orderData, DroneState droneState);
    OrderItem findByOrderRefIdAndOrderData(Long orderId,OrderData productId);
    
     @Query(value = "SELECT COALESCE(COUNT(item.orderid),0) FROM order_item item  "
             + "WHERE item.orderid = ?1 and item.orderrefid != ?2 and item.stateid = ?3" , nativeQuery = true)
    long getCOuntOfOtherActiveProduct(Long orderId , Long orderrefid , Long stateId);  
}
