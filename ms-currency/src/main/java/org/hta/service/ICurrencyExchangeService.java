package org.hta.service;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import org.hta.dto.*;

import java.util.List;
import java.util.Map;

public interface ICurrencyExchangeService {

    Single<CurrencyExchangeRsDto> applyExchangeRate(CurrencyExchangeDto dto);

    Single<CurrencyExchangeDto> updateExchangeRate(CurrencyExchangeDto dto);

    Single<List<CurrencyExchangeDto>> getAllCurrencyExchange();

    Observable<CurrencyExchangeDto> saveExchangeRate(List<CurrencyExchangeDto> dtos);

    Observable<CurrencyTransactionDto> getAllCurrencyTransaction(String authorization, String username, String documentNumber);

    Maybe<Map<String, String>> saveCurrencyExchangeTransaction(CurrencyTransactionEventDto currencyTransactionEventDto);

    Single<CurrencyOperationDto> getCurrencyExchangeTransaction(String operation, String documentNumber, String authorization);

}
