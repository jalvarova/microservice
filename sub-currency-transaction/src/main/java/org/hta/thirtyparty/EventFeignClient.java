package org.hta.thirtyparty;

import org.hta.thirtyparty.model.RequestEvent;
import org.hta.thirtyparty.model.ResponseEvent;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "event-service", url = "${event.uri}")
public interface EventFeignClient {


    @RequestMapping(method = RequestMethod.POST, value = "/send")
    ResponseEvent callEventSendApi(
            @RequestHeader(value = "Authorization") String authorization,
            @RequestBody RequestEvent RequestEvent
    );
    @RequestMapping(method = RequestMethod.POST, value = "/receive")
    ResponseEvent callEventReceiveApi(
            @RequestHeader(value = "Authorization") String authorization,
            @RequestBody RequestEvent RequestEvent
    );
}
