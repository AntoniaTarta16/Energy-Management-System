package com.sd.Device.Management.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeviceEventDTO {
    @JsonProperty("event")
    private String eventType;

    @JsonProperty("device")
    private String idDevice;

    @JsonProperty("user")
    private String idUser;

    @JsonProperty("max_consumption")
    private double maxHourlyConsumption;
}
