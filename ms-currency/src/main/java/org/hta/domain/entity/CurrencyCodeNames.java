package org.hta.domain.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "currency_code_names", schema = "hta")
public class CurrencyCodeNames extends EntityBase {

    @Id
    @Column(name = "currency_code_names_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 1, max = 4)
    @Column(name = "currency_code", unique = true)
    private String currencyCode;

    @Column(name = "currency_name", unique = true)
    private String currencyName;

    @Column(name = "state")
    private Boolean state;
}
