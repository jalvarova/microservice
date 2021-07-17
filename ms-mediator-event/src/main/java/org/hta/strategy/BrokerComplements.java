package org.hta.strategy;

import org.hta.dto.Metadata;

import java.util.StringJoiner;

public abstract class BrokerComplements {

    private static final String DELIMITER = ".";
    private static final String SUBSCRIBER = "SUBSCRIBER";

    public String topic(Metadata metadata) {
        StringJoiner joiner = new StringJoiner(DELIMITER);
        joiner.add(metadata.getApp());
        joiner.add(metadata.getDomain());
        joiner.add(metadata.getOrigin());
        return joiner.toString().toLowerCase();
    }

    public String routingKey(Metadata metadata) {
        StringJoiner joiner = new StringJoiner(DELIMITER);
        joiner.add(metadata.getApp());
        joiner.add(metadata.getDomain());
        joiner.add(metadata.getOrigin());
        joiner.add(metadata.getDestination());
        return joiner.toString().toLowerCase();
    }

    public String subscriber(Metadata metadata) {
        StringJoiner joiner = new StringJoiner(DELIMITER);
        joiner.add(metadata.getApp());
        joiner.add(metadata.getDomain());
        joiner.add(metadata.getOrigin());
        joiner.add(metadata.getDestination());
        joiner.add(SUBSCRIBER);
        return joiner.toString().toLowerCase();
    }
}
