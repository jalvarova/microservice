package org.hta.thirtyparty;

import io.reactivex.Single;
import lombok.extern.slf4j.Slf4j;
import org.hta.aspect.TraceSpan;
import org.hta.configuration.HttpClientConfiguration;
import org.hta.thirtyparty.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.retry.annotation.CircuitBreaker;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Slf4j
@Service
public class IdentityApi {

    @Autowired
    private HttpClientConfiguration httpClient;

    @Autowired
    private RetryTemplate retryTemplate;

    @TraceSpan(key = "callIdentityApi")
    public Single<UserDto> identityApí(String authorization, String documentNumber) {
        return Single.fromCallable(() -> callIdentityApí(authorization, documentNumber));
    }

    @CircuitBreaker(maxAttempts = 5, openTimeout = 15000L, resetTimeout = 30000L, include = {WebClientResponseException.class})
    private UserDto callIdentityApí(String authorization, String documentNumber) {
        return retryTemplate.execute(retryContext -> httpClient
                .identityWebClient()
                .get()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/users/" + documentNumber + "/customer")
                                .build())
                .header(HttpHeaders.AUTHORIZATION, authorization)
                .retrieve()
                .bodyToMono(UserDto.class)
                .block());
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
