package com.sd.Monitoring.Communication.service.measurement;

import com.sd.Monitoring.Communication.dto.DeviceDTO;
import com.sd.Monitoring.Communication.dto.MeasurementDTO;
import com.sd.Monitoring.Communication.entity.Measurement;
import com.sd.Monitoring.Communication.mapper.MeasurementMapper;
import com.sd.Monitoring.Communication.repository.MeasurementRepository;
import com.sd.Monitoring.Communication.service.device.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MeasurementServiceImpl implements MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final MeasurementMapper measurementMapper;
    private final DeviceService deviceService;

    @Override
    @Transactional
    public MeasurementDTO saveMeasurement(MeasurementDTO measurementDTO) {
        LocalDateTime startDate = measurementDTO.getTimestamp().truncatedTo(ChronoUnit.HOURS);

        Optional<Measurement> existingMeasurement = measurementRepository.findByDeviceIdAndDate(measurementDTO.getDeviceId(), startDate);

        Measurement measurement;
        if(existingMeasurement.isPresent()) {
            measurement = existingMeasurement.get();
            measurement.setConsumption(measurement.getConsumption() + measurementDTO.getConsumption());
        }
        else
        {
            measurement = Measurement.builder()
                    .deviceId(measurementDTO.getDeviceId())
                    .date(startDate)
                    .consumption(measurementDTO.getConsumption())
                    .build();
        }
        Measurement measurementAdded = measurementRepository.save(measurement);
        return measurementMapper.toMeasurementDTO(measurementAdded);
    }

    @Override
    @Transactional
    public void deleteByDeviceId(String deviceId) {
        measurementRepository.deleteByDeviceId(deviceId);
    }

    @Override
    public boolean isConsumptionExceeded(MeasurementDTO measurementDTO) {
        DeviceDTO deviceDTO = deviceService.findById(measurementDTO.getDeviceId());
        return deviceDTO.getConsumption() < measurementDTO.getConsumption();
    }

    @Override
    public List<MeasurementDTO> getConsumptionHistory(String deviceId, LocalDate date) {
        List<Measurement> measurements = measurementRepository.findByDeviceIdAndDate(deviceId, date);
        return measurements.stream()
                .map(measurementMapper::toMeasurementDTO)
                .toList();
    }
}
