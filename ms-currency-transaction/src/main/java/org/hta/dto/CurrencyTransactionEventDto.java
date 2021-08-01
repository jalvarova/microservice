package org.hta.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hta.dto.enums.CurrencyCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

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
        "currencyName",
        "currencyDestination",
        "username",
        "documentNumber"})

public class CurrencyTransactionEventDto implements Serializable {

    private BigDecimal amount;

    private BigDecimal exchangeRateAmount;

    private BigDecimal exchangeRate;

    private CurrencyCode currencyOrigin;

    private String currencyName;

    private CurrencyCode currencyDestination;

    private String username;

    private String documentNumber;

    private String email;

    private String nameComplete;

    private String numberOperation;

    private BigInteger accountNumberOrigin;

    private BigInteger accountNumberDestination;
}
