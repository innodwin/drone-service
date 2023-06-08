/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.droneservice.droneservice.controller;

import com.droneservice.droneservice.entity.DroneState;
import com.droneservice.droneservice.repository.DroneStateRepository;
import java.util.List;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/v1/state")
@NoArgsConstructor
public class DroneStateServiceController {

    @Autowired
    private DroneStateRepository droneStateRepository;

    @GetMapping("")
    public List<DroneState> getAllState() {
        return droneStateRepository.findAll();
    }

}
