package org.hta.factory;


import org.hta.dto.Metadata;

public interface BrokerOperationFactory {

    void publish(Metadata metadata, String message);

    String receive(Metadata metadata);

    String sendAndReceive(Metadata metadata);

}
