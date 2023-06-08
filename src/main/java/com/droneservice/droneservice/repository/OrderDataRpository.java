/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.droneservice.droneservice.repository;

import com.droneservice.droneservice.entity.DroneData;
import com.droneservice.droneservice.entity.DroneState;
import com.droneservice.droneservice.entity.OrderData;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Hp
 */
public interface OrderDataRpository extends  JpaRepository<OrderData, Long> {
     @Query( value = "SELECT orderdata.* FROM drone_data drone join order_data orderdata"
        + " on drone.droneid = orderdata.droneid  WHERE "
            + "(drone.dronestateid = ?1 or drone.dronestateid = ?2 ) and drone.droneid = ?3" , nativeQuery = true)
   public OrderData CheckForOpenOrder(Long droneIdleState ,Long droneLoadingState,Long orderId);
   
   public List<OrderData> findByDroneData(DroneData droneData);
   public List<OrderData> findByDroneDataAndDroneState(DroneData droneData,DroneState droneState);
   
 
    @Query( value = "SELECT COALESCE(COUNT(orderdata.droneid),0) FROM drone_data drone join order_data orderdata"
        + " on drone.droneid = orderdata.droneid  WHERE "
            + "orderdata.orderstateid != ?1  and drone.droneid = ?2" , nativeQuery = true)
   long  getCountByStateAndDrone(Long droneIdleState ,Long droneId);
}
