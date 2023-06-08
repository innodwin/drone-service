/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.droneservice.droneservice.controller;

import com.droneservice.droneservice.dto.request.DroneRegistrationRequest;
import com.droneservice.droneservice.entity.DroneData;
import com.droneservice.droneservice.entity.DroneState;
import com.droneservice.droneservice.response.MessageResponse;
import com.droneservice.droneservice.service.DroneRegistrationService;
import java.util.List;
import javax.validation.Valid;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/v1/drone")
@NoArgsConstructor
public class DroneRegistrationController {

    @Autowired
    private DroneRegistrationService droneRegistrationService;

    @PostMapping({"/create"})
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> registerDrone(@RequestBody @Valid DroneRegistrationRequest droneRegistrationRequest) {

        return droneRegistrationService.registerDrone(droneRegistrationRequest);
    }

    @GetMapping("/droneData")
    public @ResponseBody
    Iterable<DroneData> getAllUsers(@RequestParam(value = "page") int pageNo, @RequestParam(value = "per_page") int pageSize) {
        return droneRegistrationService.findAll(pageNo, pageSize);
    }

    @GetMapping("/droneData/{droneId}")
    public @ResponseBody
    DroneData getDroneDetails(@PathVariable Long droneId) {
        return droneRegistrationService.getSingleDroneData(droneId);
    }

    @GetMapping("/droneData/{droneId}/{droneState}")
    public @ResponseBody
    DroneData getDroneByIdAndState(@PathVariable Long droneId, @PathVariable DroneState droneState) {
        return droneRegistrationService.getDroneByDroneIdAndDroneStatus(droneId, droneState);
    }

    @GetMapping("/checkAvailability")
    public List<DroneData> getDroneByIdAndState() {
        return droneRegistrationService.getAllAvailableDroneForOrderLoading();
    }

    @GetMapping("/droneBattery/{droneId}")
    public @ResponseBody
    MessageResponse getDroneBattery(@PathVariable Long droneId) {
        return droneRegistrationService.getDroneBatteryLevel(droneId);
    }
}
