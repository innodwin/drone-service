/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.droneservice.droneservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.Range;

/**
 *
 * @author Hp
 */
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "drone_data")
public class DroneData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "droneid")
    private Long droneId;

    @Column(name = "serialnumber", nullable = false, length = 100)
    @NotNull(message = "serial number must not be empty")
    private Long serialNumber;

    @Column(name = "weight", nullable = false)
    @NotNull(message = "Drone Weight cannot be empty")
    @Min(value = 1, message = "Drone weight not not be lower than 1")
    @Max(value = 500, message = "Drone weight must not be heigher than 500")
    @Range(min = 0, max = 500, message = "Drone weight must be between 1 to 500")
    private Long droneWeight;

    @Column(name = "batterycapacity", nullable = false)
    @NotNull(message = "Drone Battery Capacity must not be empty")
    @Min(value = 0, message = "Battery Capacity must not be empty")
    @Max(value = 100, message = "Battery capacity must not be greater than 100")
    @Range(min = 0, max = 100, message = "Battery capacity must be between 0 to 100")
    private Long batterryCapacity;

    @Column(name = "status", nullable = false)
    @NotNull(message = "drone status is required")
    private String droneStatus;

    @Column(name = "description", nullable = true)
    private String description;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "modelid",
            referencedColumnName = "modelid")
    @NotFound(action = NotFoundAction.EXCEPTION)
    @NotNull
    private DroneModel model;

}
