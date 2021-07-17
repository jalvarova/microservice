package org.hta.domain;

import org.hta.domain.entity.CurrencyTransaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurrencyTransactionRepository extends CrudRepository<CurrencyTransaction, Long> {

}
