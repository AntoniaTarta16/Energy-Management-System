package com.sd.Device.Management.service.event;

import com.sd.Device.Management.dto.DeviceDTO;

import java.util.UUID;

public interface EventService {
    void createDevice(DeviceDTO deviceDTO);
    void updateDevice(DeviceDTO deviceDTO);
    void deleteDevice(UUID idDevice);
}
