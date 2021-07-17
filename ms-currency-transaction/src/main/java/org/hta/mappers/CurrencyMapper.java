package org.hta.mappers;

import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import org.hta.domain.entity.CurrencyExchange;
import org.hta.dto.CurrencyExchangeDto;
import org.hta.dto.enums.CurrencyCode;

import java.math.BigDecimal;


@FunctionalInterface
public interface CurrencyMapper {

    void demo();

    Function<CurrencyExchange, CurrencyExchangeDto> toApi = (CurrencyExchange entity) ->
            CurrencyExchangeDto
                    .builder()
                    .currencyOrigin(CurrencyCode.valueOf(entity.getCurrencyExchangeOrigin()))
                    .currencyDestination(CurrencyCode.valueOf(entity.getCurrencyExchangeDestination()))
                    .amount(entity.getAmountExchangeRate())
                    .build();

    Function<CurrencyExchange, CurrencyExchangeDto> toApiFunc = (CurrencyExchange entity) ->
            CurrencyExchangeDto
                    .builder()
                    .currencyOrigin(CurrencyCode.valueOf(entity.getCurrencyExchangeOrigin()))
                    .currencyDestination(CurrencyCode.valueOf(entity.getCurrencyExchangeDestination()))
                    .amount(entity.getAmountExchangeRate())
                    .build();


    BiFunction<CurrencyExchange, BigDecimal, CurrencyExchange> toUpdateAmount = (CurrencyExchange entity, BigDecimal amount) -> {
        entity.setAmountExchangeRate(amount);
        return entity;
    };


}
