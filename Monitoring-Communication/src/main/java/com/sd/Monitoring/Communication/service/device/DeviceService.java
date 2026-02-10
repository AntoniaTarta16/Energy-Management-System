package com.sd.Monitoring.Communication.service.device;

import com.sd.Monitoring.Communication.dto.DeviceDTO;

public interface DeviceService {
    void createDevice(DeviceDTO deviceDTO);
    void updateDevice(DeviceDTO deviceDTO);
    void deleteDevice(String idDevice);
    DeviceDTO findById(String idDevice);
}
