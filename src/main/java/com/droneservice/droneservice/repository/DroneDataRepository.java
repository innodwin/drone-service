/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.droneservice.droneservice.repository;

import com.droneservice.droneservice.entity.DroneData;
import com.droneservice.droneservice.entity.DroneState;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Hp
 */
public interface DroneDataRepository extends JpaRepository<DroneData, Long> {
     DroneData findByDroneIdAndDroneState(Long droneID,DroneState droneState);
     List<DroneData> findByDroneStateOrDroneState(DroneState droneStateIdle,DroneState droneStateLoading);
     @Query( value = "SELECT batterycapacity FROM drone_data  where droneid =?1" , nativeQuery = true)
      Long getDroneBatteryLevelByDroneId(Long droneId);
      
}
