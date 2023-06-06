/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.droneservice.droneservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

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
@Table(name = "order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderrefid")
    private Long orderRefId;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "medicationId",
            referencedColumnName = "medicationId")
    @NotFound(action = NotFoundAction.EXCEPTION)
    private MedicationRegistration medicationRegistration;
    @Column(name = "address", nullable = false)
    @NotNull
    private String address;

    @Column(name = "current_status", nullable = false)
    @NotNull
    private Long status;
    @Column(name = "created_at", nullable = false)
    @NotNull
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "orderid", nullable = false)
    @JsonIgnore
    private OrderData orderData;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stateid", nullable = false)
    private DroneState droneState;
    
    public OrderItem(MedicationRegistration medicationRegistration, String address, Long status, LocalDateTime createdAt, OrderData orderData, DroneState droneState) {
        this.medicationRegistration = medicationRegistration;
        this.address = address;
        this.status = status;
        this.createdAt = createdAt;
        this.orderData = orderData;
        this.droneState = droneState;
    }

   

}

