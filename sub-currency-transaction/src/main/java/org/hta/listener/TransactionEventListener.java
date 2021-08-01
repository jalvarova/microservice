package org.hta.listener;

import lombok.extern.slf4j.Slf4j;
import org.hta.aspect.TraceSpan;
import org.hta.domain.CurrencyTransactionRepository;
import org.hta.domain.entity.CurrencyTransaction;
import org.hta.thirtyparty.EventApi;
import org.hta.thirtyparty.model.ResponseEvent;
import org.hta.transport.CurrencyTransactionEventDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static org.hta.util.ConvertUtil.stringToObject;

@Slf4j
@Component
public class TransactionEventListener {

    @Autowired
    private CurrencyTransactionRepository transactionRepository;

    @Autowired
    private EventApi eventFeign;

    @TraceSpan(key = "eventTransactionRabbitmq")
    @RabbitListener(queues = "${transaction.queueName}")
    public void eventTransactionRabbitmq(String productPayload) {
        log.info("Rabbitmq Event Init");
        try {
            CurrencyTransactionEventDto eventDto = stringToObject(productPayload, CurrencyTransactionEventDto.class);
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

                log.info("Finished Save Transaction " + currencyTransaction.toString());

                ResponseEvent responseEvent = eventFeign.eventSend(eventDto).blockingGet();

                log.info("Finish producer event Messaging " + responseEvent.getMessage());

                log.info("PubSub Finished Event " + currencyTransaction.toString());
            }
        } catch (Exception e) {
            log.error("Event Error Consumer Transaction " + e.getMessage());
        }
    }
}
