package org.hta.thirtyparty;

import io.reactivex.Single;
import lombok.extern.slf4j.Slf4j;
import org.hta.thirtyparty.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class IdentityFeignImpl {

    @Autowired
    private IdentityFeignClient identityFeignClient;

    public Single<UserDto> identityApí(String authorization, String documentNumber) {
        return Single.just(identityFeignClient.callIdentityApi(authorization, documentNumber));
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
