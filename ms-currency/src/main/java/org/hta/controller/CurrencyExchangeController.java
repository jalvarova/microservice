package org.hta.controller;

import io.reactivex.Observable;
import io.reactivex.Single;
import org.hta.dto.CurrencyExchangeDto;
import org.hta.dto.CurrencyExchangeRsDto;
import org.hta.dto.CurrencyOperationDto;
import org.hta.dto.CurrencyTransactionDto;
import org.hta.service.ICurrencyExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("/ms-currency/api/v1")
public class CurrencyExchangeController {

    @Autowired
    private ICurrencyExchangeService currencyExchangeService;

    @PostMapping(value = "/currency-exchange/apply", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Single<CurrencyExchangeRsDto> getCurrencyExchange(@RequestBody @Valid CurrencyExchangeDto request) {
        return currencyExchangeService.applyExchangeRate(request);
    }

    @GetMapping(value = "/currency-exchange", produces = MediaType.APPLICATION_JSON_VALUE)
    public Single<List<CurrencyExchangeDto>> getAllCurrencyExchange() {
        return currencyExchangeService.getAllCurrencyExchange();
    }

    @GetMapping(
            value = "/currency-exchange/{documentNumber}/transaction",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Observable<CurrencyTransactionDto> getTransactionAll(
            @RequestHeader("Authorization") String authorization,
            @PathVariable("documentNumber") String documentNumber) {
        return currencyExchangeService.getAllCurrencyTransaction(authorization, documentNumber);
    }

    @GetMapping(
            value = "/currency-exchange/transaction/{operation}/customer/{documentNumber}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Single<CurrencyOperationDto> getCurrencyExchangeTransaction(
            @RequestHeader("Authorization") String authorization,
            @PathVariable("operation") String operation,
            @PathVariable("documentNumber") String documentNumber) {
        return currencyExchangeService.getCurrencyExchangeTransaction(operation, documentNumber, authorization);
    }

}
