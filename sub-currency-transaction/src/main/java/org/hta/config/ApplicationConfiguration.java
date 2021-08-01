package org.hta.config;

import org.hta.domain.CurrencyTransactionRepository;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackageClasses = {
        CurrencyTransactionRepository.class
})

@EnableCaching
public class ApplicationConfiguration {

    @Primary
    @Bean // Serialize message content to json using TextMessage
    public Jackson2JsonMessageConverter jacksonJmsMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
