package com.sd.Monitoring.Communication.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sd.Monitoring.Communication.dto.DeviceDTO;
import com.sd.Monitoring.Communication.service.device.DeviceService;
import com.sd.Monitoring.Communication.service.measurement.MeasurementService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceConsumer {
    @Autowired
    private DeviceService deviceService;

    @Autowired
    private MeasurementService measurementService;

    private final ObjectMapper objectMapper;

    public DeviceConsumer() {
        this.objectMapper = new ObjectMapper();
    }

    @RabbitListener(queues = "${queue.device.name}")
    public void handleDeviceEvent(String message) {
        
        DeviceDTO deviceDTO = null;
        try {
            deviceDTO = objectMapper.readValue(message, DeviceDTO.class);
        } catch (Exception e) {
            System.err.println("Error while processing the message: " + e.getMessage());
        }
        
        switch (deviceDTO.getEventType()) {
            case "DEVICE_CREATED":
                deviceService.createDevice(deviceDTO);
                break;
            case "DEVICE_UPDATED":
                deviceService.updateDevice(deviceDTO);
                break;
            case "DEVICE_DELETED":
                deviceService.deleteDevice(deviceDTO.getIdDevice());
                measurementService.deleteByDeviceId(deviceDTO.getIdDevice());
                break;
            default:
                System.out.println("Unknown event type: " + deviceDTO.getEventType());
        }
    }

}
