package org.hta.controller;

import org.hta.domain.document.MessagingDocument;
import org.hta.dto.MessagingRequest;
import org.hta.dto.MessagingResponse;
import org.hta.service.IMessagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Validated
@RestController
@RequestMapping("/ms-messaging/api/v1")
public class MessagingController {

    @Autowired
    private IMessagingService messagingService;

    @PostMapping(value = "/sender", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<MessagingResponse> senderMessageTemple(@RequestBody MessagingRequest request) throws IOException {
        return messagingService.senderMessageTemple(request);
    }

    @GetMapping(value = "/messages/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<MessagingDocument> messageId(@PathVariable("id") String id) {
        return messagingService.messageId(id);
    }

    @GetMapping(value = "/messages", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<MessagingDocument> messageId() {
        return messagingService.findAll();
    }

    @GetMapping(value = "/messages/{email}/email", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<MessagingDocument> messageByEmail(@PathVariable("email") String email) {
        return messagingService.findAllByEmail(email);
    }
}
