package org.hta.service.impl;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;
import org.hta.domain.CurrencyExchangeRepository;
import org.hta.domain.CurrencyTransactionRepository;
import org.hta.domain.entity.CurrencyExchange;
import org.hta.dto.CurrencyExchangeDto;
import org.hta.dto.CurrencyExchangeRsDto;
import org.hta.dto.CurrencyOperationDto;
import org.hta.dto.CurrencyTransactionDto;
import org.hta.service.ICurrencyCodeNamesService;
import org.hta.service.ICurrencyExchangeService;
import org.hta.thirtyparty.CustomerFeignImpl;
import org.hta.thirtyparty.IdentityFeignImpl;
import org.hta.util.CurrencyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public Single<List<CurrencyExchangeDto>> getAllCurrencyExchange() {
        return Single.just(currencyExchangeRepository.findAll())
                .subscribeOn(Schedulers.io())
                .map(CurrencyUtil::validateNullCollection)
                .flatMapObservable(Observable::fromIterable)
                .map(currencyExchange -> toApiList.apply(currencyExchange, codeNamesService.findAll()))
                .toList();
    }

    @Override
    public Observable<CurrencyTransactionDto> getAllCurrencyTransaction(
            String authorization,
            String documentNumber) {

        return Observable.zip(
                identityFeign.identityApí(authorization, documentNumber).toObservable(),
                customerFeign.customerApí(documentNumber).toObservable(),
                Observable.fromIterable(transactionRepository.findByAllDocumentNumber(documentNumber)),
                Single.just(codeNamesService.findAll()).toObservable(),
                toApiTransaction)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<CurrencyOperationDto> getCurrencyExchangeTransaction(String operation, String documentNumber, String authorization) {
        return Single.zip(Single.just(transactionRepository.findByOperationNumber(operation)),
                identityFeign.identityApí(authorization, documentNumber),
                customerFeign.customerApí(documentNumber),
                Single.just(codeNamesService.findAll()),
                toApiOperation);
    }


}

