package com.sd.Monitoring.Communication.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Value("${queue.device.name}")
    private String queueName;

    @Value("${exchange.name}")
    private String exchangeName;

    @Value("${queue.name}")
    private String dataQueueName;

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

    @Bean
    public Queue dataQueue() {
        //return new Queue(queueName, true, false, false);
        return new Queue(dataQueueName, false);
    }

    @Bean
    public Queue deviceQueue() {
        return new Queue(queueName, false);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(exchangeName, true, false);
    }

    @Bean
    public Binding deviceQueueBinding(Queue deviceQueue, TopicExchange topicExchange) {
        return BindingBuilder
                .bind(deviceQueue)
                .to(topicExchange)
                .with("device.*");
    }

}
