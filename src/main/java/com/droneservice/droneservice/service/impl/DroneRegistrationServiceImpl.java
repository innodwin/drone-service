/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.droneservice.droneservice.service.impl;

import com.droneservice.droneservice.dto.request.BatteryUpdateRequest;
import com.droneservice.droneservice.dto.request.DroneRegistrationRequest;
import com.droneservice.droneservice.dto.response.BatteryResponse;
import com.droneservice.droneservice.entity.DroneData;
import com.droneservice.droneservice.entity.DroneModel;
import com.droneservice.droneservice.entity.DroneState;
import com.droneservice.droneservice.repository.DroneDataRepository;
import com.droneservice.droneservice.repository.DroneModelRepository;
import com.droneservice.droneservice.repository.DroneStateRepository;
import com.droneservice.droneservice.repository.OrderDataRpository;
import com.droneservice.droneservice.response.MessageResponse;
import com.droneservice.droneservice.service.DroneRegistrationService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 *
 * @author Hp
 */
@Service
@AllArgsConstructor
public class DroneRegistrationServiceImpl implements DroneRegistrationService {

    @Autowired
    private final DroneDataRepository droneDataRepository;
    @Autowired
    private final DroneModelRepository droneModelRepository;
    @Autowired
    private final DroneStateRepository droneStateRepository;

    @Autowired
    private final OrderDataRpository orderDataRpository;

    /*
    *** This method returns all drone with selected page number
     */
    @Override
    public List<DroneData> findAll(int pageNo, int pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<DroneData> pagedResult = droneDataRepository.findAll(paging);
        return pagedResult.toList();
    }

    /*
    Drone finder by single ID
     */
    @Override
    public DroneData getSingleDroneData(Long id) {
        return droneDataRepository.findById(id).get();
    }

    @Override
    public ResponseEntity<?> registerDrone(DroneRegistrationRequest request) {/*
        This register drone details,it takes the model.default state
        */
        DroneModel droneModel = null;
        DroneData droneData = null;
        DroneState droneState = null;
        try {
            Long model = request.getModelId();
            Long doneStateDefault = Long.valueOf("1");
            try {
                droneState = droneStateRepository.findById(doneStateDefault).get();
            } catch (Exception e) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Could not register drone; "
                                + "kindly check the provided State ID ",
                                HttpStatus.BAD_REQUEST.value()));
            }
            droneModel = droneModelRepository.findById(model).get();
            if (droneModel == null) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Could not register drone; "
                                + "kindly check the provided Model ID ",
                                HttpStatus.BAD_REQUEST.value()));
            }
             droneData = new DroneData();
            droneData.setSerialNumber(request.getSerialNumber());
            droneData.setBatterryCapacity(request.getBatteryCapacity());
            droneData.setDroneState(droneState);
            droneData.setModel(droneModel);
            droneData.setDroneWeight(request.getWeight());
            droneData.setDroneStatus("1");
            droneData.setCreatedAt(LocalDateTime.now());
            droneData.setDescription(request.getDescription());
            droneData = droneDataRepository.save(droneData);

        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Could not register drone; kindly"
                            + " check the provided data ",
                            HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.ok(new MessageResponse("Drone registered successfully!",
                HttpStatus.CREATED.value(), droneData));
    }

    @Override
    public DroneData getDroneByDroneIdAndDroneStatus(Long droneId, DroneState droneStatus) {
        return droneDataRepository.findByDroneIdAndDroneState(droneId, droneStatus);
    }

    @Override
    public List<DroneData> getAllAvailableDroneForOrderLoading() {
        DroneState droneStateIdle = new DroneState();
        droneStateIdle.setStateId(1L);
        DroneState droneStateLoading = new DroneState();
        droneStateLoading.setStateId(2L);
        return droneDataRepository.findByDroneStateOrDroneState(droneStateIdle, droneStateLoading);
    }

    @Override
    public MessageResponse getDroneBatteryLevel(Long id) {
        MessageResponse messageResponse = new MessageResponse();
        Long battery = droneDataRepository.getDroneBatteryLevelByDroneId(id);
        if (battery != null) {
            messageResponse.setCode(200);
            messageResponse.setMessage("Request successful");
        } else {
            messageResponse.setCode(500);
            messageResponse.setMessage("Could not get battery level; kindly try again or check provided Drone Id");
        }
        BatteryResponse batteryResponse = new BatteryResponse(battery);
        messageResponse.setData(batteryResponse);
        return messageResponse;
    }

    @Override
    public MessageResponse updateBattery(BatteryUpdateRequest batteryUpdateRequest) {
        MessageResponse messageResponse = new MessageResponse();
        System.out.println("droneId " + batteryUpdateRequest.getDroneId());
        DroneData droneData = droneDataRepository.findById(batteryUpdateRequest.getDroneId()).get();
        droneData.setBatterryCapacity(batteryUpdateRequest.getBatteryCapacity());
        if (droneData != null) {
            messageResponse.setCode(200);
            messageResponse.setMessage("Request successful");
            droneDataRepository.save(droneData);
        } else {
            messageResponse.setCode(500);
            messageResponse.setMessage("Could not update battery level; kindly try again or check provided Drone Id");
        }
        messageResponse.setData(droneData);
        return messageResponse;
    }

    @Override
    public List<DroneData> getAllRegisteredDrone() {
        return droneDataRepository.findAll();
    }

    public ResponseEntity<?> flagReturnStatus(Long droneId) {
        MessageResponse messageResponse = new MessageResponse();
        DroneState droneState = new DroneState();
        DroneData droneData = droneDataRepository.findById(droneId).get();
        // droneData.setBatterryCapacity(batteryUpdateRequest.getBatteryCapacity());
        if (droneData != null) {
            Long countActiveOrder = orderDataRpository.getCountByStateAndDrone(6L, droneId);
            if (countActiveOrder > 0) {
                return ResponseEntity.ok(new MessageResponse("Failed; This Drone has product for delivery",
                        HttpStatus.BAD_REQUEST.value(), droneData));
            }
            droneState.setStateId(1L);
            droneData.setDroneState(droneState);
            droneDataRepository.save(droneData);
            return ResponseEntity.ok(new MessageResponse("Successful; Drone State reset",
                    HttpStatus.OK.value(), droneData));
        } else {
            return ResponseEntity.ok(new MessageResponse("Failed; Invalid Drone data please try again",
                    HttpStatus.BAD_REQUEST.value(), droneData));
        }

    }

}
