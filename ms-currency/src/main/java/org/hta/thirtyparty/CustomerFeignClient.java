package org.hta.thirtyparty;

import org.hta.thirtyparty.model.ClientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "customers-service", url = "${customers.uri}")
public interface CustomerFeignClient {


    @RequestMapping(method = RequestMethod.GET, value = "/customers/{documentNumber}")
    ClientDto callCustomerApi(
            @RequestHeader(value = "Authorization") String authorization,
            @PathVariable("documentNumber") String documentNumber
    );
}
