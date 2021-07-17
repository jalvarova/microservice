package org.hta.service;

import org.hta.dto.RequestEvent;
import org.hta.dto.ResponseEvent;

public interface IBrokerService {

    ResponseEvent send(RequestEvent requestEvent);

    ResponseEvent receive(RequestEvent requestEvent);

    ResponseEvent sendReceive(RequestEvent requestEvent);
}
