package org.hta.service.impl;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;
import org.hta.domain.CurrencyExchangeRepository;
import org.hta.domain.CurrencyTransactionRepository;
import org.hta.domain.entity.CurrencyExchange;
import org.hta.dto.*;
import org.hta.service.ICurrencyCodeNamesService;
import org.hta.service.ICurrencyExchangeService;
import org.hta.thirtyparty.CustomerFeignImpl;
import org.hta.thirtyparty.EventFeignImpl;
import org.hta.thirtyparty.IdentityFeignImpl;
import org.hta.util.CurrencyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.hta.mappers.CurrencyMapper.*;

@Service
@Slf4j
public class CurrencyExchangeService implements ICurrencyExchangeService {

    @Autowired
    private CurrencyExchangeRepository currencyExchangeRepository;

    @Autowired
    private ICurrencyCodeNamesService codeNamesService;

    @Autowired
    private IdentityFeignImpl identityFeign;

    @Autowired
    private CustomerFeignImpl customerFeign;

    @Autowired
    private CurrencyTransactionRepository transactionRepository;

    @Autowired
    private EventFeignImpl apiEvent;

    @Override
    public Single<CurrencyExchangeRsDto> applyExchangeRate(CurrencyExchangeDto dto) {
        String currencyOrigin = dto.getCurrencyOrigin().name();
        String currencyDestination = dto.getCurrencyDestination().name();

        return Single.fromCallable(() -> currencyExchangeRepository.findByApplyCurrency(currencyOrigin, currencyDestination))
                .subscribeOn(Schedulers.io())
                .onErrorReturnItem(CurrencyExchange.instanceEmpty())
                .map(CurrencyUtil::validateNullCurrency)
                .map(x -> toApiApply.apply(x, dto.getAmount()));
    }

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
    public Single<List<CurrencyExchangeDto>> getAllCurrencyExchange() {
        return Single.just(currencyExchangeRepository.findAll())
                .subscribeOn(Schedulers.io())
                .map(CurrencyUtil::validateNullCollection)
                .flatMapObservable(Observable::fromIterable)
                .map(currencyExchange -> toApiList.apply(currencyExchange, codeNamesService.findAll()))
                .toList();
    }

    @Override
    public Observable<CurrencyExchangeDto> saveExchangeRate(List<CurrencyExchangeDto> dtos) {

        return Observable
                .fromIterable(dtos)
                .map(this::saveAll)
                .map(toApiFunc);
    }

    @Override
    public Observable<CurrencyTransactionDto> getAllCurrencyTransaction(
            String authorization,
            String username,
            String documentNumber) {

        return Observable.zip(
                identityFeign.identityApí(authorization, username).toObservable(),
                customerFeign.customerApí(documentNumber).toObservable(),
                Observable.fromIterable(transactionRepository.findByAllDocumentNumber(documentNumber)),
                Single.just(codeNamesService.findAll()).toObservable(),
                toApiTransaction)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Maybe<Map<String, String>> saveCurrencyExchangeTransaction(CurrencyTransactionEventDto currencyTransactionEventDto) {
        String numberOperation = UUID.randomUUID().toString();
        currencyTransactionEventDto.setNumberOperation(numberOperation);
        return apiEvent
                .eventSend(currencyTransactionEventDto)
                .map(responseEvent -> Collections.singletonMap("numberOperation", numberOperation));
    }

    @Override
    public Single<CurrencyOperationDto> getCurrencyExchangeTransaction(String operation, String documentNumber, String authorization) {
        return Single.zip(Single.just(transactionRepository.findByOperationNumber(operation)),
                identityFeign.identityApí(authorization, "gaguinaga"),
                customerFeign.customerApí(documentNumber),
                Single.just(codeNamesService.findAll()),
                toApiOperation);
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

