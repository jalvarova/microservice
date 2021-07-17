package org.hta.configuration;

import lombok.extern.slf4j.Slf4j;
import org.hta.domain.CurrencyCodeNamesRepository;
import org.hta.domain.CurrencyExchangeRepository;
import org.hta.domain.entity.CurrencyCodeNames;
import org.hta.domain.entity.CurrencyExchange;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

//@Configuration
@Slf4j
class LoadDataBase {

    private static final List<CurrencyExchange> currencyExchanges = new ArrayList<>();

    private static final List<CurrencyCodeNames> currencyCodeNames = new ArrayList<>();

    static {
        currencyCodeNames.add(CurrencyCodeNames
                .builder()
                .currencyCode("USD")
                .currencyName("Dólar estadounidense")
                .state(Boolean.TRUE)
                .build());

        currencyCodeNames.add(CurrencyCodeNames
                .builder()
                .currencyCode("PEN")
                .currencyName("Nuevo sol")
                .state(Boolean.TRUE)
                .build());

        currencyCodeNames.add(CurrencyCodeNames
                .builder()
                .currencyCode("JPY")
                .currencyName("Yen japonés")
                .state(Boolean.TRUE)
                .build());

        currencyCodeNames.add(CurrencyCodeNames
                .builder()
                .currencyCode("EUR")
                .currencyName("Euro")
                .state(Boolean.TRUE)
                .build());

        currencyExchanges.add(new CurrencyExchange(BigDecimal.valueOf(3.39608), "USD", "PEN"));
        currencyExchanges.add(new CurrencyExchange(BigDecimal.valueOf(0.294475), "PEN", "USD"));
        currencyExchanges.add(new CurrencyExchange(BigDecimal.valueOf(3.72884), "EUR", "PEN"));
        currencyExchanges.add(new CurrencyExchange(BigDecimal.valueOf(0.268180), "PEN", "EUR"));
        currencyExchanges.add(new CurrencyExchange(BigDecimal.valueOf(31.5680), "JPY", "PEN"));
        currencyExchanges.add(new CurrencyExchange(BigDecimal.valueOf(0.0316777), "PEN", "JPY"));
    }

    //@Bean
    CommandLineRunner initDatabase(CurrencyCodeNamesRepository codeNamesRepository, CurrencyExchangeRepository repository) {
        return args -> {
            log.info("Preloading  Currency Names " + codeNamesRepository.saveAll(currencyCodeNames));
            log.info("Preloading  Currency Exchange " + repository.saveAll(currencyExchanges));
        };
    }
}
