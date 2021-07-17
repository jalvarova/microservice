package org.hta.controller;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import org.hta.dto.*;
import org.hta.service.ICurrencyExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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


    @PutMapping(value = "/currency-exchange", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Single<CurrencyExchangeDto> updateCurrencyExchange(@RequestBody @Valid CurrencyExchangeDto request) {
        return currencyExchangeService.updateExchangeRate(request);
    }

    @GetMapping(value = "/currency-exchange", produces = MediaType.APPLICATION_JSON_VALUE)
    public Single<List<CurrencyExchangeDto>> getAllCurrencyExchange() {
        return currencyExchangeService.getAllCurrencyExchange();
    }


    @PostMapping(value = "/currency-exchange", produces = MediaType.APPLICATION_JSON_VALUE)
    public Observable<CurrencyExchangeDto> saveCurrencyExchange(@RequestBody @Valid List<CurrencyExchangeDto> request) {
        return currencyExchangeService.saveExchangeRate(request);
    }

    @GetMapping(
            value = "/currency-exchange/{documentNumber}/transaction",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Observable<CurrencyTransactionDto> getTransactionAll(
            HttpServletRequest request,
            @PathVariable("documentNumber") String documentNumber) {
        String authorization = request.getHeader("Authorization");
        String username = request.getHeader("username");
        return currencyExchangeService.getAllCurrencyTransaction(authorization, username, documentNumber);
    }


    @PutMapping(
            value = "/currency-exchange/transaction",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Maybe<?> saveCurrencyExchangeTransaction(@RequestBody @Valid CurrencyTransactionEventDto currencyTransactionDto) {
        return currencyExchangeService.saveCurrencyExchangeTransaction(currencyTransactionDto);
    }

    @GetMapping(
            value = "/currency-exchange/transaction/{operation}/customer/{documentNumber}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Single<CurrencyOperationDto> getCurrencyExchangeTransaction(
            HttpServletRequest request,
            @PathVariable("operation") String operation,
            @PathVariable("documentNumber") String documentNumber) {
        String authorization = request.getHeader("Authorization");
        return currencyExchangeService.getCurrencyExchangeTransaction(operation, documentNumber,authorization);
    }

}
