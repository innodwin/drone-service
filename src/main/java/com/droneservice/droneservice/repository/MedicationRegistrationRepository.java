/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.droneservice.droneservice.repository;

import com.droneservice.droneservice.entity.MedicationRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Hp
 */
public interface MedicationRegistrationRepository extends  JpaRepository<MedicationRegistration, Long> {
    
}
