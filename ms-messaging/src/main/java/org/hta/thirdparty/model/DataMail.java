package org.hta.thirdparty.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataMail {

    @NotEmpty(message = "operation It can not be null")
    @NotNull(message = "operation It can not be null")
    private String operation;

    @NotEmpty(message = "documentNumber It can not be null")
    @NotNull(message = "documentNumber It can not be null")
    private String documentNumber;

    @NotEmpty(message = "username It can not be null")
    @NotNull(message = "username It can not be null")
    private String username;

    @NotEmpty(message = "nameComplete It can not be null")
    @NotNull(message = "nameComplete It can not be null")
    private String nameComplete;

    @NotEmpty(message = "account It can not be null")
    @NotNull(message = "account It can not be null")
    private String account;

    @NotEmpty(message = "accountDestination It can not be null")
    @NotNull(message = "accountDestination It can not be null")
    private String accountDestination;

    @DecimalMin(value = "0.1")
    @NotNull(message = "amount It can not be null")
    private BigDecimal amount;

    @NotEmpty(message = "currency It can not be null")
    @NotNull(message = "currency It can not be null")
    private String currency;

}
