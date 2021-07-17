package org.hta.configuration;

import org.hta.domain.CurrencyCodeNamesRepository;
import org.hta.domain.CurrencyExchangeRepository;
import org.hta.domain.CurrencyTransactionRepository;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackageClasses = {
        CurrencyExchangeRepository.class,
        CurrencyCodeNamesRepository.class,
        CurrencyTransactionRepository.class
})
@Import({
        WebSecurityConfig.class,
        DataSourceConfig.class
})
@EnableCaching
public class ApplicationConfiguration {
}
