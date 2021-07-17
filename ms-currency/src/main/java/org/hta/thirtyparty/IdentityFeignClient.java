package org.hta.thirtyparty;

import org.hta.thirtyparty.model.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "identity-service", url = "${identity.uri}")
public interface IdentityFeignClient {


    @RequestMapping(method = RequestMethod.GET, value = "/users/{username}")
    UserDto callIdentityApi(
            @RequestHeader(value = "Authorization") String authorization,
            @PathVariable("username") String username
    );
}
