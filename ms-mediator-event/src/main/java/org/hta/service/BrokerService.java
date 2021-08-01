package org.hta.service;

import lombok.extern.slf4j.Slf4j;
import org.hta.aspect.TraceSpan;
import org.hta.dto.Metadata;
import org.hta.dto.RequestEvent;
import org.hta.dto.ResponseEvent;
import org.hta.dto.BROKER;
import org.hta.strategy.BrokerStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BrokerService implements IBrokerService {

    @Autowired
    private BrokerStrategy brokerStrategy;

    @Override
    @TraceSpan(key = "send")
    public ResponseEvent send(RequestEvent requestEvent) {
        Metadata metadata = requestEvent.getMetadata();
        BROKER broker = metadata.getBroker();
        brokerStrategy
                .create(broker)
                .publish(metadata, requestEvent.getJsonPayload());

        return ResponseEvent
                .builder()
                .message("Messanje correctamente enviado")
                .build();
    }

    @Override
    @TraceSpan(key = "receive")
    public ResponseEvent receive(RequestEvent requestEvent) {
        Metadata metadata = requestEvent.getMetadata();
        BROKER broker = metadata.getBroker();
        String payload = brokerStrategy
                .create(broker)
                .receive(metadata);

        return ResponseEvent
                .builder()
                .message("Messanje correctamente enviado")
                .responseData(payload)
                .build();

    }

    @Override
    public ResponseEvent sendReceive(RequestEvent requestEvent) {
        return null;
    }
}
