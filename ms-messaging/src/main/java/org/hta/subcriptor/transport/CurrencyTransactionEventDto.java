package org.hta.subcriptor.transport;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hta.subcriptor.transport.enums.CurrencyCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyTransactionEventDto implements Serializable {

    private BigDecimal amount;

    private BigDecimal exchangeRateAmount;

    private BigDecimal exchangeRate;

    private CurrencyCode currencyOrigin;

    private String currencyName;

    private CurrencyCode currencyDestination;

    private String username;

    private String nameComplete;

    private String email;

    private String documentNumber;

    private String numberOperation;

    private String accountNumberOrigin;

    private String accountNumberDestination;
}
