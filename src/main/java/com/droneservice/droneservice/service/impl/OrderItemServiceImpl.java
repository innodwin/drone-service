/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.droneservice.droneservice.service.impl;

import com.droneservice.droneservice.dto.request.OrderDataItemStateRequest;
import com.droneservice.droneservice.entity.DroneData;
import com.droneservice.droneservice.entity.DroneState;
import com.droneservice.droneservice.entity.OrderData;
import com.droneservice.droneservice.entity.OrderItem;
import com.droneservice.droneservice.repository.DroneDataRepository;
import com.droneservice.droneservice.repository.OrderDataRpository;
import com.droneservice.droneservice.repository.OrderItemRpository;
import com.droneservice.droneservice.response.MessageResponse;
import com.droneservice.droneservice.service.OrderItemService;
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
public class OrderItemServiceImpl implements OrderItemService{
    @Autowired
    private final DroneDataRepository droneDataRepository;
    @Autowired
    private final OrderDataRpository orderDataRpository;
    @Autowired
    private final OrderItemRpository orderItemRpository;
    @Autowired
    private MessageResponse messageResponse = null;

    @Transactional
    public ResponseEntity<?> changeProductDeliveryStatus(Long orderId, Long productId) {
        OrderDataItemStateRequest request = new OrderDataItemStateRequest(orderId, productId);
        OrderData orderData = new OrderData();
        orderData.setOrderId(request.getOrderId());
        OrderItem orderItem = orderItemRpository.findByOrderRefIdAndOrderData(request.getProductId(), orderData);
        DroneData droneData = null;
        DroneState droneState = new DroneState();
        orderData = orderDataRpository.findById(request.getOrderId()).get();
        if (orderItem == null) {
            return ResponseEntity.ok(new MessageResponse("Failed; This Product id "
                    + "could not be found in this order ", HttpStatus.BAD_REQUEST.value(), request));
        }
        if (!orderItem.getDroneState().getStateId().equals(2L)) {
            return ResponseEntity.ok(new MessageResponse("Failed; This Product id "
                    + "has been delievered", HttpStatus.BAD_REQUEST.value(), request));
        }

        droneState = new DroneState();
        droneState.setStateId(5L);
        orderItem.setDroneState(droneState);
        if (!(orderData.getDroneState().getStateId().equals(3L) || orderData.getDroneState()
                .getStateId().equals(4L))) {
            return ResponseEntity.ok(new MessageResponse("Failed; This Product id status could not "
                    + "be changed ", HttpStatus.BAD_REQUEST.value(), request));
        }
        droneData = droneDataRepository.findById(orderData.getDroneData().getDroneId()).get();
        orderItemRpository.save(orderItem);
        Long countOrderItem = orderItemRpository.getCOuntOfOtherActiveProduct(request.getOrderId(),
                request.getOrderId(), 2L);
        if (countOrderItem == null || countOrderItem <= 0) {

            droneData.setDroneState(droneState);
            droneDataRepository.save(droneData);
            droneState.setStateId(6L);
            orderData.setDroneState(droneState);
            orderDataRpository.save(orderData);
            return ResponseEntity.ok(new MessageResponse("Successful ; Product delievered "
                    + "and drone returning", HttpStatus.OK.value(), orderItem));
        } else if (countOrderItem > 0) {
            droneState = new DroneState();
            droneState.setStateId(5L);
            orderItem.setDroneState(droneState);
            orderItemRpository.save(orderItem);
            droneState = new DroneState();
            droneState.setStateId(4L);
            orderData.setDroneState(droneState);
            orderDataRpository.save(orderData);
            droneData.setDroneState(droneState);
            droneDataRepository.save(droneData);
            return ResponseEntity.ok(new MessageResponse("Successful ; Product delievered "
                    + "and drone proceeding", HttpStatus.OK.value(), orderItem));
        }
        return null;
    }
}
