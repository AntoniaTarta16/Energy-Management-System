package com.sd.Device.Management.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sd.Device.Management.dto.DeviceEventDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Producer {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${exchange.name}")
    private String exchangeName;

    public void sendDeviceEvent(DeviceEventDTO event) {

        String routingKey = switch (event.getEventType()) {
            case "DEVICE_UPDATED" -> "device.updated";
            case "DEVICE_DELETED" -> "device.deleted";
            case "DEVICE_CREATED" -> "device.created";
            default -> "device.unknown";
        };

        try {
            String eventJson = objectMapper.writeValueAsString(event);
            rabbitTemplate.convertAndSend(exchangeName, routingKey, eventJson);
            System.out.println("Sent event: " + eventJson);
        } catch (Exception e) {
            System.err.println("Failed to send event: " + e.getMessage());
        }
    }
}
