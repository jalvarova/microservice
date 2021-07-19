package org.hta.config;

import org.hta.domain.CurrencyTransactionRepository;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackageClasses = {
        CurrencyTransactionRepository.class
})

@EnableCaching
public class ApplicationConfiguration {
}
