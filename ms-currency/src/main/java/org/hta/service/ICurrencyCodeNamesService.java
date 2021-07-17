package org.hta.service;

import org.hta.domain.entity.CurrencyCodeNames;

import java.util.List;

public interface ICurrencyCodeNamesService {

    List<CurrencyCodeNames> findAll();
}
