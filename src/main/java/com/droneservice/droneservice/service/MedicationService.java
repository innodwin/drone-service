/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.droneservice.droneservice.service;

import com.droneservice.droneservice.dto.request.MedicationRegistrationRequest;
import com.droneservice.droneservice.entity.MedicationRegistration;
import java.util.List;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Hp
 */
public interface MedicationService {
    ResponseEntity<?> registerMedication(MedicationRegistrationRequest medicationRegistrationRequest);
    List<MedicationRegistration> findAll();
}
