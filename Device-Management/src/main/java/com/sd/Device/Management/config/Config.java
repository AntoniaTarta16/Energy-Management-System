package com.sd.Device.Management.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Value("${queue.name}")
    private String queueName;

    @Value("${exchange.name}")
    private String exchangeName;

    @Bean
    public Queue allEventsQueue() {
        return new Queue(queueName, true);
    }

    @Bean
    public TopicExchange topicExchange()  {
        return new TopicExchange(exchangeName, true, false);
    }

    @Bean
    public Binding bindingAllEventsQueue(Queue allEventsQueue, TopicExchange topicExchange) {
        return BindingBuilder
                .bind(allEventsQueue)
                .to(topicExchange)
                .with("device.*");
    }
}
