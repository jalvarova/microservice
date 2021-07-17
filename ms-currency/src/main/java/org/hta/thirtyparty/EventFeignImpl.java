package org.hta.thirtyparty;

import io.reactivex.Maybe;
import io.reactivex.Single;
import lombok.extern.slf4j.Slf4j;
import org.hta.dto.CurrencyExchangeRsDto;
import org.hta.dto.CurrencyTransactionEventDto;
import org.hta.thirtyparty.model.event.BROKER;
import org.hta.thirtyparty.model.event.Metadata;
import org.hta.thirtyparty.model.event.RequestEvent;
import org.hta.thirtyparty.model.event.ResponseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

    public Maybe<ResponseEvent> eventSend(CurrencyTransactionEventDto body) {
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

        log.info("Request Event" + jsonToString(requestEvent));
        return Maybe.just(eventFeignClient.callEventSendApi("", requestEvent));
    }

//    @Autowired
//    private RetryTemplate retryTemplate;

//    @CircuitBreaker(maxAttempts = 5, openTimeout = 15000L, resetTimeout = 30000L, include = {FeignException.class})
//    public StockModel inventoryApí(String idTransaction, StockModel stockModelRequest) {
//        return retryTemplate.execute(retryContext -> identityFeignClient.callInventoryApi(idTransaction, stockModelRequest));
//    }

//    //@Recover()
//    public StockModel cacheFallbackResponse(StockModel stockModelRequest) {
//        return StockModel
//                .builder()
//                .entityCode("OE-122")
//                .skuCode("34015")
//                .build();
//    }
}
