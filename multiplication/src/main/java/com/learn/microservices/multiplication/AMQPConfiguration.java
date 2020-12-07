package com.learn.microservices.multiplication;

import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AMQPConfiguration {

    @Bean
    public TopicExchange buildAttemptsTopicExchange(@Value("${amqp.exchange.attempts}") String exchangeName) {
        return ExchangeBuilder
            .topicExchange(exchangeName)
            .durable(true)
            .build();
    }

    @Bean
    public Jackson2JsonMessageConverter defaultSerializer() {
        return new Jackson2JsonMessageConverter();
    }
}
