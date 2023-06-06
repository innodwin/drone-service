/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.droneservice.droneservice.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

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
@Table(name = "medication_data")
@Service
public class MedicationRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medicationId")
    private Long medicationId;

    @Column(name = "description", nullable = false)
    @NotNull
    private String description;

    @Column(name = "code", nullable = false)
    @NotEmpty
    private String code;

    @Column(name = "image_url", nullable = false)
    @NotNull
    private String imageUrl;
    private LocalDateTime createdAt;

    @Column(name = "weight", nullable = false)
    @NotNull
    private Long weight;

}
