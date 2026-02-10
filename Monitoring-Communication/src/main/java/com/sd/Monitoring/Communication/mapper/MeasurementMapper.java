package com.sd.Monitoring.Communication.mapper;

import com.sd.Monitoring.Communication.dto.MeasurementDTO;
import com.sd.Monitoring.Communication.entity.Measurement;
import org.springframework.stereotype.Component;

@Component
public class MeasurementMapper {
    public MeasurementDTO toMeasurementDTO(Measurement measurement) {
        return MeasurementDTO.builder()
                .deviceId(measurement.getDeviceId())
                .timestamp(measurement.getDate())
                .consumption(measurement.getConsumption())
                .build();
    }

    public Measurement toMeasurement(MeasurementDTO measurementDTO) {
        return Measurement.builder()
                .deviceId(measurementDTO.getDeviceId())
                .date(measurementDTO.getTimestamp())
                .consumption(measurementDTO.getConsumption())
                .build();
    }

}
