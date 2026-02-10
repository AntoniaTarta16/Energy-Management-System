package com.sd.Device.Simulator;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Value("${queue.name}")
    private String queueName;

    @Bean
    public Queue dataQueue() {
        //return new Queue(queueName, true, false, false);
        return new Queue(queueName, false);
    }
}
