package com.sd.Device.Management.controller;

import com.sd.Device.Management.dto.DeviceDTO;
import com.sd.Device.Management.service.device.DeviceService;
import com.sd.Device.Management.service.event.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/devices")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://angular.localhost", allowCredentials = "true")
public class DeviceController {
    private final DeviceService deviceService;
    private final EventService eventService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<DeviceDTO>> getAllDevices() {
        return new ResponseEntity<>(
                deviceService.findAll(),
                HttpStatus.OK
        );
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DeviceDTO> addDevice(@RequestBody DeviceDTO deviceDTO) {
        DeviceDTO device = deviceService.save(deviceDTO);
        eventService.createDevice(device);
        return new ResponseEntity<>(
                device,
                HttpStatus.CREATED
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{idDevice}")
    public ResponseEntity<DeviceDTO> updateDevice(@PathVariable("idDevice") UUID idDevice, @RequestBody DeviceDTO deviceDTO) {
        DeviceDTO device = deviceService.update(idDevice, deviceDTO);
        eventService.updateDevice(device);
        return new ResponseEntity<>(
                device,
                HttpStatus.OK
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{idDevice}")
    public ResponseEntity<Void> deleteDeviceById(@PathVariable("idDevice") UUID idDevice) {
        eventService.deleteDevice(idDevice);
        deviceService.deleteById(idDevice);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/{idUser}")
    public ResponseEntity<List<DeviceDTO>> getDevicesByUserId(@PathVariable("idUser") UUID idUser) {
        return new ResponseEntity<>(
                deviceService.findByPersonReferenceIdUser(idUser),
                HttpStatus.OK
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("device/{idDevice}")
    public ResponseEntity<DeviceDTO> getDeviceById(@PathVariable("idDevice") UUID idDevice) {
        Optional<DeviceDTO> deviceDTO = deviceService.findById(idDevice);
        return deviceDTO.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
