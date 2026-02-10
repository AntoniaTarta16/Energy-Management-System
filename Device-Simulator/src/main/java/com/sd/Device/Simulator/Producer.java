package com.sd.Device.Simulator;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Producer {
    private final RabbitTemplate rabbitTemplate;

    @Value("${queue.name}")
    private String queueName;

    public void sendMessage(String jsonMessage) {
        rabbitTemplate.convertAndSend(queueName, jsonMessage);
        System.out.println("Message: " + jsonMessage);
    }
}
