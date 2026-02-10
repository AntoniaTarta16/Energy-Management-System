package com.sd.Device.Management.service.event;

import com.sd.Device.Management.dto.DeviceDTO;
import com.sd.Device.Management.dto.DeviceEventDTO;
import com.sd.Device.Management.producer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private Producer producer;

    DeviceEventDTO createDeviceEvent(DeviceDTO deviceDTO, String eventType) {
        return new DeviceEventDTO(
                eventType,
                deviceDTO.getIdDevice().toString(),
                deviceDTO.getIdUser() != null ? deviceDTO.getIdUser().toString() : null,
                deviceDTO.getMaxHourlyEnergyConsumption()
        );
    }

    @Override
    public void createDevice(DeviceDTO deviceDTO) {
        System.out.println("Device created: " + deviceDTO.getIdDevice());
        DeviceEventDTO event = createDeviceEvent(deviceDTO, "DEVICE_CREATED");
        producer.sendDeviceEvent(event);
    }

    @Override
    public void updateDevice(DeviceDTO deviceDTO) {
        System.out.println("Device updated: " + deviceDTO.getIdDevice());
        DeviceEventDTO event = createDeviceEvent(deviceDTO, "DEVICE_UPDATED");
        producer.sendDeviceEvent(event);
    }

    @Override
    public void deleteDevice(UUID idDevice) {
        System.out.println("Device deleted: " + idDevice);
        DeviceEventDTO event = new DeviceEventDTO("DEVICE_DELETED", idDevice.toString(), null, 0.0);
        producer.sendDeviceEvent(event);
    }
}
