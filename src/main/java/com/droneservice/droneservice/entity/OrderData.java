/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.droneservice.droneservice.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.CascadeType;
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
@Table(name = "order_data")
public class OrderData implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderid")
    private Long orderId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "droneid",
            referencedColumnName = "droneid")
    @NotFound(action = NotFoundAction.EXCEPTION)
    private DroneData droneData;
    @Column(name = "battery_capacity", nullable = false)
    @NotNull
    private Long baterryCapacity;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "orderstateid",
            referencedColumnName = "stateid")
    @NotFound(action = NotFoundAction.EXCEPTION)
    private DroneState droneState;

    @OneToMany(mappedBy = "orderData", fetch = FetchType.EAGER,
                                       cascade = CascadeType.ALL)
   
    private Set<OrderItem> orderItem;

    public OrderData(DroneData droneId, Long baterryCapacity, LocalDateTime createdAt, DroneState droneState) {
        this.droneData = droneId;
        this.baterryCapacity = baterryCapacity;
        this.createdAt = createdAt;
        this.droneState = droneState;
    }

}

