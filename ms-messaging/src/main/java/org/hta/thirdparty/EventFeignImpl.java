package org.hta.thirdparty;


import lombok.extern.slf4j.Slf4j;
import org.hta.subcriptor.transport.CurrencyTransactionEventDto;
import org.hta.thirdparty.model.BROKER;
import org.hta.thirdparty.model.Metadata;
import org.hta.thirdparty.model.RequestEvent;
import org.hta.thirdparty.model.ResponseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static org.hta.util.ConvertUtil.jsonToString;

@Slf4j
@Service
public class EventFeignImpl {

    @Value("${event.app}")
    private String app;

    @Value("${event.origin}")
    private String origin;

    @Value("${event.destination}")
    private String destination;

    @Value("${event.domain}")
    private String domain;

    @Autowired
    private EventFeignClient eventFeignClient;

    public Mono<ResponseEvent> eventSend(String body) {
        RequestEvent requestEvent = RequestEvent
                .builder()
                .metadata(
                        Metadata
                                .builder()
                                .app(app)
                                .origin(origin)
                                .domain(domain)
                                .destination(destination)
                                .broker(BROKER.RABBITMQ)
                                .build()
                )
                .jsonPayload(body)
                .build();

        log.info("Request Event" + jsonToString(requestEvent));
        return Mono.just(eventFeignClient.callEventSendApi(requestEvent));
    }
}
