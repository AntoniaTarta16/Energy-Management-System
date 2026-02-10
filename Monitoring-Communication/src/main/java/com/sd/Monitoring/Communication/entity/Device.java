package com.sd.Monitoring.Communication.entity;

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
    @Column(name = "device", nullable = false)
    private String idDevice;

    @Column(name = "user")
    private String idUser;

    @Column(name = "consumption", nullable = false)
    private double consumption;
}
