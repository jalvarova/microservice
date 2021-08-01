package org.hta.thirtyparty;

import io.reactivex.Maybe;
import lombok.extern.slf4j.Slf4j;
import org.hta.aspect.TraceSpan;
import org.hta.configuration.HttpClientConfiguration;
import org.hta.dto.CurrencyTransactionEventDto;
import org.hta.thirtyparty.model.BROKER;
import org.hta.thirtyparty.model.Metadata;
import org.hta.thirtyparty.model.RequestEvent;
import org.hta.thirtyparty.model.ResponseEvent;
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
public class EventApi {

    @Value("${event.app}")
    private String app;

    @Value("${event.origin}")
    private String origin;

    @Value("${event.destination}")
    private String destination;

    @Value("${event.domain}")
    private String domain;

    @Autowired
    private HttpClientConfiguration httpClient;

    @Autowired
    private RetryTemplate retryTemplate;

    @TraceSpan(key = "callEventSendApí")
    public Maybe<ResponseEvent> eventSend(CurrencyTransactionEventDto body, String authorization) {
        String jsonPayload = jsonToString(body);
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
                .jsonPayload(jsonPayload)
                .build();

        log.info("Request Event" + jsonPayload);
        return Maybe.just(callEventSendApí(authorization, requestEvent));
    }

    @CircuitBreaker(maxAttempts = 5, openTimeout = 15000L, resetTimeout = 30000L, include = {WebClientResponseException.class})
    private ResponseEvent callEventSendApí(String authorization, RequestEvent requestEvent) {
        return retryTemplate.execute(retryContext ->
                httpClient
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

    @CircuitBreaker(maxAttempts = 5, openTimeout = 15000L, resetTimeout = 30000L, include = {WebClientResponseException.class})
    private ResponseEvent callEventReceiveApí(String authorization, RequestEvent requestEvent) {
        return retryTemplate.execute(retryContext ->
                httpClient
                        .eventWebClient()
                        .post()
                        .uri(uriBuilder ->
                                uriBuilder
                                        .path("/receive")
                                        .build())
                        .body(Mono.just(requestEvent), RequestEvent.class)
                        .header(HttpHeaders.AUTHORIZATION, authorization)
                        .retrieve()
                        .bodyToMono(ResponseEvent.class)
                        .block());
    }

}
