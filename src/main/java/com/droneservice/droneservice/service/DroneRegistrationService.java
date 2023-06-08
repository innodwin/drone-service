/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.droneservice.droneservice.service;

import com.droneservice.droneservice.dto.request.BatteryUpdateRequest;
import com.droneservice.droneservice.dto.request.DroneRegistrationRequest;
import com.droneservice.droneservice.entity.DroneData;
import com.droneservice.droneservice.entity.DroneState;
import com.droneservice.droneservice.response.MessageResponse;
import java.util.List;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Hp
 */
public interface DroneRegistrationService {
   ResponseEntity<?> registerDrone(DroneRegistrationRequest request); 
   DroneData getSingleDroneData(Long id);
   List<DroneData> findAll(int pageNo, int pageSize);
   DroneData getDroneByDroneIdAndDroneStatus(Long droneId, DroneState droneStatus);
   List<DroneData> getAllAvailableDroneForOrderLoading();
   MessageResponse getDroneBatteryLevel(Long id);
   MessageResponse updateBattery(BatteryUpdateRequest batteryUpdateRequest);
   List<DroneData> getAllRegisteredDrone();
   ResponseEntity<?> flagReturnStatus(Long droneId);
   
}
