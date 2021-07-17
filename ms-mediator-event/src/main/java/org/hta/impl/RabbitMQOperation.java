package org.hta.impl;

import lombok.extern.slf4j.Slf4j;
import org.hta.dto.Metadata;
import org.hta.factory.BrokerOperationFactory;
import org.hta.strategy.BrokerComplements;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Slf4j
@Component("rabbitMQOperation")
public class RabbitMQOperation extends BrokerComplements implements BrokerOperationFactory {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public RabbitMQOperation create() {
        return new RabbitMQOperation();
    }

    @Override
    public void publish(Metadata metadata, String s) {
        log.info("Begin publishSendMessage " + subscriber(metadata) + " and routing key " + routingKey(metadata));
        rabbitTemplate.convertAndSend(topic(metadata), routingKey(metadata), s);
        log.info("End publishSendMessage");

    }

    @Override
    public String receive(Metadata metadata) {
        byte[] bytes = Objects.requireNonNull(rabbitTemplate.receive(subscriber(metadata))).getBody();
        return new String(bytes, StandardCharsets.UTF_8);
    }

    @Override
    public String sendAndReceive(Metadata metadata) {
        return null;
    }

}
