package org.hta.strategy;

import org.hta.dto.BROKER;
import org.hta.factory.BrokerOperationFactory;

public interface BrokerStrategy {

    BrokerOperationFactory create(BROKER broker);

}
