/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.droneservice.droneservice.service.impl;

import com.droneservice.droneservice.dto.request.OrderDataStateRequest;
import com.droneservice.droneservice.entity.DroneData;
import com.droneservice.droneservice.entity.DroneState;
import com.droneservice.droneservice.entity.OrderData;
import com.droneservice.droneservice.repository.DroneDataRepository;
import com.droneservice.droneservice.repository.OrderDataRpository;
import com.droneservice.droneservice.repository.OrderItemRpository;
import com.droneservice.droneservice.response.MessageResponse;
import com.droneservice.droneservice.service.OrderDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Hp
 */
@Service
@RequiredArgsConstructor
public class OrderDataServiceImpl implements OrderDataService{
    @Autowired
    private final DroneDataRepository droneDataRepository;
    @Autowired
    private final OrderDataRpository orderDataRpository;
    @Autowired
    private final OrderItemRpository orderItemRpository;
    @Autowired
    private MessageResponse messageResponse = null;

    @Transactional
    public ResponseEntity<?> changeOrderStatusLoaded(Long orderId) {
        OrderDataStateRequest request = new OrderDataStateRequest(orderId, 2L);
        OrderData orderData = orderDataRpository.getOne(request.getOrderId());
        DroneData droneData = null;
        DroneState droneState = new DroneState();

        if (orderData == null) {
            return ResponseEntity.ok(new MessageResponse("Failed; No Order found"
                    + " for the provided details", HttpStatus.BAD_REQUEST.value(), request));
        }
        if (!(orderData.getDroneState().getStateId().equals(2L) || orderData.getDroneState()
                .getStateId().equals(1L))) {
            return ResponseEntity.ok(new MessageResponse("Failed; This Product id status could not "
                    + "be changed ", HttpStatus.BAD_REQUEST.value(), request));
        }
        Long countOrderItem = orderItemRpository.countByOrderData(orderData);
        droneData = droneDataRepository.findById(orderData.getDroneData().getDroneId()).get();
        if (request.getOrderStateId().equals(1L) && countOrderItem == null) {
            droneState.setStateId(1L);
            droneData.setDroneState(droneState);
            droneDataRepository.save(droneData);
            return ResponseEntity.ok(new MessageResponse("Failed; No Order  item found"
                    + " for the provided order details", HttpStatus.BAD_REQUEST.value(), orderData));
        } else if (countOrderItem == null) {
            return ResponseEntity.ok(new MessageResponse("Failed; No Order  item found"
                    + " for the provided order details", HttpStatus.BAD_REQUEST.value(), orderData));
        }
        if (request.getOrderStateId() == 2) {
            droneState = new DroneState();
            droneState.setStateId(3L);
            orderData.setDroneState(droneState);
            orderDataRpository.save(orderData);
            droneData.setDroneState(droneState);
            droneDataRepository.save(droneData);
            return ResponseEntity.ok(new MessageResponse("Order State changed succesfully", HttpStatus.OK.value(), orderData));
        } else {
            return ResponseEntity.ok(new MessageResponse("Failed; Couldnt change status try again", HttpStatus.BAD_REQUEST.value(), orderData));
        }
        // return null;
    }
}
