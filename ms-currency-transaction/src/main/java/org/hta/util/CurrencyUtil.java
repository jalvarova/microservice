package org.hta.util;

import org.hta.domain.entity.CurrencyExchange;
import org.hta.exceptions.CurrencyExchangeNotFound;

public final class CurrencyUtil {

    public static CurrencyExchange validateNullCurrency(CurrencyExchange currencyExchange) {
        if (currencyExchange.getId() == 0) {
            throw new CurrencyExchangeNotFound("001");
        }
        return currencyExchange;
    }

}
