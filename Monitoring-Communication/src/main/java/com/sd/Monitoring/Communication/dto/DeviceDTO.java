package com.sd.Monitoring.Communication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeviceDTO {
    @JsonProperty("event")
    private String eventType;

    @JsonProperty("device")
    private String idDevice;

    @JsonProperty("user")
    private String idUser;

    @JsonProperty("max_consumption")
    private double consumption;
}