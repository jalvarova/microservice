package org.hta.service;

import io.reactivex.Observable;
import io.reactivex.Single;
import org.hta.dto.CurrencyExchangeDto;
import org.hta.dto.CurrencyExchangeRsDto;
import org.hta.dto.CurrencyOperationDto;
import org.hta.dto.CurrencyTransactionDto;

import java.util.List;

public interface ICurrencyExchangeService {

    Single<CurrencyExchangeRsDto> applyExchangeRate(CurrencyExchangeDto dto);

    Single<List<CurrencyExchangeDto>> getAllCurrencyExchange();

    Observable<CurrencyTransactionDto> getAllCurrencyTransaction(String authorization, String documentNumber);

    Single<CurrencyOperationDto> getCurrencyExchangeTransaction(String operation, String documentNumber, String authorization);

}
