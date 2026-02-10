package com.sd.Monitoring.Communication.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sd.Monitoring.Communication.dto.MeasurementDTO;
import com.sd.Monitoring.Communication.service.measurement.MeasurementService;
import com.sd.Monitoring.Communication.service.notification.NotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MeasurementConsumer {

    @Autowired
    private MeasurementService measurementService;

    @Autowired
    private NotificationService notificationService;

    private final ObjectMapper objectMapper;

    public MeasurementConsumer() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @RabbitListener(queues = "${queue.name}")
    public void receiveMessage(String message) {
        System.out.println("Received message: " + message);

        try {
            MeasurementDTO measurementDTO = objectMapper.readValue(message, MeasurementDTO.class);
            MeasurementDTO measurement = measurementService.saveMeasurement(measurementDTO);
            if(measurementService.isConsumptionExceeded(measurement)) {
                notificationService.notifyUser(measurement.getDeviceId());
            }
        } catch (Exception e) {
            System.err.println("Error while processing the message: " + e.getMessage());
        }
    }

}
