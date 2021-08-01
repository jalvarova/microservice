package org.hta.service;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import org.hta.dto.CurrencyExchangeDto;
import org.hta.dto.CurrencyTransactionEventDto;

import java.util.List;
import java.util.Map;

public interface ICurrencyTransactionService {


    Single<CurrencyExchangeDto> updateExchangeRate(CurrencyExchangeDto dto);


    Observable<CurrencyExchangeDto> saveExchangeRate(List<CurrencyExchangeDto> dtos);


    Maybe<Map<String, String>> saveCurrencyExchangeTransaction(CurrencyTransactionEventDto currencyTransactionEventDto, String authorization);


}
