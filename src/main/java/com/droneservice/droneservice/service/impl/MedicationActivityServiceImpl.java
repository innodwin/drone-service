/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.droneservice.droneservice.service.impl;

import com.droneservice.droneservice.dto.request.OrderDataRequest;
import com.droneservice.droneservice.dto.request.OrderItemRequest;
import com.droneservice.droneservice.entity.DroneData;
import com.droneservice.droneservice.entity.DroneState;
import com.droneservice.droneservice.entity.MedicationRegistration;
import com.droneservice.droneservice.entity.OrderData;
import com.droneservice.droneservice.entity.OrderItem;
import com.droneservice.droneservice.repository.DroneDataRepository;
import com.droneservice.droneservice.repository.MedicationRegistrationRepository;
import com.droneservice.droneservice.repository.OrderDataRpository;
import com.droneservice.droneservice.repository.OrderItemRpository;
import com.droneservice.droneservice.response.MessageResponse;
import com.droneservice.droneservice.service.MedicationActivityService;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Hp
 */
@Service
@AllArgsConstructor
public class MedicationActivityServiceImpl implements MedicationActivityService {

    @Autowired
    private final MedicationRegistrationRepository medicationRegistrationRepository;
    @Autowired
    private final DroneDataRepository droneDataRepository;
    @Autowired
    private final OrderDataRpository orderDataRpository;
    @Autowired
    private final OrderItemRpository orderItemRpository;
    @Autowired
    private MessageResponse messageResponse = null;

    private MedicationRegistration medicationRegistration = null;

    @Override
    @Transactional
    public ResponseEntity<?> loadDroneWithMedication(OrderDataRequest request) {//register a new order
        OrderData orderData = new OrderData();
        MedicationRegistration medicationRegistration = new MedicationRegistration(); // Initialize medication product
        OrderItem orderItem = new OrderItem();
        OrderData checkDroneData = null;
        Long droneCurrentWeight = 0L;
        Long orderItemWeight = 0L;
        Long droneWeight = 0L;
        Long itemWeight = 0L;
        Set<OrderItem> orderItemResponse = new HashSet<OrderItem>();
        DroneState droneState = null;

        String responseMessageCharacter = "Order Request successful"; //default success message
        droneState = new DroneState(2L, ""); //default state for initial drone state
        DroneData droneData = droneDataRepository.findById(request.getDroneId()).get();//validate the drone
        if (droneData == null) {
            return ResponseEntity.ok(new MessageResponse("Failed; Drone current"
                    + " not active for new order", HttpStatus.FORBIDDEN.value(), droneData));
        }

        if (!(droneData.getDroneState().getStateId().equals(1L)
                || droneData.getDroneState().getStateId().equals(2L))) {
            return ResponseEntity.ok(new MessageResponse("Failed; Drone current"
                    + " not active for new order", HttpStatus.FORBIDDEN.value(), droneData));
        }

        if (droneData.getBatterryCapacity() >= 25L) {
            droneData.setDroneState(droneState);
        }
        droneData.setUpdatedAt(LocalDateTime.now());
        droneDataRepository.save(droneData);//update drone data
        orderData.setBaterryCapacity(droneData.getBatterryCapacity());
        droneWeight = droneData.getDroneWeight();
        orderData.setDroneData(droneData);
        orderData.setCreatedAt(LocalDateTime.now());
        try {
            checkDroneData = orderDataRpository.CheckForOpenOrder(1L, 2L,
                    request.getDroneId());//checking for an opened order
            if (checkDroneData == null) {
                if (droneData.getBatterryCapacity() >= 25L)//check battery level and prevent it for been in loading state
                {
                    orderData.setDroneState(droneState);
                }
                orderData = orderDataRpository.save(orderData);//if order does not exist create a new one
            } else {
                orderData = checkDroneData;
            }
        } catch (Exception e) {
        }
        Set<OrderItemRequest> medicationItem = new HashSet<OrderItemRequest>();
        medicationItem = request.getOrderItem();
        int count = 0;
        for (OrderItemRequest value : medicationItem) {

            orderItem = new OrderItem();
            orderItemWeight = 0L;
            droneCurrentWeight = orderItemRpository.getSumOfCurrentWeight(orderData.getOrderId());
            try {
                medicationRegistration = medicationRegistrationRepository.
                        findById(value.getMedicationid()).get();

                if (medicationRegistration != null) {
                    itemWeight = medicationRegistration.getWeight();
                }
            } catch (Exception e) {
                medicationRegistration = null;
                itemWeight = 0L;
            }
            orderItemWeight = droneCurrentWeight + itemWeight;
            if ((droneWeight > orderItemWeight)
                    && (medicationRegistration != null)) {
                orderItem.setAddress(value.getDeliveryAddress());
                orderItem.setMedicationRegistration(medicationRegistration);
                orderItem.setOrderData(orderData);
                orderItem.setCreatedAt(LocalDateTime.now());
                orderItem.setStatus(1L);
                orderItem.setDroneState(droneState);
                orderItem = orderItemRpository.save(orderItem);
                orderItemResponse.add(orderItem);
            }
        }
        orderData.setOrderItem(orderItemResponse);
        if (orderItemResponse.size() <= 0) {
            responseMessageCharacter = "Could not add products to placed order; "
                    + "kindly check the "
                    + "product ID or product weight ";
        }
        if (orderData == null) {
            messageResponse = new MessageResponse("Could not complete "
                    + "transactions please try again",
                    HttpStatus.BAD_REQUEST.value(), orderData);
        } else {
            messageResponse = new MessageResponse(responseMessageCharacter,
                    HttpStatus.CREATED.value(), orderData);
        }
        return ResponseEntity.ok(messageResponse);
    }

    @Override
    public List<OrderData> findAll(int pageNo, int pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<OrderData> pagedResult = orderDataRpository.findAll(paging);
        return pagedResult.toList();
    }

    @Override
    public DroneData updateDroneData(DroneData request, DroneState droneState) {
        DroneData droneData = droneDataRepository.findById(request.getDroneId()).orElse(null);
        assert droneData != null;
        droneData.setUpdatedAt(LocalDateTime.now());
        droneData.setDroneState(droneState);
        return droneDataRepository.save(droneData);
    }

    private MessageResponse validDroneState(DroneData droneData) {
        String enableState[] = {"idle", "loading"};
        String inActiveState[] = {"loaded", "delivering", "delivered", "returning"};
        MessageResponse result = new MessageResponse();
        boolean checkDroneState = Arrays.asList(enableState).
                contains(droneData.getDroneState().
                        getDescription().toLowerCase());
        if (checkDroneState) {
            if (droneData.getBatterryCapacity() > 25L) {

            }

        } else {
            result.setCode(300);
            result.setMessage("Failed! Drone can not be loaded ; Drone currently in " + droneData.getDroneState().
                    getDescription() + " Mode");
        }

        return result;
    }

    @Override
    public List<OrderData> findByDroneId(Long droneId) {
        DroneData droneData = new DroneData();
        droneData.setDroneId(droneId);
        return orderDataRpository.findByDroneData(droneData);
    }

    @Override
    public List<OrderData> findByDroneIdAndOrderState(Long droneId, Long OrderStateId) {
        DroneData droneData = new DroneData();
        droneData.setDroneId(droneId);
        DroneState droneState = new DroneState();
        droneState.setStateId(OrderStateId);
        return orderDataRpository.findByDroneDataAndDroneState(droneData, droneState);
    }

}
