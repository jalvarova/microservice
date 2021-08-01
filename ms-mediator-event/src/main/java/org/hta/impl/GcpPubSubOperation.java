package org.hta.impl;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import lombok.extern.slf4j.Slf4j;
import org.hta.aspect.TraceSpan;
import org.hta.dto.Metadata;
import org.hta.factory.BrokerOperationFactory;
import org.hta.strategy.BrokerComplements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.SuccessCallback;

import java.util.List;

@Slf4j
@Component("gcpPubSubOperation")
public class GcpPubSubOperation extends BrokerComplements implements BrokerOperationFactory {

    @Autowired
    private PubSubTemplate pubSubTemplate;

    public GcpPubSubOperation create() {
        return new GcpPubSubOperation();
    }

    @Override
    @TraceSpan(key = "publishGCP")
    public void publish(Metadata metadata, String message) {
        log.info("Begin publishSendMessage");
        pubSubTemplate
                .publish(topic(metadata), message)
                .addCallback(getSuccessCallback(message), getFailureCallback(message));
        log.info("End publishSendMessage");
    }


    private SuccessCallback<String> getSuccessCallback(String message) {
        return (result) -> log.info(message + " -> Successfully sent message to topic " + result);
    }

    private FailureCallback getFailureCallback(String message) {
        return (error) -> log.error(message + " -> Error occurred trying to publish to Google Pub sub " + error.getMessage(), error);
    }

    @Override
    @TraceSpan(key = "receiveGCP")
    public String receive(Metadata metadata) {
        List<PubsubMessage> pubsubMessages = pubSubTemplate.pullAndAck(subscriber(metadata), 1, Boolean.TRUE);
        return pubsubMessages
                .stream()
                .map(PubsubMessage::getData)
                .map(ByteString::toStringUtf8)
                .findFirst()
                .orElse(null);
    }

    @Override
    public String sendAndReceive(Metadata metadata) {
        return null;
    }

}
