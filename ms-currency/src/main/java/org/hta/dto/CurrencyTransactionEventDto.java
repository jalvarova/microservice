package org.hta.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hta.dto.enums.CurrencyCode;
import org.hta.thirtyparty.model.ClientDto;
import org.hta.thirtyparty.model.UserDto;

import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "amount",
        "exchangeRateAmount",
        "exchangeRate",
        "currencyOrigin",
        "currencyDestination",
        "username",
        "documentNumber"})

public class CurrencyTransactionEventDto implements Serializable {


    private BigDecimal amount;

    private BigDecimal exchangeRateAmount;

    private BigDecimal exchangeRate;

    private CurrencyCode currencyOrigin;

    private CurrencyCode currencyDestination;

    private String username;

    private String documentNumber;

    private String numberOperation;

    private String accountNumberOrigin;

    private String accountNumberDestination;
}
