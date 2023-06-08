/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.droneservice.droneservice.controller;

import com.droneservice.droneservice.dto.request.MedicationRegistrationRequest;
import com.droneservice.droneservice.entity.MedicationRegistration;
import com.droneservice.droneservice.service.MedicationService;
import java.util.List;
import javax.validation.Valid;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Hp
 */
@RestController
@CrossOrigin
@RequestMapping("/api/v1/medication")
@NoArgsConstructor
public class MedicationController {

    @Autowired
    private MedicationService medicationService;

    @PostMapping("/register")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> registerMedication(@RequestBody @Valid MedicationRegistrationRequest medicationRegistrationRequest) {
        return medicationService.registerMedication(medicationRegistrationRequest);
    }

    @GetMapping("")
    public List<MedicationRegistration> getAllMedicalItem() {
        return medicationService.findAll();
    }

}

