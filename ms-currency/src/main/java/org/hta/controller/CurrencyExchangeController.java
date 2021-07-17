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

    @GetMapping(value = "/currency-exchange", produces = MediaType.APPLICATION_JSON_VALUE)
    public Single<List<CurrencyExchangeDto>> getAllCurrencyExchange() {
        return currencyExchangeService.getAllCurrencyExchange();
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

    @GetMapping(
            value = "/currency-exchange/transaction/{operation}/customer/{documentNumber}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Single<CurrencyOperationDto> getCurrencyExchangeTransaction(
            HttpServletRequest request,
            @PathVariable("operation") String operation,
            @PathVariable("documentNumber") String documentNumber) {
        String authorization = request.getHeader("Authorization");
        return currencyExchangeService.getCurrencyExchangeTransaction(operation, documentNumber, authorization);
    }

}
