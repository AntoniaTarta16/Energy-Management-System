package com.sd.Device.Management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Table(name = "device")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_device", nullable = false)
    private UUID idDevice;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "maxHourlyEnergyConsumption", nullable = false)
    private double maxHourlyEnergyConsumption;

    @ManyToOne
    @JoinColumn(name = "id_person")
    private PersonReference personReference;
}
