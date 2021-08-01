package org.hta.impl;

import lombok.extern.slf4j.Slf4j;
import org.hta.aspect.TraceSpan;
import org.hta.dto.Metadata;
import org.hta.factory.BrokerOperationFactory;
import org.hta.strategy.BrokerComplements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.SuccessCallback;

@Slf4j
@Component("kafkaOperation")
public class KafkaOperation extends BrokerComplements implements BrokerOperationFactory {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    @TraceSpan(key = "publishKafka")
    public void publish(Metadata metadata, String message) {
        log.info("Begin publishSendMessage");
        kafkaTemplate.send(topic(metadata), message)
                .addCallback(getSuccessCallback(message), getFailureCallback(message));
        log.info("End publishSendMessage");
    }

    private SuccessCallback<? super SendResult<String, String>> getSuccessCallback(String message) {
        return (result) -> log.info(message + " -> Successfully sent message to topic " + result);
    }

    private FailureCallback getFailureCallback(String message) {
        return (error) -> log.error(message + " -> Error occurred trying to publish to Google Pub sub " + error.getMessage(), error);
    }

    @Override
    public String receive(Metadata metadata) {
        return null;
    }

    @Override
    public String sendAndReceive(Metadata metadata) {
        return null;
    }
}
