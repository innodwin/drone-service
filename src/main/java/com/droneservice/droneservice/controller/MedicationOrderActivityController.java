/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.droneservice.droneservice.controller;

import com.droneservice.droneservice.dto.request.OrderDataRequest;
import com.droneservice.droneservice.entity.OrderData;
import com.droneservice.droneservice.service.MedicationActivityService;
import java.util.List;
import javax.validation.Valid;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Hp
 */
@RestController
@CrossOrigin
@RequestMapping("/api/v1/order")
@NoArgsConstructor
public class MedicationOrderActivityController {
     @Autowired
    private MedicationActivityService medicationActivityService;

    @PostMapping("/register")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> registerOrder(@RequestBody @Valid OrderDataRequest orderDataRequest) {
        return medicationActivityService.loadDroneWithMedication(orderDataRequest);
    }

    @GetMapping("")
    public @ResponseBody
    Iterable<OrderData> getAllOrder(@RequestParam(value = "page") int pageNo,
            @RequestParam(value = "per_page") int pageSize) {
        return medicationActivityService.findAll(pageNo, pageSize);
    }

    @GetMapping("/drone")
    public ResponseEntity<List<OrderData>> getOrderByDroneId(@RequestParam(required = true) Long droneId) {
        List<OrderData> orderData = medicationActivityService.findByDroneId(droneId);
        return new ResponseEntity<>(orderData, HttpStatus.OK);
    }

    @GetMapping("/drone/{droneId}/{orderState}")
    public ResponseEntity<List<OrderData>> getOrderByDroneIdAndOrderState(@PathVariable Long droneId, @PathVariable Long orderState) {
        List<OrderData> orderData = medicationActivityService.findByDroneIdAndOrderState(droneId, orderState);
        return new ResponseEntity<>(orderData, HttpStatus.OK);
    } 
}
