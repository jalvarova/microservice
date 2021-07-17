package org.hta.strategy;


import lombok.extern.slf4j.Slf4j;
import org.hta.dto.BROKER;
import org.hta.factory.BrokerOperationFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class BrokerStrategyImpl implements BrokerStrategy {

    private static final Map<BROKER, BrokerOperationFactory> mapStrategy = new HashMap<>(5);

    @Autowired
    @Qualifier(value = "gcpPubSubOperation")
    private BrokerOperationFactory gcpPubSubOperation;

    @Autowired
    @Qualifier(value = "rabbitMQOperation")
    private BrokerOperationFactory rabbitMQOperation;

    @Autowired
    @Qualifier(value = "kafkaOperation")
    private BrokerOperationFactory kafkaOperation;

    @PostConstruct
    public void init() {
        mapStrategy.put(BROKER.PUBSUB, gcpPubSubOperation);
        mapStrategy.put(BROKER.RABBITMQ, rabbitMQOperation);
        mapStrategy.put(BROKER.KAFKA, kafkaOperation);

    }

    @Override
    public BrokerOperationFactory create(BROKER broker) {
        BrokerOperationFactory companyInventoryStrategy = mapStrategy.get(broker);
        Assert.notNull(companyInventoryStrategy, String.format("Not exists strategy for companyCode : %s", broker.name()));
        return companyInventoryStrategy;
    }
}
