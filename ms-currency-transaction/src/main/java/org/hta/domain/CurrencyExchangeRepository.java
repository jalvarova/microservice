package org.hta.domain;

import org.hta.domain.entity.CurrencyExchange;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyExchangeRepository extends CrudRepository<CurrencyExchange, Long> {

    @Query(value = "FROM CurrencyExchange e WHERE" +
            " e.currencyExchangeOrigin = :exchangeOrigin AND" +
            " e.currencyExchangeDestination = :exchangeDestination")
    CurrencyExchange findByApplyCurrency(String exchangeOrigin, String exchangeDestination);

}
