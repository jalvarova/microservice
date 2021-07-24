package org.hta.thirdparty;

import org.hta.thirdparty.model.RequestEvent;
import org.hta.thirdparty.model.ResponseEvent;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "event-service", url = "${event.uri}")
public interface EventFeignClient {


    @RequestMapping(method = RequestMethod.POST, value = "/send")
    ResponseEvent callEventSendApi(@RequestBody RequestEvent RequestEvent);

    @RequestMapping(method = RequestMethod.POST, value = "/receive")
    ResponseEvent callEventReceiveApi(@RequestBody RequestEvent RequestEvent);
}
