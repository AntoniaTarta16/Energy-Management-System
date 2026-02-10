package com.sd.Monitoring.Communication.controller;

import com.sd.Monitoring.Communication.dto.MeasurementDTO;
import com.sd.Monitoring.Communication.service.measurement.MeasurementService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/measurements")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://angular.localhost", allowCredentials = "true")
public class MeasurementController {
    private final MeasurementService measurementService;

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/consumption/{deviceId}/{date}")
    public List<MeasurementDTO> getConsumptionHistory(@PathVariable String deviceId,
                                                      @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return measurementService.getConsumptionHistory(deviceId, date);
    }
}
