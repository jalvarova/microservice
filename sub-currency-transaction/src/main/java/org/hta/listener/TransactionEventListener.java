package org.hta.listener;

import lombok.extern.slf4j.Slf4j;
import org.hta.domain.CurrencyTransactionRepository;
import org.hta.domain.entity.CurrencyTransaction;
import org.hta.transport.CurrencyTransactionEventDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.function.Consumer;

import static org.hta.util.ConvertUtil.stringToObject;

@Slf4j
@Component
public class TransactionEventListener {

    @Autowired
    private CurrencyTransactionRepository transactionRepository;

    @Bean
    public Consumer<Message<CurrencyTransactionEventDto>> eventTransactionPusbSub() {
        return productPayload -> {
            log.info("Rabbitmq Event Init");
            CurrencyTransactionEventDto productDto = productPayload.getPayload();

            log.info("Rabbitmq Finished Event");
        };
    }


    @Bean
    public Consumer<Message<String>> eventTransactionRabbitmq() {
        return productPayload -> {
            log.info("PubSub Event Init");
            CurrencyTransactionEventDto eventDto = stringToObject(productPayload.getPayload(), CurrencyTransactionEventDto.class);
            if (Objects.nonNull(eventDto)) {
                CurrencyTransaction currencyTransaction =
                        CurrencyTransaction
                                .builder()
                                .accountDestination(eventDto.getAccountNumberDestination())
                                .accountOrigin(eventDto.getAccountNumberDestination())
                                .amount(eventDto.getAmount())
                                .amountExchangeRate(eventDto.getExchangeRate())
                                .amountRate(eventDto.getExchangeRate())
                                .currencyDestination(eventDto.getCurrencyDestination().name())
                                .currencyOrigin(eventDto.getCurrencyOrigin().name())
                                .documentNumber(eventDto.getDocumentNumber())
                                .operationNumber(eventDto.getNumberOperation())
                                .username(eventDto.getUsername())
                                .build();

                transactionRepository.save(currencyTransaction);
                log.info("PubSub Finished Event " + currencyTransaction.toString());
            } else {
                log.error("Event Error Consumer");
            }
        };
    }
}
