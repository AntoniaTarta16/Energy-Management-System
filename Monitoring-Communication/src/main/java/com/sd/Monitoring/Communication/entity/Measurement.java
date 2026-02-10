package com.sd.Monitoring.Communication.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "consumption")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "device", nullable = false)
    private String deviceId;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "consumption", nullable = false)
    private double consumption;
}
