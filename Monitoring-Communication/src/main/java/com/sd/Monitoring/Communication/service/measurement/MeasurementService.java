package com.sd.Monitoring.Communication.service.measurement;

import com.sd.Monitoring.Communication.dto.MeasurementDTO;

import java.time.LocalDate;
import java.util.List;

public interface MeasurementService {
    MeasurementDTO saveMeasurement(MeasurementDTO measurementDTO);
    void deleteByDeviceId(String deviceId);
    boolean isConsumptionExceeded(MeasurementDTO measurementDTO);
    public List<MeasurementDTO> getConsumptionHistory(String deviceId, LocalDate date);
}
