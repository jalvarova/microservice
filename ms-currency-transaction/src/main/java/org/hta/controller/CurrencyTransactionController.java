package org.hta.controller;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import org.hta.dto.CurrencyExchangeDto;
import org.hta.dto.CurrencyTransactionEventDto;
import org.hta.service.CurrencyTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("/ms-currency-transaction/api/v1")
public class CurrencyTransactionController {

    @Autowired
    private CurrencyTransactionService currencyExchangeService;


    @PutMapping(value = "/currency-exchange", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Single<CurrencyExchangeDto> updateCurrencyExchange(@RequestBody @Valid CurrencyExchangeDto request) {
        return currencyExchangeService.updateExchangeRate(request);
    }

    @PostMapping(value = "/currency-exchange", produces = MediaType.APPLICATION_JSON_VALUE)
    public Observable<CurrencyExchangeDto> saveCurrencyExchange(@RequestBody @Valid List<CurrencyExchangeDto> request) {
        return currencyExchangeService.saveExchangeRate(request);
    }

    @PutMapping(
            value = "/currency-exchange/transaction",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Maybe<?> saveCurrencyExchangeTransaction(@RequestBody @Valid CurrencyTransactionEventDto currencyTransactionDto) {
        return currencyExchangeService.saveCurrencyExchangeTransaction(currencyTransactionDto);
    }
}
