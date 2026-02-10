package com.sd.Device.Management.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeviceDTO {
    private UUID idDevice;
    private String description;
    private String address;
    private double maxHourlyEnergyConsumption;
    private UUID idUser;
}
