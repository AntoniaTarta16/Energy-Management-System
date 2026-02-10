package com.sd.Monitoring.Communication.mapper;

import com.sd.Monitoring.Communication.dto.DeviceDTO;
import com.sd.Monitoring.Communication.entity.Device;
import org.springframework.stereotype.Component;

@Component
public class DeviceMapper {

    public DeviceDTO toDeviceDTO(Device device) {
        return DeviceDTO.builder()
                .idDevice(device.getIdDevice())
                .idUser(device.getIdUser())
                .consumption(device.getConsumption())
                .build();
    }

    public Device toDevice(DeviceDTO deviceDTO) {
        return Device.builder()
                .idDevice(deviceDTO.getIdDevice())
                .idUser(deviceDTO.getIdUser())
                .consumption(deviceDTO.getConsumption())
                .build();
    }
}
