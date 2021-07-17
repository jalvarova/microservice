package org.hta.mappers;

import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function4;
import org.hta.domain.entity.CurrencyCodeNames;
import org.hta.domain.entity.CurrencyExchange;
import org.hta.domain.entity.CurrencyTransaction;
import org.hta.dto.CurrencyExchangeDto;
import org.hta.dto.CurrencyExchangeRsDto;
import org.hta.dto.CurrencyOperationDto;
import org.hta.dto.CurrencyTransactionDto;
import org.hta.dto.enums.CurrencyCode;
import org.hta.thirtyparty.model.ClientDto;
import org.hta.thirtyparty.model.UserDto;
import org.hta.util.CurrencyUtil;

import java.math.BigDecimal;
import java.util.List;


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

    BiFunction<CurrencyExchange, List<CurrencyCodeNames>, CurrencyExchangeDto> toApiList = (CurrencyExchange entity, List<CurrencyCodeNames> entities) ->
            CurrencyExchangeDto
                    .builder()
                    .currencyOrigin(CurrencyCode.valueOf(entity.getCurrencyExchangeOrigin()))
                    .nameOrigin(CurrencyUtil.findCurrencyCode(entities, entity.getCurrencyExchangeOrigin()))
                    .currencyDestination(CurrencyCode.valueOf(entity.getCurrencyExchangeDestination()))
                    .nameDestination(CurrencyUtil.findCurrencyCode(entities, entity.getCurrencyExchangeDestination()))
                    .amount(entity.getAmountExchangeRate())
                    .build();

    Function<CurrencyExchange, CurrencyExchangeDto> toApiFunc = (CurrencyExchange entity) ->
            CurrencyExchangeDto
                    .builder()
                    .currencyOrigin(CurrencyCode.valueOf(entity.getCurrencyExchangeOrigin()))
                    .currencyDestination(CurrencyCode.valueOf(entity.getCurrencyExchangeDestination()))
                    .amount(entity.getAmountExchangeRate())
                    .build();

    BiFunction<CurrencyExchange, BigDecimal, CurrencyExchangeRsDto> toApiApply = (CurrencyExchange entity, BigDecimal amount) ->
            CurrencyExchangeRsDto
                    .builder()
                    .amount(amount)
                    .currencyDestination(CurrencyCode.valueOf(entity.getCurrencyExchangeDestination()))
                    .currencyOrigin(CurrencyCode.valueOf(entity.getCurrencyExchangeOrigin()))
                    .exchangeRate(entity.getAmountExchangeRate())
                    .exchangeRateAmount(entity.getAmountExchangeRate().multiply(amount).setScale(5, BigDecimal.ROUND_HALF_UP))
                    .build();


    BiFunction<CurrencyExchange, BigDecimal, CurrencyExchange> toUpdateAmount = (CurrencyExchange entity, BigDecimal amount) -> {
        entity.setAmountExchangeRate(amount);
        return entity;
    };

    Function4<UserDto, ClientDto, CurrencyTransaction, List<CurrencyCodeNames>, CurrencyTransactionDto> toApiTransaction =
            (UserDto userDto, ClientDto clientDto, CurrencyTransaction trans, List<CurrencyCodeNames> entities) ->
                    CurrencyTransactionDto
                            .builder()
                            .currencyOrigin(CurrencyCode.valueOf(trans.getCurrencyOrigin()))
                            .nameOrigin(CurrencyUtil.findCurrencyCode(entities, trans.getCurrencyOrigin()))
                            .nameDestination(CurrencyUtil.findCurrencyCode(entities, trans.getCurrencyDestination()))
                            .currencyDestination(CurrencyCode.valueOf(trans.getCurrencyDestination()))
                            .amount(trans.getAmountExchangeRate())
                            .exchangeRateAmount(trans.getAmountExchangeRate())
                            .exchangeRate(trans.getAmountRate())
                            .amount(trans.getAmount())
                            .user(userDto)
                            .client(clientDto)
                            .build();

    Function4<CurrencyTransaction, UserDto, ClientDto, List<CurrencyCodeNames>, CurrencyOperationDto> toApiOperation =
            (CurrencyTransaction entity,UserDto userDto, ClientDto clientDto, List<CurrencyCodeNames> entities) ->
                    CurrencyOperationDto
                            .builder()
                            .currencyOrigin(CurrencyCode.valueOf(entity.getCurrencyOrigin()))
                            .nameOrigin(CurrencyUtil.findCurrencyCode(entities, entity.getCurrencyOrigin()))
                            .amount(entity.getAmountExchangeRate())
                            .exchangeRateAmount(entity.getAmountExchangeRate())
                            .accountNumberDestination(entity.getAccountDestination())
                            .accountNumberOrigin(entity.getAccountOrigin())
                            .user(userDto)
                            .client(clientDto)
                            .build();


}
