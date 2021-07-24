package org.hta.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.hta.domain.document.MessagingDocument;
import org.hta.domain.repository.MessagingRepository;
import org.hta.dto.MessagingRequest;
import org.hta.dto.MessagingResponse;
import org.hta.mapper.MessagingMapper;
import org.hta.service.IMessagingService;
import org.hta.thirdparty.SendgridApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Slf4j
@Service
public class MessagingService implements IMessagingService {

    @Autowired
    private SendgridApi sendgridApi;

    @Autowired
    private MessagingRepository messagingRepository;

    @Override
    public Mono<MessagingResponse> senderMessageTemple(MessagingRequest request) throws IOException {
        return Mono.just(sendgridApi.call(
                request.getPayload(),
                request.getEmail(),
                request.getTemplateId()))
                .map(responseSengrid -> MessagingMapper.apiToEntity.apply(request, responseSengrid))
                .map(messagingDocument -> messagingRepository.save(messagingDocument).block())
                .map(response -> MessagingResponse.builder().messageId(response.getId()).build());
    }

    @Override
    public Mono<MessagingDocument> messageId(String id) {
        return messagingRepository.findById(id);
    }

    @Override
    public Flux<MessagingDocument> findAll() {
        return messagingRepository.findAllByState(Boolean.TRUE);
    }

    @Override
    public Flux<MessagingDocument> findAllByEmail(String email) {
        return messagingRepository.findAllByEmailTo(email);
    }
}
