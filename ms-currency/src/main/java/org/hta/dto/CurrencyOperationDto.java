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

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"amount", "exchangeRateAmount", "exchangeRate", "currencyOrigin", "nameOrigin", "nameDestination", "currencyDestination"})

public class CurrencyOperationDto {

        private BigDecimal amount;

        private CurrencyCode currencyOrigin;

        private String nameOrigin;

        private BigDecimal exchangeRateAmount;

        private String accountNumberOrigin;

        private String accountNumberDestination;

        private UserDto user;

        private ClientDto client;
}
