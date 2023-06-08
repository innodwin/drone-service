/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.droneservice.droneservice.controller;

import com.droneservice.droneservice.entity.DroneModel;
import com.droneservice.droneservice.repository.DroneModelRepository;
import java.util.List;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Hp
 */
@RestController
@CrossOrigin
@RequestMapping("/api/v1/model")
@NoArgsConstructor
public class DroneModelService {

    @Autowired
    private DroneModelRepository droneModelRepository;

    /*
    This method returns all Drone Models
     */
    @GetMapping("")
    @PreAuthorize("hasRole('admin')")
    public List<DroneModel> getAllModel() {
        return droneModelRepository.findAll();
    }

}
