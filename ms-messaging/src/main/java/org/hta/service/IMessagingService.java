package org.hta.service;

import org.hta.domain.document.MessagingDocument;
import org.hta.dto.MessagingRequest;
import org.hta.dto.MessagingResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;

public interface IMessagingService {

    Mono<MessagingResponse> senderMessageTemple(MessagingRequest request) throws IOException;

    Mono<MessagingDocument> messageId(String id);

    Flux<MessagingDocument> findAll();

    Flux<MessagingDocument> findAllByEmail(String email);
}
