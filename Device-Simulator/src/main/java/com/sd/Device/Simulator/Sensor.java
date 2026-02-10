package com.sd.Device.Simulator;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Component
public class Sensor {
    @Autowired
    private Producer producer;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${device.id}")
    private String deviceId;

    @Value("${file.path}")
    private String filePath;

    private BufferedReader bufferedReader;

    @Scheduled(fixedRate = 10000)  // 10 seconds
    public void simulateData() {
        try {
            if (bufferedReader == null) {
                bufferedReader = new BufferedReader(new FileReader(filePath));
            }

            String line = bufferedReader.readLine();
            if (line != null) {
                double measurementValue = Double.parseDouble(line.trim());
                long timestamp = System.currentTimeMillis();

                Measurement measurement = new Measurement(timestamp, deviceId, measurementValue);
                String jsonMessage = objectMapper.writeValueAsString(measurement);

                producer.sendMessage(jsonMessage);
            }
            else {
                System.out.println("File has been fully read. Resetting the reader.");
                bufferedReader.close();
                bufferedReader = null;
            }
        } catch (IOException e) {
            System.err.println("Error while reading the file: " + e.getMessage());
        }
    }
}
