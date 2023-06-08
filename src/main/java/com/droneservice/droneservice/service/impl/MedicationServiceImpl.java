/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.droneservice.droneservice.service.impl;

import com.droneservice.droneservice.dto.request.MedicationRegistrationRequest;
import com.droneservice.droneservice.dto.response.FileUploadResponse;
import com.droneservice.droneservice.entity.MedicationRegistration;
import com.droneservice.droneservice.repository.MedicationRegistrationRepository;
import com.droneservice.droneservice.response.MessageResponse;
import com.droneservice.droneservice.service.MedicationService;
import com.droneservice.droneservice.utils.ApplicationValidator;
import com.droneservice.droneservice.utils.FileUpload;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
public class MedicationServiceImpl implements MedicationService {

    @Autowired
    private final MedicationRegistrationRepository medicationRegistrationRepository;
    @Autowired
    private final ApplicationValidator applicationValidator;
    @Autowired
    private final FileUpload fileUpload;

    @Transactional
    public ResponseEntity<?> registerMedication(MedicationRegistrationRequest medicationRegistrationRequest) {
        MedicationRegistration request = new MedicationRegistration();
        boolean isValidName = false;
        FileUploadResponse fileUploadResponse = null;
        try {
            isValidName = applicationValidator.validateMedicationName(medicationRegistrationRequest.getDescription());
            if (!isValidName) {
                throw new IllegalStateException("Medication name " + medicationRegistrationRequest.getDescription() + " is not Valid");
            }
            Boolean validateCode = applicationValidator.validateMedicationCode(medicationRegistrationRequest.getCode());
            if (validateCode == false) {
                return ResponseEntity.ok(new MessageResponse("Failed, Invalid medication code; please "
                        + " use upper casses , numbers and underscore", HttpStatus.OK.value(), medicationRegistrationRequest));
            }
            fileUploadResponse = fileUpload.convertBase64ToImage(medicationRegistrationRequest.getFile(), 3L);
            if (fileUploadResponse == null) {
                throw new IllegalStateException("Valid Medication Image is required");
            }
            request.setCode(medicationRegistrationRequest.getCode());
            request.setDescription(medicationRegistrationRequest.getDescription());
            request.setCreatedAt(LocalDateTime.now());
            request.setImageUrl(fileUploadResponse.getName());
            request.setWeight(medicationRegistrationRequest.getWeight());
            request = request = medicationRegistrationRepository.save(request);

        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Medication Id  is already taken!"));
        }
        return ResponseEntity.ok(new MessageResponse("Medication registered successfully!", HttpStatus.CREATED.value(), request));
    }

    public List<MedicationRegistration> findAll() {
        return medicationRegistrationRepository.findAll();
    }
}
