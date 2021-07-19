package org.hta.domain;

import org.hta.domain.entity.CurrencyTransaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyTransactionRepository extends CrudRepository<CurrencyTransaction, Long> {

}
