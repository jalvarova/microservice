package org.hta.thirdparty;


import lombok.extern.slf4j.Slf4j;
import org.hta.config.HttpClientConfiguration;
import org.hta.subcriptor.transport.CurrencyTransactionEventDto;
import org.hta.thirdparty.model.BROKER;
import org.hta.thirdparty.model.Metadata;
import org.hta.thirdparty.model.RequestEvent;
import org.hta.thirdparty.model.ResponseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.retry.annotation.CircuitBreaker;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;
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
    private RetryTemplate retryTemplate;

    @Autowired
    private HttpClientConfiguration httpClient;


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
        return Mono.just(callEventSendApí("", requestEvent));
    }


    @CircuitBreaker(maxAttempts = 5, openTimeout = 15000L, resetTimeout = 30000L, include = {WebClientResponseException.class})
    private ResponseEvent callEventSendApí(String authorization, RequestEvent requestEvent) {
        return retryTemplate.execute(retryContext -> httpClient
                .eventWebClient()
                .post()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/send")
                                .build())
                .body(Mono.just(requestEvent), RequestEvent.class)
                .header(HttpHeaders.AUTHORIZATION, authorization)
                .retrieve()
                .bodyToMono(ResponseEvent.class)
                .block());
    }
}
