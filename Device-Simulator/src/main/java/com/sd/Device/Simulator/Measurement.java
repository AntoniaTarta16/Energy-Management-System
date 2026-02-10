package com.sd.Device.Simulator;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Measurement {
    @JsonProperty("timestamp")
    private long timestamp;

    @JsonProperty("device_id")
    private String deviceId;

    @JsonProperty("measurement_value")
    private double value;
}
