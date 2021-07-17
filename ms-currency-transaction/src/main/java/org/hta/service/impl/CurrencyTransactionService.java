package org.hta.service.impl;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;
import org.hta.domain.CurrencyExchangeRepository;
import org.hta.domain.entity.CurrencyExchange;
import org.hta.dto.*;
import org.hta.thirtyparty.EventFeignImpl;
import org.hta.util.CurrencyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.hta.mappers.CurrencyMapper.*;

@Service
@Slf4j
public class CurrencyTransactionService implements org.hta.service.CurrencyTransactionService {

    @Autowired
    private CurrencyExchangeRepository currencyExchangeRepository;

    @Autowired
    private EventFeignImpl apiEvent;


    @Override
    public Single<CurrencyExchangeDto> updateExchangeRate(CurrencyExchangeDto dto) {
        String currencyOrigin = dto.getCurrencyOrigin().name();
        String currencyDestination = dto.getCurrencyDestination().name();

        return Single.fromCallable(() -> currencyExchangeRepository.findByApplyCurrency(currencyOrigin, currencyDestination))
                .subscribeOn(Schedulers.io())
                .onErrorReturnItem(CurrencyExchange.instanceEmpty())
                .map(CurrencyUtil::validateNullCurrency)
                .map(x -> toUpdateAmount.apply(x, dto.getAmount()))
                .map(currencyExchangeRepository::save)
                .map(toApi);
    }


    @Override
    public Observable<CurrencyExchangeDto> saveExchangeRate(List<CurrencyExchangeDto> dtos) {

        return Observable
                .fromIterable(dtos)
                .map(this::saveAll)
                .map(toApiFunc);
    }

    @Override
    public Maybe<Map<String, String>> saveCurrencyExchangeTransaction(CurrencyTransactionEventDto currencyTransactionEventDto) {
        String numberOperation = UUID.randomUUID().toString();
        currencyTransactionEventDto.setNumberOperation(numberOperation);
        return apiEvent
                .eventSend(currencyTransactionEventDto)
                .map(responseEvent -> Collections.singletonMap("numberOperation", numberOperation));
    }


    private CurrencyExchange saveAll(CurrencyExchangeDto dtos) {

        String currencyOrigin = dtos.getCurrencyOrigin().name();
        String currencyDestination = dtos.getCurrencyDestination().name();

        CurrencyExchange currencyExchange = new CurrencyExchange();

        CurrencyExchange foundCurrencyExchange = currencyExchangeRepository.findByApplyCurrency(currencyOrigin, currencyDestination);
        if (Objects.nonNull(foundCurrencyExchange)) {
            foundCurrencyExchange.setAmountExchangeRate(dtos.getAmount());
            currencyExchange = currencyExchangeRepository.save(foundCurrencyExchange);
        } else {
            currencyExchange.setCurrencyExchangeOrigin(currencyOrigin);
            currencyExchange.setCurrencyExchangeDestination(currencyDestination);
            currencyExchange.setAmountExchangeRate(dtos.getAmount());
            currencyExchange = currencyExchangeRepository.save(currencyExchange);
        }
        return currencyExchange;
    }

}

