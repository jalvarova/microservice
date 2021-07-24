package org.hta.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.hta.domain.document.MessagingDocument;
import org.hta.domain.repository.MessagingRepository;
import org.hta.thirdparty.EventFeignImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
public class MessagingScheduler {

    private static final String DATETIME_FORMAT = "yyyy-MM-dd hh:mm:ss";

    @Autowired
    private MessagingRepository messagingRepository;

    @Autowired
    private EventFeignImpl eventFeign;

    @Scheduled(fixedDelay = 10 * 60 * 1000, initialDelay = 500)
    public void schedulerSendSendgrid() {
        messagingRepository.findAllByState(Boolean.FALSE)
                .subscribeOn(Schedulers.parallel())
                .filter(x -> x.getAttempts() < 3)
                .map(MessagingDocument::getPayload)
                .map(eventDto -> eventFeign.eventSend(eventDto))
                .blockFirst();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATETIME_FORMAT);
        System.out.println("Notification send error message" + LocalDateTime.now().format(formatter));
    }
}
