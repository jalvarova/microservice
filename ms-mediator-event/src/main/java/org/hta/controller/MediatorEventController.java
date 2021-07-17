package org.hta.controller;

import org.hta.dto.RequestEvent;
import org.hta.dto.ResponseEvent;
import org.hta.service.IBrokerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ms-mediator-event/api/v1")
public class MediatorEventController {

    @Autowired
    private IBrokerService brokerService;

    @PostMapping("/send")
    public ResponseEvent sendMessage(@RequestBody RequestEvent requestEvent) {

        return brokerService.send(requestEvent);
    }

    @PostMapping("/receive")
    public ResponseEvent receiveMessage(@RequestBody RequestEvent requestEvent) {
        return brokerService.receive(requestEvent);
    }

    @PostMapping("/send-receive")
    public ResponseEvent sendReceiveMessage(@RequestBody RequestEvent requestEvent) {
        return null;
    }
}
