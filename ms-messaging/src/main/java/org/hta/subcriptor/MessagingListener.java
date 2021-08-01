package org.hta.subcriptor;

import lombok.extern.slf4j.Slf4j;
import org.hta.aspect.TraceSpan;
import org.hta.domain.document.MessagingDocument;
import org.hta.domain.repository.MessagingRepository;
import org.hta.subcriptor.transport.CurrencyTransactionEventDto;
import org.hta.thirdparty.SendgridApi;
import org.hta.thirdparty.model.DataMail;
import org.hta.thirdparty.model.ResponseSengrid;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

import static org.hta.util.ConvertUtil.jsonToString;
import static org.hta.util.ConvertUtil.stringToObject;

@Slf4j
@Component
public class MessagingListener {

    @Autowired
    private MessagingRepository messagingRepository;

    @Autowired
    private SendgridApi sendgridApi;

    @Value("${sendgrid.from}")
    private String emailFrom;

    @Value("${sendgrid.id}")
    private String templateId;

    @TraceSpan(key = "eventTransactionRabbitmq")
    @RabbitListener(queues = "${sendgrid.queueName}")
    public void eventTransactionMessaging(String payload) {

        MessagingDocument messagingDocument = null;

        CurrencyTransactionEventDto eventDto = stringToObject(payload, CurrencyTransactionEventDto.class);

        try {
            messagingDocument = messagingRepository.findByOperationNumber(eventDto.getNumberOperation()).block();

            if (Objects.isNull(messagingDocument)) {
                log.info("Received a new notification...");
                ResponseSengrid responseSengrid = sendMessage(eventDto);

                String payloadResponse = jsonToString(responseSengrid);
                MessagingDocument document = MessagingDocument
                        .builder()
                        .operationNumber(eventDto.getNumberOperation())
                        .emailFrom(emailFrom)
                        .emailTo(eventDto.getEmail())
                        .payload(payload)
                        .payloadResponse(payloadResponse)
                        .sendMessageDate(LocalDateTime.now())
                        .attempts(0)
                        .state(Boolean.TRUE)
                        .build();

                messagingRepository.save(document).block();
            } else {
                if (!messagingDocument.getState() && messagingDocument.getAttempts() < 3) {
                    ResponseSengrid responseSengrid = sendMessage(eventDto);

                    String payloadResponse = jsonToString(responseSengrid);
                    MessagingDocument document = MessagingDocument
                            .builder()
                            .operationNumber(eventDto.getNumberOperation())
                            .emailFrom(emailFrom)
                            .emailTo(eventDto.getEmail())
                            .payload(payload)
                            .payloadResponse(payloadResponse)
                            .resendMessageDate(LocalDateTime.now())
                            .attempts(messagingDocument.getAttempts() + 1)
                            .state(Boolean.TRUE)
                            .build();

                    messagingRepository.save(document).block();
                } else {
                    log.info("attempts exceeded or send email correct");
                }
            }

        } catch (Exception e) {
            if (Objects.nonNull(messagingDocument)) {
                messagingDocument.setAttempts(messagingDocument.getAttempts() + 1);
                messagingDocument.setResendMessageDate(LocalDateTime.now());
                messagingRepository.save(messagingDocument).block();

            } else {
                MessagingDocument document = MessagingDocument
                        .builder()
                        .operationNumber(eventDto.getNumberOperation())
                        .emailFrom(emailFrom)
                        .emailTo(eventDto.getEmail())
                        .payload(payload)
                        .sendMessageDate(LocalDateTime.now())
                        .attempts(0)
                        .state(Boolean.FALSE)
                        .build();

                messagingRepository.save(document).block();
                log.info("Error consumer :" + e.getMessage());
            }

        }
    }

    private ResponseSengrid sendMessage(CurrencyTransactionEventDto eventDto) throws IOException {
        DataMail dataMail = DataMail
                .builder()
                .operation(eventDto.getNumberOperation())
                .account(eventDto.getAccountNumberOrigin())
                .accountDestination(eventDto.getAccountNumberDestination())
                .amount(eventDto.getAmount())
                .currency(eventDto.getCurrencyName())
                .documentNumber(eventDto.getDocumentNumber())
                .nameComplete(eventDto.getNameComplete())
                .username(eventDto.getUsername())
                .build();

        return sendgridApi.call(dataMail, eventDto.getEmail(), templateId);

    }
}
