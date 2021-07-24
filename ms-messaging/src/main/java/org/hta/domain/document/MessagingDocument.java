package org.hta.domain.document;

import lombok.*;
import org.hta.subcriptor.transport.enums.CurrencyCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(value = "messaging")
public class MessagingDocument {

    @Id
    private String id;

    private String operationNumber;

    private String emailTo;

    private String emailFrom;

    private String subject;

    private String payload;

    private String payloadResponse;

    private Boolean state = Boolean.TRUE;

    private Integer attempts = 0;

    private LocalDateTime sendMessageDate;

    private LocalDateTime resendMessageDate;

}
