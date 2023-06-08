/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.droneservice.droneservice.service;

import com.droneservice.droneservice.dto.request.OrderDataRequest;
import com.droneservice.droneservice.entity.DroneData;
import com.droneservice.droneservice.entity.DroneState;
import com.droneservice.droneservice.entity.OrderData;
import java.util.List;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Hp
 */
public interface MedicationActivityService {
    ResponseEntity<?> loadDroneWithMedication(OrderDataRequest request);
    List<OrderData> findAll(int pageNo, int pageSize);
    DroneData updateDroneData(DroneData request, DroneState droneState);
    List<OrderData> findByDroneId(Long droneId);
    List<OrderData> findByDroneIdAndOrderState(Long droneId, Long OrderStateId);
}
