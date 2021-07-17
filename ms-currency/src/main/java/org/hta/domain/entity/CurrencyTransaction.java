package org.hta.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "currency_transaction", schema = "hta")
@Entity
public class CurrencyTransaction extends EntityBase {


    @GeneratedValue
    @Id
    private Long id;

    @Column(name = "currency_origin")
    private String currencyOrigin;

    @Column(name = "currency_destination")
    private String currencyDestination;

    @Column(name = "amount_rate", precision = 11, scale = 5)
    private BigDecimal amountRate;

    @Column(name = "amount", precision = 11, scale = 5)
    private BigDecimal amount;

    @Column(name = "amount_exchange_rate", precision = 11, scale = 5)
    private BigDecimal amountExchangeRate;

    @Column(name = "username")
    private String username;

    @Column(name = "document_number")
    private String documentNumber;

    @Column(name = "operation_number")
    private String operationNumber;

    @Column(name = "account_origin")
    private BigInteger accountOrigin;

    @Column(name = "account_destination")
    private BigInteger accountDestination;

}
